package es.guillermogonzalezdeaguero.container.impl.server.event;

import es.guillermogonzalezdeaguero.container.api.ServletDeployment;
import es.guillermogonzalezdeaguero.container.api.event.ServerStartingEvent;
import es.guillermogonzalezdeaguero.container.impl.server.Server;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author guillermo
 */
public class ServerStartingEventImpl extends ServerStartingEvent {

    private static final Class<?> DESIRED_DEPLOYMENTS_LIST_CLASS = HashSet.class;
    
    private final Set<ServletDeployment> deployments;

    /**
     * 
     * @param deployments Mutable list where deployments will be registered
     */
    public ServerStartingEventImpl(Server server, HashSet<ServletDeployment> deployments) {
        super(server);
        if(!deployments.getClass().equals(DESIRED_DEPLOYMENTS_LIST_CLASS)) {
            throw new IllegalArgumentException("Deployments variable must be of class " + DESIRED_DEPLOYMENTS_LIST_CLASS.getName() + ". " + deployments.getClass() + " received.");
        }
        this.deployments = deployments;
    }

    @Override
    public void registerDeployment(ServletDeployment deployment) {
        deployments.add(deployment);
    }

}
