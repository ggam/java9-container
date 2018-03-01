package eu.ggam.servlet.impl.core;

import eu.ggam.container.api.http.HttpRequest;
import eu.ggam.container.api.http.HttpRequestHandler;
import eu.ggam.container.api.http.HttpResponse;
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
 * @author guillermo
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
            HttpResponse response;
            try {
                response = DeploymentRegistry.matches(request.getUri().getPath()).
                        get().
                        process(request);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error processing request: ", e);

                StringWriter stackTrace = new StringWriter();
                PrintWriter printer = new PrintWriter(stackTrace);
                e.printStackTrace(printer);

                response = new ContainerHttpResponseImpl();
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
            }
            return response;
        }, executor);
    }

}
