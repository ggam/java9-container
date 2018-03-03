package eu.ggam.container.api.event;

import java.util.EventListener;

/**
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
