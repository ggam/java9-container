package eu.ggam.container.api.event;

import eu.ggam.container.api.Server;
import java.util.EventObject;

/**
 *
 * @author guillermo
 */
public class ServerStoppingEvent extends EventObject {

    public ServerStoppingEvent(Server source) {
        super(source);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Server getSource() {
        return (Server) super.getSource();
    }
}
