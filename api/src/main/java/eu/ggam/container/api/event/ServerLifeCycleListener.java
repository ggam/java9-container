package eu.ggam.container.api.event;

import eu.ggam.container.api.Server;
import java.util.EventListener;

/**
 * {@link Server} implementations will discover implementations of this
 * interface via the ServiceLoader API. The server implementation will call this
 * methods during state changes
 *
 * Exceptions occurred during the execution of any lifecycle method must be
 * propagated to caller and prevent the change state
 *
 * @author guillermo
 */
public interface ServerLifeCycleListener extends EventListener {

    default void serverStarting(ServerStartingEvent serverStartingEvent) {

    }

    default void serverStarted(ServerStartedEvent serverStartedEvent) {

    }

    default void serverStopping(ServerStoppingEvent serverStoppingEvent) {

    }
}
