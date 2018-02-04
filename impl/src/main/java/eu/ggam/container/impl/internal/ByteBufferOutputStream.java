package eu.ggam.container.impl.internal;

import java.io.IOException;
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
    private final Queue<ByteBuffer> buffers = new ArrayDeque<>();
    private ByteBuffer current;

    public ByteBufferOutputStream(int bufferSize) {
        this.bufferSize = bufferSize;
        current = ByteBuffer.allocate(bufferSize);
        buffers.offer(current);
    }

    @Override
    public void write(int b) throws IOException {
        if (!current.hasRemaining()) {
            current = ByteBuffer.allocate(bufferSize);
            buffers.offer(current);
        }

        current.put((byte) b);
    }

    @Override
    public void close() throws IOException {
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
