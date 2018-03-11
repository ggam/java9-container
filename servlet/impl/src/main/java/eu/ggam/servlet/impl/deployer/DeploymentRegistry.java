package eu.ggam.servlet.impl.deployer;

import eu.ggam.servlet.impl.api.Deployment;
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
    
    public static synchronized void undeployAll() {
        if (!deployed) {
            throw new IllegalStateException("Applications were already deployed");
        }

        deployed = true;
        DEPLOYMENTS.forEach(Deployment::undeploy);
    }

    public static synchronized void undeploy(String moduleName) {
        // There must be only one
        Optional<Deployment> deployment = DEPLOYMENTS.stream().
                filter(d -> d.getModuleName().equals(moduleName)).
                findFirst();

        deployment.orElseThrow(() -> new IllegalArgumentException(moduleName + " not found")).
                undeploy();
    }

    public synchronized static Optional<Deployment> matches(String uri) {
        if (!deployed) {
            throw new IllegalStateException("Applications are still not deployed");
        }

        // Will return all deployments, even undeployed ones
        return DEPLOYMENTS.
                stream().
                filter(app -> app.matches(uri)).
                max(Comparator.comparingInt((app) -> app.getContextPath().length()));
    }

}
