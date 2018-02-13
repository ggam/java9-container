package eu.ggam.container.impl.server.handler;

import eu.ggam.container.impl.server.HttpRequestHolder;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Map;

/**
 *
 * @author guillermo
 */
public class AcceptHandler implements Handler {

    private final Map<SocketChannel, HttpRequestHolder> pendingData;

    public AcceptHandler(Map<SocketChannel, HttpRequestHolder> pendingData) {
        this.pendingData = pendingData;
    }

    @Override
    public void handle(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
        SocketChannel sc = ssc.accept();

        pendingData.put(sc, new HttpRequestHolder());

        sc.configureBlocking(false);
        sc.register(selectionKey.selector(), SelectionKey.OP_READ);
    }

    @Override
    public boolean handles(int readyOps) {
        return (readyOps & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;
    }
}
