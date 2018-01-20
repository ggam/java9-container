package es.guillermogonzalezdeaguero.container.api.deployment;

import java.util.Set;
import es.guillermogonzalezdeaguero.container.api.Deployment;

/**
 *
 * @author guillermo
 */
public interface DeploymentRegistry {

    public Set<Deployment> getDeployments();
}
