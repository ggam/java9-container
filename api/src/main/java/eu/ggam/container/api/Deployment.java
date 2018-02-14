package eu.ggam.container.api;

import eu.ggam.container.api.http.HttpRequest;
import eu.ggam.container.api.http.HttpResponse;

/**
 *
 * @author guillermo
 */
public interface Deployment {

    void deploy();

    boolean matches(String url);

    void process(HttpRequest request, HttpResponse response) throws Exception;
    
    String getContextPath();
}
