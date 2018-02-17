package eu.ggam.container.api.http;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

/**
 *
 * @author guillermo
 */
public interface HttpRequestHandler {

    CompletionStage<? extends HttpResponse> handle(HttpRequest request) throws IOException;
}
