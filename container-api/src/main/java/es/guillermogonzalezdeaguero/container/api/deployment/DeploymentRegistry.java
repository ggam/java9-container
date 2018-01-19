package es.guillermogonzalezdeaguero.container.api.deployment;

import es.guillermogonzalezdeaguero.container.api.ServletDeployment;
import java.util.Set;

/**
 *
 * @author guillermo
 */
public interface DeploymentRegistry {

    public Set<ServletDeployment> getDeployments();
}
