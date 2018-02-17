package eu.ggam.container.impl.lifecycle;

import eu.ggam.container.api.event.ServerStartedEvent;
import eu.ggam.container.impl.Server;

/**
 *
 * @author guillermo
 */
public class ServerStartedEventImpl extends ServerStartedEvent {

    public ServerStartedEventImpl(Server source) {
        super(source);
    }
}
