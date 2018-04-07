package eu.ggam.container.impl;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 *
 * @author Guillermo González de Agüero
 */
public class Launcher {

    public static void main(String[] args) throws IOException {
        Path baseDir = Paths.get(System.getProperty("server.basedir")).toAbsolutePath();

        Properties properties = new Properties();

        try (Reader reader = Files.newBufferedReader(baseDir.resolve("conf").resolve("app.properties"))) {
            properties.load(reader);
        }
        int port = Integer.valueOf(String.valueOf(properties.getOrDefault("port", 8383)));

        //new Server(port).start();
        new ServerImpl(port).start();
    }
}
