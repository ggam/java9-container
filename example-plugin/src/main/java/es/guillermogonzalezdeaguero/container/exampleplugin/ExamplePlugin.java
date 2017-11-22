package es.guillermogonzalezdeaguero.container.exampleplugin;

import es.guillermogonzalezdeaguero.container.api.Plugin;

/**
 *
 * @author guillermo
 */
public class ExamplePlugin implements Plugin {

    @Override
    public void run() {
        System.out.println("Running!");
    }

}
