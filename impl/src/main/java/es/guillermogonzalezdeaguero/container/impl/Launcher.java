package es.guillermogonzalezdeaguero.container.impl;

import es.guillermogonzalezdeaguero.container.impl.server.Server;
import java.io.IOException;

/**
 *
 * @author guillermo
 */
public class Launcher {

    public static void main(String[] args) throws IOException {
        new Server(8282).start();
    }
}
