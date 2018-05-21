package eu.ggam.servlet.impl.core;

import eu.ggam.container.api.http.HttpRequest;
import eu.ggam.container.api.http.HttpRequestHandler;
import eu.ggam.container.api.http.HttpResponse;
import eu.ggam.servlet.impl.api.Deployment;
import eu.ggam.servlet.impl.api.DeploymentState;
import eu.ggam.servlet.impl.container.ContainerHttpResponseImpl;
import eu.ggam.servlet.impl.deployer.DeploymentRegistry;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;
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
public class HttpServletRequestHandler implements HttpRequestHandler {

    private static final Logger LOGGER = Logger.getLogger(HttpServletRequestHandler.class.getName());

    private final ExecutorService executor;

    public HttpServletRequestHandler() {
        executor = new ServletRequestExecutorService();
    }

    @Override
    public CompletionStage<HttpResponse> handle(HttpRequest request) throws IOException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Deployment deployment = DeploymentRegistry.matches(request.getUri().getPath()).
                        get();
                
                if(deployment.getState() == DeploymentState.DEPLOYED) {
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