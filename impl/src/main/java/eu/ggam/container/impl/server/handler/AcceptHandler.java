package eu.ggam.container.impl.server.handler;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermo
 */
public class AcceptHandler implements Handler {

    private static final Logger LOGGER = Logger.getLogger(AcceptHandler.class.getName());
    
    @Override
    public void handle(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
        SocketChannel sc = ssc.accept();
        
        LOGGER.log(Level.INFO, "Connected client: {0}", sc);

        sc.configureBlocking(false);
        sc.register(selectionKey.selector(), SelectionKey.OP_READ);
    }

    @Override
    public boolean handles(int readyOps) {
        return (readyOps & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;
    }
}
