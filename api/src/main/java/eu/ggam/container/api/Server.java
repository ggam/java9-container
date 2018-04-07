package eu.ggam.container.api;

import java.io.IOException;

/**
 * The interface that must be implemented by complaint servers
 *
 * @author Guillermo González de Agüero
 */
public interface Server {

    /**
     * Possible states for the server. Possible state changes are:
     * <ul>
     * <li>{@link State#STOPPED} to {@link State#STARTING}</li>
     * <li>{@link State#STARTING} to {@link State#RUNNING}</li>
     * <li>{@link State#RUNNING} to {@link State#STOPPING}</li>
     * <li>{@link State#STOPPING} to {@link State#STOPPED}</li>
     * </ul>
     *
     * Implementations must prevent any other state change.
     */
    public enum State {
        STOPPED,
        STARTING,
        RUNNING,
        STOPPING
    }

    /**
     * Initializes the server, changing its state from the default
     * {@link State#STOPPED} to {@link State#STARTING}. After all registered
     * lifecycle listeners are called, state will change to
     * {@link State#RUNNING}.
     *
     * @throws IOException
     */
    void start() throws IOException;

    /**
     * Causes the server to stop. State changes to {@link State#STOPPING} before
     * calling registered lifecycle listeners. State is then changed to
     * {@link State#STOPPED}.
     *
     */
    void stop();

    /**
     * Gets the current state of the server. Default state upon creation must be
     * {@link State#STOPPED}
     *
     * @return current state of the server
     */
    State getState();
}
