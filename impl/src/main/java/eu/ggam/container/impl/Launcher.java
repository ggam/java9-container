package eu.ggam.container.impl;

import eu.ggam.jlink.launcher.spi.ServerLauncher;
import eu.ggam.jlink.launcher.spi.WebAppModule;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 *
 * @author Guillermo González de Agüero
 */
public class Launcher implements ServerLauncher {

    @Override
    public void launch(WebAppModule webAppModule) throws IOException {
        Properties properties = new Properties();

        try (Reader reader = Files.newBufferedReader(Paths.get("conf").resolve("app.properties"))) {
            properties.load(reader);
        }
        Configuration config = new Configuration(properties);

        new ServerImpl(webAppModule, config).start();
    }
}
