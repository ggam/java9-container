package eu.ggam.container.api.http;

import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Guillermo González de Agüero
 */
public interface HttpRequest {

    /**
     * HTTP Method
     *
     * @return
     */
    String getMethod();

    /**
     * Requested URI
     *
     * @return
     */
    URI getUri();

    /**
     * HTTP Request headers
     *
     * @return
     */
    Map<String, List<String>> getHeaders();

    /**
     * Input stream for the body of POST requests. Will return
     * {@link Optional#empty()} when {@link #getMethod()} returns a HTTP method
     * which does not accept a message body.
     *
     * @return
     */
    Optional<InputStream> getInputStream();
}
