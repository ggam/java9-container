package eu.ggam.container.impl;

import eu.ggam.container.api.Server;
import eu.ggam.container.impl.connection.HttpServer;
import eu.ggam.container.impl.servletcontainer.core.ServletEngine;
import eu.ggam.jlink.launcher.spi.WebAppModule;
import java.io.IOException;
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
    private final ServletEngine servletEngine;
    private final HttpServer httpServer;

    public ServerImpl(WebAppModule module, Configuration config) {
        this.config = config;
        this.servletEngine = new ServletEngine(module);

        int port = config.getInteger(Configuration.Option.PORT);
        this.httpServer = new HttpServer(port);

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

        LOGGER.log(Level.INFO, "\n============================\nStarting server on port {0}", httpServer.getPort());

        httpServer.beginService();

        changeState(State.RUNNING);

        servletEngine.start().
                thenAccept(httpServer::setRequestHandler).
                exceptionally(t -> {
                    t.printStackTrace();
                    stop();
                    return null;
                });
    }

    @Override
    public void stop() {
        LOGGER.info("*** SHUTTING DOWN ***");
        changeState(State.STOPPING);

        httpServer.shutdown();
        servletEngine.stop();
        changeState(State.STOPPED);
    }

    @Override
    public State getState() {
        return state;
    }

}
