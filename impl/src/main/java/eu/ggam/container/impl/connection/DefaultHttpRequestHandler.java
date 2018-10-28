package eu.ggam.container.impl.connection;

import eu.ggam.container.api.http.HttpRequest;
import eu.ggam.container.api.http.HttpRequestHandler;
import eu.ggam.container.api.http.HttpResponse;
import eu.ggam.container.impl.servletcontainer.container.ContainerHttpResponseImpl;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author Guillermo González de Agüero
 */
public class DefaultHttpRequestHandler implements HttpRequestHandler {

    @Override
    public CompletableFuture<HttpResponse> handle(HttpRequest request) throws IOException {
        return CompletableFuture.completedFuture(unavailableResponse());
    }

    private HttpResponse unavailableResponse() {
        HttpResponse response = new ContainerHttpResponseImpl();
        response.setStatus(503);

        response.getHeaders().put("Content-Type", List.of("text/html"));
        response.getHeaders().put("Server-name", List.of("localhost"));

        byte[] body = "503 - Application is still deploying.".getBytes();
        response.getHeaders().put("Content-Length", List.of(String.valueOf(body.length)));

        try {
            response.getOutputStream().write(body);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return response;
    }

}
