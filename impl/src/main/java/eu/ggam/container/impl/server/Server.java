package eu.ggam.container.impl.server;

import eu.ggam.container.api.Deployment;
import eu.ggam.container.api.event.ServerLifeCycleListener;
import eu.ggam.container.api.event.ServerStartedEvent;
import eu.ggam.container.api.event.ServerStartingEvent;
import eu.ggam.container.impl.deployment.DeploymentRegistryImpl;
import eu.ggam.container.impl.internal.HttpWorkerThreadFactory;
import eu.ggam.container.impl.server.event.ServerStartedEventImpl;
import eu.ggam.container.impl.server.event.ServerStartingEventImpl;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.PushbackInputStream;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new HttpWorkerThreadFactory());

        ServerSocket serverSocket = new ServerSocket(port);

        LOGGER.log(Level.INFO, "Listening on port {0}", String.valueOf(port));

        deploymentRegistry.deployAll();

        changeState(ServerState.RUNNING);
        LOGGER.log(Level.INFO, "\n============================\nServer is running on port {0}", String.valueOf(port));

        while (true) {
            Socket request = serverSocket.accept();
            executor.execute(() -> {
                try (request) {
                    handleRequest(request);
                } catch (IOException ex) {
                    throw new UncheckedIOException(ex);
                }
            });
        }
    }

    private void handleRequest(Socket request) throws IOException {
        try ( PushbackInputStream inputStream = new PushbackInputStream(request.getInputStream(), 2000); // 2000 character buffer for URL
                  OutputStream outputStream = request.getOutputStream()) {
            try {
                String readString = "";
                byte[] buffer = new byte[16];
                while (inputStream.read(buffer, 0, buffer.length) != -1) {
                    readString += new String(buffer);
                    if (readString.contains("\n")) {
                        break;
                    }
                }

                inputStream.unread(readString.getBytes());
                String uri = readString.split("\n")[0].split(" ")[1].split("\\?")[0];

                Deployment deployment = deploymentRegistry.getDeployments().
                        stream().
                        filter(app -> app.matches(uri)).
                        max(Comparator.comparingInt((app) -> app.getContextPath().length())).
                        get();

                deployment.process(inputStream, outputStream);
            } catch (Exception e) {
                StringWriter stackTrace = new StringWriter();
                PrintWriter printer = new PrintWriter(stackTrace);
                e.printStackTrace(printer);

                outputStream.write(("HTTP/1.1 500\n"
                        + "Content-type: text/html\n"
                        + "Server-name: localhost\n"
                        + "\n"
                        + "Error processing request: " + stackTrace.toString().replaceAll("\n", "<br>")).getBytes());

                LOGGER.log(Level.SEVERE, "Error processing request: ", e);
            }
        }
    }
}
