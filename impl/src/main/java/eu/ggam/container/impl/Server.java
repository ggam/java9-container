package eu.ggam.container.impl;

import eu.ggam.container.api.event.ServerLifeCycleListener;
import eu.ggam.container.api.event.ServerStartedEvent;
import eu.ggam.container.api.event.ServerStartingEvent;
import eu.ggam.container.api.http.HttpRequestHandler;
import eu.ggam.container.impl.connection.ConnectionManager;
import eu.ggam.container.impl.lifecycle.ServerStartedEventImpl;
import eu.ggam.container.impl.lifecycle.ServerStartingEventImpl;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author guillermo
 */
public class Server {

    public enum State {
        STOPPED,
        STARTING,
        RUNNING,
        STOPPING
    }

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private State state = State.STOPPED;
    private final int port;
    private final List<ServerLifeCycleListener> lifeCycleListeners;

    private HttpRequestHandler requestHandler;

    public Server(int port) {
        this.port = port;

        this.lifeCycleListeners = new ArrayList<>();
        ServiceLoader.load(ServerLifeCycleListener.class).
                iterator().
                forEachRemaining(lifeCycleListeners::add);

        List<ServiceLoader.Provider<HttpRequestHandler>> requestHandlers = ServiceLoader.load(HttpRequestHandler.class).
                stream().
                collect(toList());
        if (requestHandlers.size() != 1) {
            throw new IllegalStateException("There must be exactly one " + HttpRequestHandler.class.getSimpleName() + ". (" + requestHandlers.size() + " found)");
        }
        requestHandler = requestHandlers.iterator().next().get();
    }

    private synchronized void changeState(State newState) {
        this.state = newState;

        switch (newState) {
            case STARTING:
                ServerStartingEvent startingEvent = new ServerStartingEventImpl(this);
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
        changeState(State.STARTING);

        //ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new HttpWorkerThreadFactory());
        changeState(State.RUNNING);
        LOGGER.log(Level.INFO, "\n============================\nServer is running on port {0}", String.valueOf(port));

        final Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();

        serverSocket.bind(new InetSocketAddress("localhost", port));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        ConnectionManager connectionManager = new ConnectionManager(requestHandler, selector);
        connectionManager.beginService();
    }
}
