package es.guillermogonzalezdeaguero.container.api.event;

import java.util.EventObject;

/**
 *
 * @author guillermo
 */
public abstract class ServerStartedEvent extends EventObject {

    public ServerStartedEvent(Object source) {
        super(source);
    }

}
