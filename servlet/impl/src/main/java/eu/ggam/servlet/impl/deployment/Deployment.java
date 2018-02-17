package eu.ggam.servlet.impl.deployment;

import eu.ggam.container.api.http.HttpRequest;
import eu.ggam.container.api.http.HttpResponse;

/**
 *
 * @author guillermo
 */
public interface Deployment {

    void deploy();

    boolean matches(String url);

    HttpResponse process(HttpRequest request) throws Exception;
    
    String getContextPath();
}
