package es.guillermogonzalezdeaguero.container.impl.deployment;

import es.guillermogonzalezdeaguero.container.api.deployment.DeploymentRegistry;
import java.util.HashSet;
import java.util.Set;
import es.guillermogonzalezdeaguero.container.api.Deployment;

/**
 *
 * @author guillermo
 */
public class DeploymentRegistryImpl implements DeploymentRegistry {

    private final Set<Deployment> deployments = new HashSet<>();

    private boolean deployed = false;

    public void registerDeployment(Deployment deployment) {
        deployments.add(deployment);
    }

    public synchronized void deployAll() {
        if (deployed) {
            throw new IllegalStateException("Applications were already deployed");
        }

        deployed = true;
        deployments.forEach(Deployment::deploy);
    }

    public Set<Deployment> getDeployments() {
        return new HashSet<>(deployments);
    }

}
