package es.guillermogonzalezdeaguero.container.impl;

import es.guillermogonzalezdeaguero.container.impl.server.Server;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 *
 * @author guillermo
 */
public class Launcher {

    public static void main(String[] args) throws IOException {
        Path baseDir = Paths.get(System.getProperty("server.basedir")).toAbsolutePath();

        Properties properties = new Properties();
        try (InputStream is = new FileInputStream(baseDir.resolve("conf").resolve("app.properties").toFile())) {
            properties.load(is);
        }
        int port = Integer.valueOf(String.valueOf(properties.getOrDefault("port", 8383)));

        new Server(port).start();
    }
}
