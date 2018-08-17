package eu.ggam.servlet.impl.deployer;

import eu.ggam.container.api.event.ServerLifeCycleListener;
import eu.ggam.container.api.event.ServerStartingEvent;
import eu.ggam.container.api.event.ServerStoppingEvent;
import java.util.logging.Logger;

/**
 *
 * @author Guillermo González de Agüero
 */
public class DeploymentScanner implements ServerLifeCycleListener {

    private static final Logger LOGGER = Logger.getLogger(DeploymentScanner.class.getName());

    @Override
    public void serverStarting(ServerStartingEvent serverStartingEvent) {
        DeploymentManager.start();
    }

    @Override
    public void serverStopping(ServerStoppingEvent serverStoppingEvent) {
        DeploymentManager.stop();
    }

}
