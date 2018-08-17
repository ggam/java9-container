package eu.ggam.container.impl;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 *
 * @author Guillermo González de Agüero
 */
public class Launcher {

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();

        try (Reader reader = Files.newBufferedReader(Paths.get("conf").resolve("app.properties"))) {
            properties.load(reader);
        }
        int port = Integer.parseInt(String.valueOf(properties.getOrDefault("port", 8383)));

        new ServerImpl(port).start();
    }
}
