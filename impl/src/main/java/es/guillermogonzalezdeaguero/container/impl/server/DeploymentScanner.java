package es.guillermogonzalezdeaguero.container.impl.server;

import es.guillermogonzalezdeaguero.container.impl.server.deployment.WebApplication;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

/**
 *
 * @author guillermo
 */
public class DeploymentScanner {

    private final Path path;

    public DeploymentScanner(Path path) {
        this.path = path;
    }

    // TODO: move to a dedicated thread and make it a daemon
    public void startScanning(Consumer<WebApplication> callback) {
        try {
            Files.list(path).
                    filter(Files::isDirectory).
                    map(p -> new WebApplication(ModuleLayer.boot(), p)).
                    forEach(callback);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
