package eu.ggam.container.impl.servletcontainer.core;

import eu.ggam.container.api.http.HttpRequest;
import eu.ggam.container.api.http.HttpRequestHandler;
import eu.ggam.container.api.http.HttpResponse;
import eu.ggam.container.impl.servletcontainer.api.DeploymentState;
import eu.ggam.container.impl.servletcontainer.container.ContainerHttpResponseImpl;
import eu.ggam.container.impl.servletcontainer.deployer.ServletDeployment;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServletRequestHandler implements HttpRequestHandler {

    private static final Logger LOGGER = Logger.getLogger(ServletRequestHandler.class.getName());

    private final ExecutorService executor;
    private final ServletDeployment deployment;

    public ServletRequestHandler(ExecutorService executor, ServletDeployment deployment) {
        this.executor = executor;
        this.deployment = deployment;
    }

    @Override
    public CompletableFuture<HttpResponse> handle(HttpRequest request) throws IOException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (deployment.getState() != DeploymentState.DEPLOYED) {
                    // Application is there, but undeployed. 
                    return unavailableResponse();
                }

                return deployment.process(request);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error processing request: ", e);
                return failedResponse(e);
            }
        }, executor);
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
