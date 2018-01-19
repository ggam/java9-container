package es.guillermogonzalezdeaguero.container.api;

import es.guillermogonzalezdeaguero.container.api.deployment.DeploymentRegistry;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;

/**
 *
 * @author guillermo
 */
public interface RequestProcessor {

    public void process(InputStream input, OutputStream output) throws IOException, ServletException;

    public void setDeploymentRegistry(DeploymentRegistry deploymentRegistry);

}
