package eu.ggam.container.impl.server.event;

import eu.ggam.container.api.event.ServerStartedEvent;
import eu.ggam.container.impl.server.Server;

/**
 *
 * @author guillermo
 */
public class ServerStartedEventImpl extends ServerStartedEvent {

    public ServerStartedEventImpl(Server source) {
        super(source);
    }
}
