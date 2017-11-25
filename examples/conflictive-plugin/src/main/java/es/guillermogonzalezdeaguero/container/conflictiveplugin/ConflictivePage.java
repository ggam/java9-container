package es.guillermogonzalezdeaguero.container.conflictiveplugin;

import es.guillermogonzalezdeaguero.container.DuplicatedClass;
import es.guillermogonzalezdeaguero.container.api.ServerPage;

/**
 *
 * @author guillermo
 */
public class ConflictivePage implements ServerPage {

    @Override
    public String getUrl() {
        return "/conflictive.html";
    }

    @Override
    public String process() {
        return new DuplicatedClass().getConflictiveMessage();
    }

}
