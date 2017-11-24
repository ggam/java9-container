package es.guillermogonzalezdeaguero.container.conflictiveplugin;

import es.guillermogonzalezdeaguero.container.DuplicatedClass;
import es.guillermogonzalezdeaguero.container.api.Plugin;

/**
 *
 * @author guillermo
 */
public class ConflictivePlugin implements Plugin {

    @Override
    public void run() {
        System.out.println("Running conflictive plugin! And hey, " + new DuplicatedClass().getConflictiveMessage());
    }

}
