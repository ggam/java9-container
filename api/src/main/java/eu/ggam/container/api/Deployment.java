package eu.ggam.container.api;

import eu.ggam.container.api.http.HttpMessageExchange;

/**
 *
 * @author guillermo
 */
public interface Deployment {

    void deploy();

    boolean matches(String url);

    void process(HttpMessageExchange httpMessageExchange) throws Exception;
    
    String getContextPath();
}
