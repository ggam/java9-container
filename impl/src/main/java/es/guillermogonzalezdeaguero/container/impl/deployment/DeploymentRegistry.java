package es.guillermogonzalezdeaguero.container.impl.deployment;

import es.guillermogonzalezdeaguero.container.api.ServletDeployment;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author guillermo
 */
public class DeploymentRegistry {

    private final Set<ServletDeployment> deployments = new HashSet<>();

    private boolean deployed = false;

    public void registerDeployment(ServletDeployment deployment) {
        deployments.add(deployment);
    }

    public synchronized void deployAll() {
        if (deployed) {
            throw new IllegalStateException("Applications were already deployed");
        }

        deployed = true;
        deployments.forEach(ServletDeployment::deploy);
    }

    public Set<ServletDeployment> getDeployments() {
        return new HashSet<>(deployments);
    }

}
