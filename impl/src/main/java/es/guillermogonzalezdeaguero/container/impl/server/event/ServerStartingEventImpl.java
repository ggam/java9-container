package es.guillermogonzalezdeaguero.container.impl.server.event;

import es.guillermogonzalezdeaguero.container.api.event.ServerStartingEvent;
import es.guillermogonzalezdeaguero.container.impl.deployment.DeploymentRegistryImpl;
import es.guillermogonzalezdeaguero.container.impl.server.Server;
import es.guillermogonzalezdeaguero.container.api.Deployment;

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
