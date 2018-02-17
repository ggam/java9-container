package eu.ggam.container.impl.server.connection;

import eu.ggam.container.impl.http.HttpResponseImpl;
import eu.ggam.container.impl.http.HttpRequestImpl;
import eu.ggam.container.impl.RequestHandler;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermo
 */
public class ConnectionManager {

    private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getName());

    private final RequestHandler requestHandler;
    private final Selector selector;
    private final Map<SocketChannel, Connection> connections;

    public ConnectionManager(RequestHandler requestHandler, Selector selector) {
        this.requestHandler = requestHandler;
        this.selector = selector;
        this.connections = new HashMap<>();
    }

    public void beginService() throws IOException {
        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();

            for (Iterator<SelectionKey> it = keys.iterator(); it.hasNext();) {
                SelectionKey key = it.next();
                it.remove();

                if (!key.isValid()) {
                    break; // Channel might have been closed by the previous handler
                }

                try {
                    if (key.isAcceptable()) {
                        Connection connection = acceptConnection(key);
                        connections.put(connection.getChannel(), connection);
                    } else {
                        SocketChannel channel = (SocketChannel) key.channel();
                        Connection connection = connections.get(channel); // Get the associated connection object
                        try {
                            if (key.isReadable()) {
                                connection.read();

                                if (connection.readFinished()) {
                                    requestHandler.handle(new HttpRequestImpl(connection), new HttpResponseImpl(connection));

                                    key.interestOps(SelectionKey.OP_WRITE);
                                }
                            } else if (key.isWritable()) {
                                connection.write();

                                if (connection.writeFinished()) {
                                    connection.resetBuffers();
                                    key.interestOps(SelectionKey.OP_READ);
                                }
                            }
                        } catch (ConnectionClosedException e) {
                            connections.remove(channel);
                        }
                    }

                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Request could not be processed", e);
                }
            }

            connections.entrySet().removeIf(e -> !e.getKey().isConnected()); // Remove closed connections
        }
    }

    private Connection acceptConnection(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel socketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel clientChannel = socketChannel.accept();

        LOGGER.log(Level.INFO, "Connected client: {0}", clientChannel);

        clientChannel.configureBlocking(false);
        SelectionKey newKey = clientChannel.register(selectionKey.selector(), SelectionKey.OP_READ);

        UUID id = UUID.randomUUID();
        newKey.attach(id);

        return new Connection(clientChannel);
    }

    public void shutdown() {
        try {
            selector.close();
            connections.clear();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
