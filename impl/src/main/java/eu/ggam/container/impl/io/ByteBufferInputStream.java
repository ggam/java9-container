package eu.ggam.container.impl.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ByteBufferInputStream extends InputStream {

    private final Queue<ByteBuffer> queue;

    private ByteBuffer current;

    public ByteBufferInputStream(List<ByteBuffer> buffers) {
        this.queue = new ArrayDeque<>(buffers.size());
        for (ByteBuffer buffer : buffers) {
            buffer.flip();
            queue.offer(buffer);
        }
        this.current = queue.poll();
    }

    @Override
    public int read() throws IOException {
        if (current == null) {
            return -1;
        }

        if (!current.hasRemaining() && ((current = queue.poll()) == null || !current.hasRemaining())) {
            return -1;
        }

        return current.get() & 0xFF;
    }

    @Override
    public int read(byte[] bytes, int off, int length) throws IOException {
        if (current == null) {
            return -1;
        }

        if (!current.hasRemaining() && ((current = queue.poll()) == null || !current.hasRemaining())) {
            return -1;
        }

        length = Math.min(length, current.remaining());
        current.get(bytes, off, length);

        return length;
    }

    @Override
    public void close() throws IOException {
        queue.clear();
        current = null;
    }

}
