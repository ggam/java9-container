package eu.ggam.container.impl.server.event;

import eu.ggam.container.api.event.ServerStartingEvent;
import eu.ggam.container.impl.deployment.DeploymentRegistryImpl;
import eu.ggam.container.impl.server.Server;
import eu.ggam.container.api.Deployment;

/**
 *
 * @author guillermo
 */
public class ServerStartingEventImpl extends ServerStartingEvent {

    private final DeploymentRegistryImpl deploymentRegistry;

    /**
     *
     * @param deployments Mutable list where deployments will be registered
     */
    public ServerStartingEventImpl(Server server, DeploymentRegistryImpl deploymentRegistry) {
        super(server);
        this.deploymentRegistry = deploymentRegistry;
    }

    @Override
    public void registerDeployment(Deployment deployment) {
        deploymentRegistry.registerDeployment(deployment);
    }

}
