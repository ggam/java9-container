package eu.ggam.servlet.impl.core;

import eu.ggam.container.api.event.ServerLifeCycleListener;
import eu.ggam.container.api.event.ServerStartingEvent;
import eu.ggam.container.api.event.ServerStoppingEvent;
import eu.ggam.container.api.http.HttpRequest;
import eu.ggam.container.api.http.HttpRequestHandler;
import eu.ggam.container.api.http.HttpResponse;
import eu.ggam.servlet.impl.api.DeploymentState;
import eu.ggam.servlet.impl.container.ContainerHttpResponseImpl;
import eu.ggam.servlet.impl.deployer.ServletDeployment;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServletContainer implements ServerLifeCycleListener, HttpRequestHandler {

    private static final Logger LOGGER = Logger.getLogger(ServletContainer.class.getName());
    private static final Path WEBAPPS_PATH = Paths.get("..", "webapps");

    private final ExecutorService executor;

    private ServletDeployment deployment;

    public static ServletContainer provider() {
        return new ServletContainer();
    }

    private ServletContainer() {
        executor = new ServletRequestExecutorService();
    }

    @Override
    public void serverStarting(ServerStartingEvent serverStartingEvent) {
        deployment = new ServletDeployment.ServletDeploymentBuilder(ModuleLayer.boot(), WEBAPPS_PATH).
                    deploy();
    }

    @Override
    public CompletionStage<HttpResponse> handle(HttpRequest request) throws IOException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (deployment.getState() == DeploymentState.DEPLOYED) {
                    return deployment.process(request);
                }

                // Application is there, but undeployed. 
                return unavailableResponse();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error processing request: ", e);
                return failedResponse(e);
            }
        }, executor);
    }

    @Override
    public void serverStopping(ServerStoppingEvent serverStoppingEvent) {
        deployment.undeploy();
    }

    private HttpResponse failedResponse(Exception e) {
        StringWriter stackTrace = new StringWriter();
        PrintWriter printer = new PrintWriter(stackTrace);
        e.printStackTrace(printer);

        HttpResponse response = new ContainerHttpResponseImpl();
        response.setStatus(500);

        response.getHeaders().put("Content-Type", List.of("text/html"));
        response.getHeaders().put("Server-name", List.of("localhost"));

        byte[] body = ("Error processing request: " + stackTrace.toString().replaceAll("\n", "<br>")).getBytes();
        response.getHeaders().put("Content-Length", List.of(String.valueOf(body.length)));

        try {
            response.getOutputStream().write(body);
        } catch (IOException e2) {
            throw new UncheckedIOException(e2);
        }

        return response;
    }

    private HttpResponse unavailableResponse() {
        HttpResponse response = new ContainerHttpResponseImpl();
        response.setStatus(503);

        response.getHeaders().put("Content-Type", List.of("text/html"));
        response.getHeaders().put("Server-name", List.of("localhost"));

        byte[] body = "503 - Service Temporarily Unavailable".getBytes();
        response.getHeaders().put("Content-Length", List.of(String.valueOf(body.length)));

        try {
            response.getOutputStream().write(body);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return response;
    }
}
