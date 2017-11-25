package es.guillermogonzalezdeaguero.container.exampleplugin;

import es.guillermogonzalezdeaguero.container.DuplicatedClass;
import es.guillermogonzalezdeaguero.container.api.ServerPage;

/**
 *
 * @author guillermo
 */
public class ExamplePage implements ServerPage {

    @Override
    public String getUrl() {
        return "/example-plugin.html";
    }

    @Override
    public String process() {
        return new DuplicatedClass().getExampleMessage();
    }

}
