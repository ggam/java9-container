package es.guillermogonzalezdeaguero.container.impl;

import es.guillermogonzalezdeaguero.container.impl.server.Server;
import java.io.IOException;

/**
 *
 * @author guillermo
 */
public class Launcher {

    public static void main(String[] args) throws IOException {
        /*Properties properties = new Properties();
        properties.load(Launcher.class.getResourceAsStream("conf/app.properties"));

        int port = Integer.valueOf(String.valueOf(properties.getOrDefault("port", 8383)));*/

        new Server(8282).start();
    }
}
