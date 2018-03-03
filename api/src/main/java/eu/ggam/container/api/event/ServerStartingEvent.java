package eu.ggam.container.api.event;

import eu.ggam.container.api.Server;
import java.util.EventObject;

/**
 *
 * @author guillermo
 */
public class ServerStartingEvent extends EventObject {

    public ServerStartingEvent(Server source) {
        super(source);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Server getSource() {
        return (Server) super.getSource();
    }
}
