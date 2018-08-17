package eu.ggam.servlet.impl.deployer;

import eu.ggam.container.api.event.ServerLifeCycleListener;
import eu.ggam.container.api.event.ServerStartingEvent;
import eu.ggam.container.api.event.ServerStoppingEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 *
 * @author Guillermo González de Agüero
 */
public class DeploymentScanner implements ServerLifeCycleListener {

    private static final Path WEBAPPS_PATH = Paths.get("..", "webapps");

    private static final Logger LOGGER = Logger.getLogger(DeploymentScanner.class.getName());

    @Override
    public void serverStarting(ServerStartingEvent serverStartingEvent) {
        DeploymentRegistry.registerDeployment(new ServletDeployment(ModuleLayer.boot(), WEBAPPS_PATH));

        DeploymentRegistry.deployAll();
    }

    @Override
    public void serverStopping(ServerStoppingEvent serverStoppingEvent) {
        DeploymentRegistry.undeployAll();
    }

}
