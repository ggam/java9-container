package eu.ggam.servlet.impl.deployment.deployment;

import eu.ggam.container.api.event.ServerLifeCycleListener;
import eu.ggam.container.api.event.ServerStartingEvent;
import eu.ggam.servlet.impl.deployment.ServletDeployment;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermo
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
                    forEach(serverStartingEvent::registerDeployment);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
