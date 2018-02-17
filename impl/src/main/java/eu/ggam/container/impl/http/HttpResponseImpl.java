package eu.ggam.container.impl.http;

import eu.ggam.container.api.http.HttpResponse;
import eu.ggam.container.impl.server.connection.Connection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    private final ByteArrayOutputStream output;

    private boolean completed;

    private final OutputStream connectionOutputStream;

    public HttpResponseImpl(Connection connection) {
        connectionOutputStream = connection.getOutputStream(); // SocketChannel buffer

        output = new ResponseOutputStream(); // Intermediate stream
    }

    @Override
    public void setHeaders(Map<String, List<String>> headers) {
        if (completed) {
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
        if (completed) {
            throw new IllegalStateException("Response already finished");
        }

        return output;
    }

    @Override
    public void setStatus(int status) {
        if (completed) {
            throw new IllegalStateException("Response already finished");
        }

        this.status = status;
    }

    @Override
    public int getResponseStatus() {
        return status;
    }

    public void complete() {
        try {
            // No more changes are allowed
            // TODO: outputStream should throw an exception on attempts to write

            connectionOutputStream.write(("HTTP/1.1 " + getResponseStatus() + " Unknown").getBytes());

            Iterator<Map.Entry<String, List<String>>> iterator = headers.entrySet().iterator();

            connectionOutputStream.write((iterator.hasNext() ? "\n" : "\r\n\r\n").getBytes());

            while (iterator.hasNext()) {
                Map.Entry<String, List<String>> header = iterator.next();
                for (String headerValue : header.getValue()) {
                    String endOfLine = iterator.hasNext() ? "\n" : "\r\n\r\n";
                    connectionOutputStream.write((header.getKey() + ": " + headerValue + endOfLine).getBytes());
                }
            }

            output.writeTo(connectionOutputStream);

            completed = true;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    /*
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
    }*/
    private class ResponseOutputStream extends ByteArrayOutputStream {

        @Override
        public void write(byte[] b) throws IOException {
            checkNotCompleted();
            super.write(b);
        }

        @Override
        public synchronized void write(byte[] b, int off, int len) {
            checkNotCompleted();
            super.write(b, off, len);
        }

        @Override
        public synchronized void write(int b) {
            checkNotCompleted();
            super.write(b);
        }

        private void checkNotCompleted() {
            if (completed) {
                throw new IllegalStateException("Response already finished");
            }
        }

    }
}
