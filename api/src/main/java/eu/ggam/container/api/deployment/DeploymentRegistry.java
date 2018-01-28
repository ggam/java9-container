package eu.ggam.container.api.deployment;

import java.util.Set;
import eu.ggam.container.api.Deployment;

/**
 *
 * @author guillermo
 */
public interface DeploymentRegistry {

    public Set<Deployment> getDeployments();
}
