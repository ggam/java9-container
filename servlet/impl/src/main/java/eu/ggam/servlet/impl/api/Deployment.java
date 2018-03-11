package eu.ggam.servlet.impl.api;

import eu.ggam.container.api.http.HttpRequest;
import eu.ggam.container.api.http.HttpResponse;
import java.io.IOException;
import javax.servlet.ServletException;

/**
 *
 * @author guillermo
 */
public interface Deployment {

    void deploy();

    public void undeploy();

    public boolean matches(String url);

    public HttpResponse process(HttpRequest containerRequest) throws IOException, ServletException;
    
    public String getModuleName();
    
    public String getContextPath();
    
    public DeploymentState getState();
}
