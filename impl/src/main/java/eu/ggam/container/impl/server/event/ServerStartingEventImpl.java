package eu.ggam.container.impl.server.event;

import eu.ggam.container.api.event.ServerStartingEvent;
import eu.ggam.container.impl.server.Server;

/**
 *
 * @author guillermo
 */
public class ServerStartingEventImpl extends ServerStartingEvent {

    public ServerStartingEventImpl(Server server) {
        super(server);
    }

}
