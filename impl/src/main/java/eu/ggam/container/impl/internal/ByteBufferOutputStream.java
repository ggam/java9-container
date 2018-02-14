package eu.ggam.container.impl.internal;

import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

/**
 *
 * @author guillermo
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
        current = ByteBuffer.allocateDirect(bufferSize);
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

    public Optional<ByteBuffer> getNextBuffer() {
        ByteBuffer poll = buffers.poll();

        if (poll == null || poll.remaining() == bufferSize) {
            return Optional.empty();
        }

        poll.flip();

        return Optional.of(poll);
    }

}
