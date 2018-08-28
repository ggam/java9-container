package eu.ggam.container.impl;

import eu.ggam.container.api.Server;
import eu.ggam.container.impl.connection.HttpConnectionManager;
import eu.ggam.container.impl.servletcontainer.core.ServletContainer;
import eu.ggam.jlink.launcher.spi.WebAppModule;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServerImpl implements Server {

    private static final Logger LOGGER = Logger.getLogger(ServerImpl.class.getName());

    private State state = State.STOPPED;

    private final Configuration config;
    private final ServletContainer servletContainer;

    public ServerImpl(WebAppModule module, Configuration config) {
        this.config = config;
        this.servletContainer = new ServletContainer(module);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                ServerImpl.this.stop();
            }
        });
    }

    private synchronized void changeState(State newState) {
        // TODO: ensure state change can be done

        State oldState = this.state;

        if (oldState == newState) {
            throw new IllegalStateException("new state cannot be the same as the previous one");
        }

        this.state = newState;

        LOGGER.log(Level.INFO, "Server state changed from {0} to {1}", new Object[]{oldState, this.state});
    }

    @Override
    public void start() throws IOException {
        changeState(State.STARTING);
        servletContainer.start();

        changeState(State.RUNNING);
        Integer port = config.getInteger(Configuration.Option.PORT);
        LOGGER.log(Level.INFO, "\n============================\nServer is running on port {0}", String.valueOf(port));

        final Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();

        serverSocket.bind(new InetSocketAddress("localhost", port));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        HttpConnectionManager connectionManager = new HttpConnectionManager(servletContainer, selector);
        connectionManager.beginService();
    }

    @Override
    public void stop() {
        LOGGER.info("*** SHUTTING DOWN ***");
        changeState(State.STOPPING);
        servletContainer.stop();
        changeState(State.STOPPED);
    }

    @Override
    public State getState() {
        return state;
    }

}
