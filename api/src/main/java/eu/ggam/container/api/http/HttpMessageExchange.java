package eu.ggam.container.api.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 *
 * @author guillermo
 */
public interface HttpMessageExchange {

    String getRequestMethod();

    URI getRequestUri();

    Map<String, List<String>> getRequestHeaders();

    InputStream getInputStream();

    void setResponseHeaders(Map<String, List<String>> responseHeaders);

    Map<String, List<String>> getResponseHeaders();

    OutputStream getOutputStream();
    
    void setResponseStatus(int status);
    
    int getResponseStatus();
}
