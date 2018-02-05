package eu.ggam.container.impl.server;

import eu.ggam.container.api.Deployment;
import eu.ggam.container.api.event.ServerLifeCycleListener;
import eu.ggam.container.api.event.ServerStartedEvent;
import eu.ggam.container.api.event.ServerStartingEvent;
import eu.ggam.container.api.http.HttpMessageExchange;
import eu.ggam.container.impl.deployment.DeploymentRegistryImpl;
import eu.ggam.container.impl.http.HttpMessageExchangeImpl;
import eu.ggam.container.impl.http.HttpRequestInputStream;
import eu.ggam.container.impl.http.HttpResponseOutputStream;
import eu.ggam.container.impl.server.event.ServerStartedEventImpl;
import eu.ggam.container.impl.server.event.ServerStartingEventImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermo
 */
public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private ServerState state = ServerState.STOPPED;
    private final int port;
    private final List<ServerLifeCycleListener> lifeCycleListeners;

    // Server already running
    private final DeploymentRegistryImpl deploymentRegistry = new DeploymentRegistryImpl();

    public Server(int port) {
        this.port = port;

        this.lifeCycleListeners = new ArrayList<>();
        ServiceLoader.load(ServerLifeCycleListener.class).
                iterator().
                forEachRemaining(lifeCycleListeners::add);
    }

    private synchronized void changeState(ServerState newState) {
        this.state = newState;

        switch (newState) {
            case STARTING:
                ServerStartingEvent startingEvent = new ServerStartingEventImpl(this, deploymentRegistry);
                lifeCycleListeners.forEach(listener -> listener.serverStarting(startingEvent));
                break;
            case RUNNING:
                ServerStartedEvent startedEvent = new ServerStartedEventImpl(this);
                lifeCycleListeners.forEach(listener -> listener.serverStarted(startedEvent));
                break;
        }

        LOGGER.log(Level.INFO, "Server state changed from {0} to {1}", new Object[]{this.state, newState});
    }

    public void start() throws IOException {
        changeState(ServerState.STARTING);

        //ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new HttpWorkerThreadFactory());
        deploymentRegistry.deployAll();

        changeState(ServerState.RUNNING);
        LOGGER.log(Level.INFO, "\n============================\nServer is running on port {0}", String.valueOf(port));

        try (final Selector selector = Selector.open();
                ServerSocketChannel serverSocket = ServerSocketChannel.open()) {

            serverSocket.bind(new InetSocketAddress("localhost", port));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

            LOGGER.log(Level.INFO, "Listening on port {0}", String.valueOf(port));
            while (true) {
                if (selector.select() != 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();

                        if (key.isAcceptable()) {
                            SocketChannel clientSocket = ((ServerSocketChannel) key.channel()).accept();
                            clientSocket.configureBlocking(false);
                            clientSocket.register(selector, SelectionKey.OP_READ, new ArrayList<ByteBuffer>());
                        } else if (key.isReadable()) {
                            SocketChannel clientSocket = (SocketChannel) key.channel();
                            int inputBufferSize = clientSocket.socket().getReceiveBufferSize();
                            int outputBufferSize = clientSocket.socket().getSendBufferSize();
                            ByteBuffer allocate = ByteBuffer.allocate(clientSocket.socket().getReceiveBufferSize());
                            int read = clientSocket.read(allocate);

                            List<ByteBuffer> list = (List<ByteBuffer>) key.attachment();
                            if (read == -1 && list.isEmpty()) {
                                // Nothing has been read. Perhaps a closed keep alive connection?
                                continue;
                            }
                            list.add(allocate);

                            if (read < inputBufferSize) {
                                String lastRead = new String(allocate.array());
                                int length = lastRead.length();
                                if (read == -1 || "".equals(lastRead.substring(length - 3, length - 1).trim())) {
                                    HttpResponseOutputStream output = new HttpResponseOutputStream(outputBufferSize);
                                    HttpRequestInputStream input = new HttpRequestInputStream(list);

                                    HttpMessageExchangeImpl httpMessage = HttpMessageExchangeImpl.of(input, output);
                                    
                                    handleRequest(httpMessage);
                                    clientSocket.register(selector, SelectionKey.OP_WRITE, httpMessage);
                                }
                            }
                        } else if (key.isWritable()) {
                            SocketChannel clientSocket = (SocketChannel) key.channel();

                            HttpMessageExchangeImpl httpMessage = (HttpMessageExchangeImpl) key.attachment();

                            ByteBuffer orElse = httpMessage.getNextResponseBuffer().orElse(null);
                            if (orElse != null) {
                                clientSocket.write(orElse);
                            } else {
                                //clientSocket.close();
                                clientSocket.register(selector, SelectionKey.OP_READ, new ArrayList<ByteBuffer>());
                                httpMessage.close();
                            }
                        }
                    }
                }
            }
        }
    }

    private void handleRequest(HttpMessageExchange httpMessage) throws IOException {
        try {
            Deployment deployment = deploymentRegistry.getDeployments().
                    stream().
                    filter(app -> app.matches(httpMessage.getRequestUri().getPath())).
                    max(Comparator.comparingInt((app) -> app.getContextPath().length())).
                    get();

            deployment.process(httpMessage);
        } catch (Exception e) {

            LOGGER.log(Level.SEVERE, "Error processing request: ", e);
            
            StringWriter stackTrace = new StringWriter();
            PrintWriter printer = new PrintWriter(stackTrace);
            e.printStackTrace(printer);
            httpMessage.setResponseStatus(500);
            httpMessage.getResponseHeaders().put("Content-Type", List.of("text/html"));
            httpMessage.getResponseHeaders().put("Server-name", List.of("localhost"));
            httpMessage.getOutputStream().write(("Error processing request: " + stackTrace.toString().replaceAll("\n", "<br>")).getBytes());
        }
    }
}
