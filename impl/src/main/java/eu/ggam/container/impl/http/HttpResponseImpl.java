package eu.ggam.container.impl.http;

import eu.ggam.container.api.http.HttpResponse;
import eu.ggam.container.impl.internal.ByteBufferOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author guillermo
 */
public class HttpResponseImpl implements HttpResponse {


    public enum ResponseStatus {
        ALL_SENT,
        IN_PROGRESS,
        CONNECTION_LOST
    }

    private int status = 200;
    private Map<String, List<String>> headers = new HashMap<>();

    private final ArrayDeque<ByteBuffer> bufferQueue = new ArrayDeque<>();
    private final ByteBufferOutputStream output;
    
    private final ArrayDeque<ByteBuffer> headersBufferQueue = new ArrayDeque<>();
    private final ByteBufferOutputStream headersOutput;

    private boolean finished = false;

    public HttpResponseImpl() {
        output = new ByteBufferOutputStream(bufferQueue, 4096);
        headersOutput = new ByteBufferOutputStream(headersBufferQueue, 4096);
    }

    @Override
    public void setHeaders(Map<String, List<String>> headers) {
        if (finished) {
            throw new IllegalStateException("Response already finished");
        }

        this.headers = headers;
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    @Override
    public OutputStream getOutputStream() {
        if (finished) {
            throw new IllegalStateException("Response already finished");
        }

        return output;
    }

    @Override
    public void setStatus(int status) {
        if (finished) {
            throw new IllegalStateException("Response already finished");
        }

        this.status = status;
    }

    @Override
    public int getResponseStatus() {
        return status;
    }

    public Queue<ByteBuffer> getBufferQueue() {
        if (!finished) {
            throw new IllegalStateException("Response not finished");
        }
        return bufferQueue;
    }

    public void finish() {
        try {
            // No more changes are allowed
            // TODO: outputStream should throw an exception on attempts to write
            
            headersOutput.write(("HTTP/1.1 " + getResponseStatus() + " Unknown").getBytes());
            
            Iterator<Map.Entry<String, List<String>>> iterator = headers.entrySet().iterator();
            
            headersOutput.write((iterator.hasNext() ? "\n" : "\r\n\r\n").getBytes());
            
            while (iterator.hasNext()) {
                Map.Entry<String, List<String>> header = iterator.next();
                for (String headerValue : header.getValue()) {
                    String endOfLine = iterator.hasNext() ? "\n" : "\r\n\r\n";
                    headersOutput.write((header.getKey() + ": " + headerValue + endOfLine).getBytes());
                }
            }
            
            ByteBuffer nextHeadersBuffer;
            while ((nextHeadersBuffer = headersBufferQueue.pollLast()) != null) {
                bufferQueue.offerFirst(nextHeadersBuffer);
            }
            
            finished = true;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public ResponseStatus write(SocketChannel channel) throws IOException {
        while (!bufferQueue.isEmpty()) {
            ByteBuffer buf = bufferQueue.peek();
            buf.flip();

            if (channel.write(buf) == -1) { // Connection is closed
                return ResponseStatus.CONNECTION_LOST;
            }

            if (buf.hasRemaining()) {
                return ResponseStatus.IN_PROGRESS;
            }
            bufferQueue.remove();
        }

        return ResponseStatus.ALL_SENT;
    }
}
