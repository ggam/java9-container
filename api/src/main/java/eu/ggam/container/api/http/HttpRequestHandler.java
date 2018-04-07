package eu.ggam.container.api.http;

import eu.ggam.container.api.Server;
import java.io.IOException;
import java.util.concurrent.CompletionStage;

/**
 * Interface for request handlers. Implementations of this class will be
 * discovered by {@link Server} via the ServiceLoader API. One and only one
 * implementation must be available at runtime.
 *
 * Calling {@link Server#start()} with none or multiple implementations of this
 * interface discoverable by the ServerLoader API will result in an exception.
 *
 * @author Guillermo González de Agüero
 */
public interface HttpRequestHandler {

    /**
     * Processes the {@link HttpRequest} and returns a {@link HttpResponse}.
     * Implementations are free to use multithreading and return an async
     * {@link CompletionStage} or block until the response is complete returning
     * an already completed {@link CompletionStage}.
     *
     * @param request
     * @return Response to be sent to the user
     * @throws IOException
     */
    CompletionStage<? extends HttpResponse> handle(HttpRequest request) throws IOException;
}
