package eu.ggam.container.api.event;

import java.util.EventObject;

/**
 *
 * @author guillermo
 */
public abstract class ServerStartingEvent extends EventObject {

    public ServerStartingEvent(Object source) {
        super(source);
    }
}
