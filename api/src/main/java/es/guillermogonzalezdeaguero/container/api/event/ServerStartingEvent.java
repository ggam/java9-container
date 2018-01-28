package eu.ggam.container.api.event;

import java.util.EventObject;
import eu.ggam.container.api.Deployment;

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
