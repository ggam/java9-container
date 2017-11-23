package es.guillermogonzalezdeaguero.container.impl;

import java.nio.file.Paths;

/**
 *
 * @author guillermo
 */
public class Launcher {

    public static void main(String[] args) {
        new Container(ModuleLayer.boot(), Paths.get("plugins")).
                runAllPlugins();        
    }
}
