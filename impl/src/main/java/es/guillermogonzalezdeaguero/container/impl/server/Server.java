package es.guillermogonzalezdeaguero.container.impl.server;

import es.guillermogonzalezdeaguero.container.api.RequestProcessor;
import es.guillermogonzalezdeaguero.container.api.event.ServerLifeCycleListener;
import es.guillermogonzalezdeaguero.container.api.event.ServerStartedEvent;
import es.guillermogonzalezdeaguero.container.api.event.ServerStartingEvent;
import es.guillermogonzalezdeaguero.container.impl.deployment.DeploymentRegistryImpl;
import es.guillermogonzalezdeaguero.container.impl.internal.HttpWorkerThreadFactory;
import es.guillermogonzalezdeaguero.container.impl.server.event.ServerStartedEventImpl;
import es.guillermogonzalezdeaguero.container.impl.server.event.ServerStartingEventImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;

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
    private RequestProcessor requestProcessor;

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

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new HttpWorkerThreadFactory());

        ServerSocket serverSocket = new ServerSocket(port);

        LOGGER.log(Level.INFO, "Listening on port {0}", String.valueOf(port));

        deploymentRegistry.deployAll();

        changeState(ServerState.RUNNING);
        LOGGER.log(Level.INFO, "\n============================\nServer is running on port {0}", String.valueOf(port));

        Iterator<RequestProcessor> iterator = ServiceLoader.load(RequestProcessor.class).iterator();

        requestProcessor = iterator.next();

        if (iterator.hasNext()) {
            throw new IllegalStateException("Only one processor at a time is allowed");
        }

        requestProcessor.setDeploymentRegistry(deploymentRegistry);

        while (true) {
            Socket request = serverSocket.accept();
            executor.execute(() -> handleRequest(request));
        }
    }

    private void handleRequest(Socket request) {
        try(request) {
            requestProcessor.process(request.getInputStream(), request.getOutputStream());
        } catch (IOException | ServletException e) {
            try {
                PrintWriter stackTrace = new PrintWriter(new StringWriter());
                e.printStackTrace(stackTrace);

                new PrintWriter(request.getOutputStream()).write(
                        "HTTP/1.1 500\n"
                        + "Content-type: text/html\n"
                        + "Server-name: localhost\n"
                        + "\n"
                        + "Error processing request: " + stackTrace.toString());
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }

            LOGGER.log(Level.SEVERE, "Error processing request: ", e);
        }
    }
}
