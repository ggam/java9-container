package eu.ggam.container.api.event;

import eu.ggam.container.api.Server;
import java.util.EventObject;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServerStartedEvent extends EventObject {

    public ServerStartedEvent(Server source) {
        super(source);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Server getSource() {
        return (Server) super.getSource();
    }

}
