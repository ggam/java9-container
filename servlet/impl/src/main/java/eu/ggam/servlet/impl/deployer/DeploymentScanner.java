package eu.ggam.servlet.impl.deployer;

import eu.ggam.container.api.event.ServerLifeCycleListener;
import eu.ggam.container.api.event.ServerStartingEvent;
import eu.ggam.container.api.event.ServerStoppingEvent;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
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
        try {
            Files.list(WEBAPPS_PATH).
                    filter(Files::isDirectory).
                    map(p -> new ServletDeployment(ModuleLayer.boot(), p)).
                    peek(deployment -> LOGGER.log(Level.FINE, "Registered deployment for path: {0}", deployment.getContextPath())).
                    forEach(DeploymentRegistry::registerDeployment);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        DeploymentRegistry.deployAll();
    }

    @Override
    public void serverStopping(ServerStoppingEvent serverStoppingEvent) {
        DeploymentRegistry.undeployAll();
    }

}
