package es.guillermogonzalezdeaguero.container.impl;

import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 * @author guillermo
 */
public class Launcher {

    public static void main(String[] args) throws IOException {
        new Container(ModuleLayer.boot(), Paths.get("plugins")).
                startServer();
    }
}
