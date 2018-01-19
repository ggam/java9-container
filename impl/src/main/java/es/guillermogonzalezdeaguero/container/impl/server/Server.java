package es.guillermogonzalezdeaguero.container.impl.server;

import es.guillermogonzalezdeaguero.container.api.event.ServerLifeCycleListener;
import es.guillermogonzalezdeaguero.container.api.event.ServerStartedEvent;
import es.guillermogonzalezdeaguero.container.api.event.ServerStartingEvent;
import es.guillermogonzalezdeaguero.container.impl.deployment.DeploymentRegistry;
import es.guillermogonzalezdeaguero.container.impl.internal.HttpWorkerThreadFactory;
import es.guillermogonzalezdeaguero.container.impl.server.event.ServerStartedEventImpl;
import es.guillermogonzalezdeaguero.container.impl.server.event.ServerStartingEventImpl;
import es.guillermogonzalezdeaguero.container.impl.servlet.FilterChainFactory;
import es.guillermogonzalezdeaguero.container.impl.servlet.HttpServletResponseImpl;
import es.guillermogonzalezdeaguero.container.impl.servlet.PreMatchingHttpServletRequestImpl;
import es.guillermogonzalezdeaguero.container.impl.servlet.ServletResponseToSocket;
import es.guillermogonzalezdeaguero.container.impl.servlet.SocketToServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
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
    private final DeploymentRegistry deploymentRegistry = new DeploymentRegistry();
    private FilterChainFactory uriMatcher;

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

        uriMatcher = new FilterChainFactory(deploymentRegistry);

        while (true) {
            Socket request = serverSocket.accept();
            executor.execute(() -> handleRequest(request));
        }
    }

    private void handleRequest(Socket request) {
        try (request;
                 BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));  PrintWriter out = new PrintWriter(request.getOutputStream(), true)) {

            // Request without
            PreMatchingHttpServletRequestImpl servletRequest = SocketToServletRequest.convert(in);

            FilterChain filterChain = uriMatcher.match(servletRequest.getRequestURI());

            HttpServletResponseImpl servletResponse = new HttpServletResponseImpl();
            filterChain.doFilter(servletRequest, servletResponse);

            ServletResponseToSocket.convert(servletResponse, out);
            LOGGER.log(Level.INFO, "Requested URL {0} - Status: {1}", new Object[]{servletRequest.getRequestURI(), servletResponse.getStatus()});

        } catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, "Error responding to request: ", e);
        }
    }

    private String createResponse(int statusCode, String responseBody) {
        return "HTTP/1.1 " + statusCode + "\n"
                + "Content-type: text/html\n"
                + "Server-name: localhost\n"
                + "Content-length: " + responseBody.length() + "\n"
                + "\n"
                + responseBody;
    }
}
