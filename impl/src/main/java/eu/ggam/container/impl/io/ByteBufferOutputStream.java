package eu.ggam.container.impl.io;

import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ByteBufferOutputStream extends OutputStream {

    private final int bufferSize;
    private final Queue<ByteBuffer> buffers;
    private ByteBuffer current;

    public ByteBufferOutputStream(int bufferSize) {
        this(new ArrayDeque<>(), bufferSize);
    }

    public ByteBufferOutputStream(Queue<ByteBuffer> buffers, int bufferSize) {
        this.buffers = buffers; // Use the reference directly
        this.bufferSize = bufferSize;
        current = ByteBuffer.allocate(bufferSize);
        buffers.offer(current);
    }

    @Override
    public void write(int b) {
        if (!current.hasRemaining()) {
            current = ByteBuffer.allocate(bufferSize);
            buffers.offer(current);
        }

        current.put((byte) b);
    }

    @Override
    public void close() {
        buffers.clear();
    }
}
