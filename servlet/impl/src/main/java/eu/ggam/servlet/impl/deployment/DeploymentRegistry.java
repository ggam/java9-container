package eu.ggam.servlet.impl.deployment;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author guillermo
 */
public final class DeploymentRegistry {

    private static final Set<Deployment> DEPLOYMENTS = new HashSet<>();

    private static boolean deployed = false;

    private DeploymentRegistry() {
    }

    public static synchronized void registerDeployment(Deployment deployment) {
        if (deployed) {
            throw new IllegalStateException("Applications were already deployed");
        }

        DEPLOYMENTS.add(deployment);
    }

    public static synchronized void deployAll() {
        if (deployed) {
            throw new IllegalStateException("Applications were already deployed");
        }

        deployed = true;
        DEPLOYMENTS.forEach(Deployment::deploy);
    }

    public synchronized static Optional<Deployment> matches(String uri) {
        if (!deployed) {
            throw new IllegalStateException("Applications are still not deployed");
        }
        
        return DEPLOYMENTS.
                stream().
                filter(app -> app.matches(uri)).
                max(Comparator.comparingInt((app) -> app.getContextPath().length()));
    }

}
