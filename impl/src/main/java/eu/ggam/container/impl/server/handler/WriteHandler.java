package eu.ggam.container.impl.server.handler;

import eu.ggam.container.impl.server.HttpRequestHolder;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author guillermo
 */
public class WriteHandler implements Handler {

    private final Map<SocketChannel, HttpRequestHolder> pendingData;

    public WriteHandler(Map<SocketChannel, HttpRequestHolder> pendingData) {
        this.pendingData = pendingData;
    }

    @Override
    public void handle(SelectionKey selectionKey) throws IOException {
        SocketChannel sc = (SocketChannel) selectionKey.channel();

        HttpRequestHolder get = pendingData.get(sc);
        get.createExchange().prepareResponseBuffer();

        Queue<ByteBuffer> queue = pendingData.get(sc).getOutput();
        while (!queue.isEmpty()) {
            ByteBuffer buf = queue.peek();
            buf.flip();
            int written = sc.write(buf);
            if (written == -1) {
                // Lost connection?
                sc.close();
                pendingData.remove(sc);
                return;
            }

            if (buf.hasRemaining()) {
                return;
            }
            queue.remove();
        }
        pendingData.put(sc, new HttpRequestHolder());
        selectionKey.interestOps(SelectionKey.OP_READ);
    }

    @Override
    public boolean handles(int readyOps) {
        return (readyOps & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE;
    }

}
