package es.guillermogonzalezdeaguero.container.api.event;

import java.util.EventObject;
import es.guillermogonzalezdeaguero.container.api.Deployment;

/**
 *
 * @author guillermo
 */
public abstract class ServerStartingEvent extends EventObject {

    public ServerStartingEvent(Object source) {
        super(source);
    }
    
    public abstract void registerDeployment(Deployment deployment);
}
