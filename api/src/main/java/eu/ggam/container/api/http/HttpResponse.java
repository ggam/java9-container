package eu.ggam.container.api.http;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
 * The representation of the HTTP response to be sent to the client.
 *
 * No changes will be allowed on the headers or status once the server has
 * started to send the response to the client.
 *
 * @author guillermo
 */
public interface HttpResponse {

    /**
     * Discards current headers and set the new ones
     *
     * @param headers
     */
    void setHeaders(Map<String, List<String>> headers);

    /**
     * Returns a modifiable map of headers. Changes done on the returned map
     * will directly affect the response.
     *
     * @return Modifiable map of headers
     */
    Map<String, List<String>> getHeaders();

    /**
     * Output stream for the response body
     *
     * @return
     */
    ByteArrayOutputStream getOutputStream();

    /**
     * Discards the current HTTP status to set the new one
     *
     * @param status
     */
    void setStatus(int status);

    /**
     *
     * @return current HTTP status
     */
    int getStatus();
}
