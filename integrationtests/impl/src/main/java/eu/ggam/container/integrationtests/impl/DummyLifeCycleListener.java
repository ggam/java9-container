package eu.ggam.container.integrationtests.impl;

import eu.ggam.container.api.event.ServerLifeCycleListener;
import eu.ggam.container.api.event.ServerStartedEvent;
import eu.ggam.container.api.event.ServerStartingEvent;
import eu.ggam.container.api.event.ServerStoppingEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guillermo González de Agüero
 */
public class DummyLifeCycleListener implements ServerLifeCycleListener {

    private static final Logger LOGGER = Logger.getLogger(DummyLifeCycleListener.class.getName());

    @Override
    public void serverStarting(ServerStartingEvent event) {
        LOGGER.log(Level.INFO, "{0} Server starting. Current state: {1}", new Object[]{
            event.getClass().getName(),
            event.getSource().getState()});
    }

    @Override
    public void serverStarted(ServerStartedEvent event) {
        LOGGER.log(Level.INFO, "{0} Server started. Current state: {1}", new Object[]{
            event.getClass().getName(),
            event.getSource().getState()});
    }

    @Override
    public void serverStopping(ServerStoppingEvent event) {
        LOGGER.log(Level.INFO, "{0} Server stopping. Current state: {1}", new Object[]{
            event.getClass().getName(),
            event.getSource().getState()});
    }

}
