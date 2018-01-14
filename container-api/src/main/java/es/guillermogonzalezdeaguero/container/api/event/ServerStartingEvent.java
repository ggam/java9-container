package es.guillermogonzalezdeaguero.container.api.event;

import es.guillermogonzalezdeaguero.container.api.ServletDeployment;
import java.util.EventObject;

/**
 *
 * @author guillermo
 */
public abstract class ServerStartingEvent extends EventObject {

    public ServerStartingEvent(Object source) {
        super(source);
    }
    
    public abstract void registerDeployment(ServletDeployment deployment);
}
