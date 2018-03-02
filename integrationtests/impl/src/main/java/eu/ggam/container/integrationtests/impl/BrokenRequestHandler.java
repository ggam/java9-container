package eu.ggam.container.integrationtests.impl;

import eu.ggam.container.api.http.HttpRequest;
import eu.ggam.container.api.http.HttpRequestHandler;
import eu.ggam.container.api.http.HttpResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author guillermo
 */
public class BrokenRequestHandler implements HttpRequestHandler {

    @Override
    public CompletionStage<? extends HttpResponse> handle(HttpRequest request) throws IOException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        return CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Error!!");
        }, executor);
    }

}
