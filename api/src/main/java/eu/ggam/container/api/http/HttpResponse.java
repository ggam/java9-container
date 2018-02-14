package eu.ggam.container.api.http;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 *
 * @author guillermo
 */
public interface HttpResponse {

    void setHeaders(Map<String, List<String>> headers);

    Map<String, List<String>> getHeaders();

    OutputStream getOutputStream();

    void setStatus(int status);

    int getResponseStatus();
}
