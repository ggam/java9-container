package eu.ggam.container.impl.lifecycle;

import eu.ggam.container.api.event.ServerStartingEvent;
import eu.ggam.container.impl.Server;

/**
 *
 * @author guillermo
 */
public class ServerStartingEventImpl extends ServerStartingEvent {

    public ServerStartingEventImpl(Server server) {
        super(server);
    }

}
