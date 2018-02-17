package eu.ggam.container.impl.server.connection;

import eu.ggam.container.impl.internal.ByteBufferInputStream;
import eu.ggam.container.impl.internal.ByteBufferOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

/**
 *
 * @author guillermo
 */
public class Connection {

    private static final int BUFFER_SIZE = 4096;

    private final SocketChannel channel;

    /* READER */
    private final Queue<ByteBuffer> inputQueue = new ArrayDeque<>();
    private ByteBuffer lastReadBuffer;

    /* WRITER */
    private final Queue<ByteBuffer> outputQueue = new ArrayDeque<>();

    public Connection(SocketChannel channel) {
        this.channel = channel;
    }

    public void read() throws IOException {
        // TODO: reuse last buffer if possible
        lastReadBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        inputQueue.add(lastReadBuffer);

        int read = channel.read(lastReadBuffer);
        if (read == -1) {
            channel.close();
            throw new ConnectionClosedException();
        }
    }

    public boolean readFinished() {
        if (lastReadBuffer == null) {
            return false;
        }

        String lastRead = new String(lastReadBuffer.array());
        int length = lastRead.length();

        return "".equals(lastRead.substring(length - 3, length - 1).trim());
    }

    public InputStream getInputStream() {
        return new ByteBufferInputStream(new ArrayList<>(inputQueue));
    }

    public void write() throws IOException {
        while (!outputQueue.isEmpty()) {
            ByteBuffer buf = outputQueue.peek();
            buf.flip();
            
            int written = channel.write(buf);
            if (written == -1) { // Connection is closed
                channel.close();
                throw new ConnectionClosedException();
            }

            if (!buf.hasRemaining()) {
                outputQueue.remove();
            }

            if (written == 0 && !outputQueue.isEmpty()) {
                return; // The client is not ready to receive more data at this point
            }
        }
    }

    public boolean writeFinished() {
        return outputQueue.isEmpty();
    }

    public OutputStream getOutputStream() {
        return new ByteBufferOutputStream(outputQueue, BUFFER_SIZE);
    }

    public void resetBuffers() {
        inputQueue.clear();
        outputQueue.clear();
    }

    public SocketChannel getChannel() {
        return channel;
    }

}
