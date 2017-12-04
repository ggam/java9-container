package es.guillermogonzalezdeaguero.container.impl.server;

import es.guillermogonzalezdeaguero.container.impl.internal.HttpWorkerThreadFactory;
import es.guillermogonzalezdeaguero.container.impl.server.deployment.WebApplication;
import es.guillermogonzalezdeaguero.container.impl.servlet.HttpServletRequestImpl;
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
    private UrlMatcher urlMatcher;

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

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        ServerSocket serverSocket = new ServerSocket(port);

        LOGGER.log(Level.INFO, "Listening on port {0}", String.valueOf(port));

        deploymentScanner.startScanning(webApps::add);

        webApps.forEach(WebApplication::deploy);

        changeState(ServerState.RUNNING);
        LOGGER.log(Level.INFO, "\n============================\nServer is running on port {0}", String.valueOf(port));

        urlMatcher = new UrlMatcher(webApps);

        while (true) {
            Socket request = serverSocket.accept();
            executor.submit(() -> handleRequest(request), new HttpWorkerThreadFactory());
        }
    }

    private void handleRequest(Socket request) {
        try (request;
                BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
                PrintWriter out = new PrintWriter(request.getOutputStream(), true)) {

            String url = in.readLine().split("GET ")[1].split(" HTTP")[0];

            Optional<FilterChain> filterChain = urlMatcher.match(url);

            if (!filterChain.isPresent()) {
                out.print(createResponse(404, "404 - Not found"));
                LOGGER.log(Level.INFO, "Requested URL {0} - Status: 404", url);
            } else {
                HttpServletResponseImpl response = new HttpServletResponseImpl(out);
                filterChain.get().doFilter(new HttpServletRequestImpl(), response);
                LOGGER.log(Level.INFO, "Requested URL {0} - Status: {1}", new Object[]{url, response.getStatus()});
            }
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
