package eu.ggam.container.impl.connection;

import eu.ggam.container.api.http.HttpRequestHandler;
import eu.ggam.container.impl.http.HttpRequestImpl;
import eu.ggam.container.impl.util.NamedThreadFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guillermo González de Agüero
 */
public class HttpServer {

    private static final Logger LOGGER = Logger.getLogger(HttpServer.class.getName());

    private final Map<SocketChannel, SocketConnection> connections;
    private HttpRequestHandler requestHandler;
    private final int port;
    private Selector selector;

    private ExecutorService nioPool;

    public HttpServer(int port) {
        this.connections = new HashMap<>();
        this.requestHandler = new DefaultHttpRequestHandler();
        this.port = port;
    }

    public void beginService() throws IOException {
        // Listen for requests on another thread
        nioPool = Executors.newSingleThreadExecutor(new NamedThreadFactory("http-nio-pool"));
        nioPool.submit(this::beginService2);
    }

    private Void beginService2() throws IOException {
        selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();

        serverSocket.bind(new InetSocketAddress("localhost", port));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

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
                        SocketConnection connection = acceptConnection(key);
                        connections.put(connection.getChannel(), connection);
                    } else {
                        SocketChannel channel = (SocketChannel) key.channel();
                        SocketConnection connection = connections.get(channel); // Get the associated connection object
                        try {
                            if (key.isReadable()) {
                                connection.read();

                                if (connection.readFinished()) {
                                    requestHandler.handle(new HttpRequestImpl(connection.getInputStream())).
                                            thenAccept(response -> {
                                                try {
                                                    OutputStream socketOutputStream = connection.getOutputStream();
                                                    ByteArrayOutputStream intermediateOutputStream = response.getOutputStream();

                                                    socketOutputStream.write(("HTTP/1.1 " + response.getStatus() + " Unknown").getBytes());

                                                    Iterator<Map.Entry<String, List<String>>> iterator = response.getHeaders().entrySet().iterator();

                                                    socketOutputStream.write((iterator.hasNext() ? "\n" : "\r\n\r\n").getBytes());

                                                    while (iterator.hasNext()) {
                                                        Map.Entry<String, List<String>> header = iterator.next();
                                                        for (String headerValue : header.getValue()) {
                                                            String endOfLine = iterator.hasNext() ? "\n" : "\r\n\r\n";
                                                            socketOutputStream.write((header.getKey() + ": " + headerValue + endOfLine).getBytes());
                                                        }
                                                    }

                                                    intermediateOutputStream.writeTo(socketOutputStream);
                                                } catch (IOException ex) {
                                                    LOGGER.log(Level.SEVERE, null, ex);
                                                }

                                                key.interestOps(SelectionKey.OP_WRITE);
                                                selector.wakeup();
                                            }).
                                            exceptionally((Throwable t) -> {
                                                LOGGER.log(Level.SEVERE, "Problem executing request", t);
                                                try {
                                                    connection.getChannel().close();
                                                } catch (IOException ex) {
                                                    LOGGER.log(Level.SEVERE, "Problem closing SocketChannel", ex);
                                                }
                                                return null;
                                            });
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

    private SocketConnection acceptConnection(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel socketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel clientChannel = socketChannel.accept();

        LOGGER.log(Level.INFO, "Connected client: {0}", clientChannel);

        clientChannel.configureBlocking(false);
        SelectionKey newKey = clientChannel.register(selectionKey.selector(), SelectionKey.OP_READ);

        UUID id = UUID.randomUUID();
        newKey.attach(id);

        return new SocketConnection(clientChannel);
    }

    public void setRequestHandler(HttpRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public int getPort() {
        return port;
    }

    public void shutdown() {
        try {
            nioPool.shutdown();
            if (selector != null) {
                selector.close();
            }
            connections.clear();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
