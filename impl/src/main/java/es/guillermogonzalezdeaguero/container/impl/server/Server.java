package es.guillermogonzalezdeaguero.container.impl.server;

import es.guillermogonzalezdeaguero.container.impl.internal.HttpWorkerThreadFactory;
import es.guillermogonzalezdeaguero.container.impl.server.deployment.WebApplication;
import es.guillermogonzalezdeaguero.container.impl.servlet.HttpServletResponseImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author guillermo
 */
public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private static final Path WEBAPPS_PATH = Paths.get("..", "webapps");

    private ServerState state = ServerState.STOPPED;
    private final int port;
    private final DeploymentScanner deploymentScanner;

    // Server already running
    private Set<WebApplication> webApps = new HashSet<>();
    private UriMatcher uriMatcher;

    public Server(int port) {
        this.port = port;
        this.deploymentScanner = new DeploymentScanner(WEBAPPS_PATH);
    }

    private synchronized void changeState(ServerState newState) {
        LOGGER.log(Level.INFO, "Server state changed from {0} to {1}", new Object[]{this.state, newState});
        this.state = newState;
    }

    public void start() throws IOException {
        changeState(ServerState.STARTING);

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new HttpWorkerThreadFactory());

        ServerSocket serverSocket = new ServerSocket(port);

        LOGGER.log(Level.INFO, "Listening on port {0}", String.valueOf(port));

        deploymentScanner.startScanning(webApps::add);

        webApps.forEach(WebApplication::deploy);

        changeState(ServerState.RUNNING);
        LOGGER.log(Level.INFO, "\n============================\nServer is running on port {0}", String.valueOf(port));

        uriMatcher = new UriMatcher(webApps);

        while (true) {
            Socket request = serverSocket.accept();
            executor.execute(() -> handleRequest(request));
        }
    }

    private void handleRequest(Socket request) {
        try (request;
                BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
                PrintWriter out = new PrintWriter(request.getOutputStream(), true)) {

            HttpServletRequest servletRequest = SocketToServletRequest.convert(in);

            Optional<FilterChain> filterChain = uriMatcher.match(servletRequest.getRequestURI());

            if (!filterChain.isPresent()) {
                out.write(createResponse(404, "404 - Not found"));
                LOGGER.log(Level.INFO, "Requested URL {0} - Status: 404", servletRequest);
            } else {
                HttpServletResponseImpl servletResponse = new HttpServletResponseImpl();

                filterChain.get().doFilter(servletRequest, servletResponse);

                ServletResponseToSocket.convert(servletResponse, out);

                LOGGER.log(Level.INFO, "Requested URL {0} - Status: {1}", new Object[]{servletRequest.getRequestURI(), servletResponse.getStatus()});
            }
        } catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, "Error responding to request: ", e);
        } catch(NullPointerException e) {
            System.out.println("Aquí se queda todo" + Thread.currentThread().getUncaughtExceptionHandler());
            throw e;
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
