package eu.ggam.container.api.http;

import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 *
 * @author guillermo
 */
public interface HttpRequest {

    String getMethod();

    URI getUri();

    Map<String, List<String>> getHeaders();

    InputStream getInputStream();
}
