package eu.ggam.container.api;

/**
 *
 * @author guillermo
 */
public interface Server {

    public enum State {
        STOPPED,
        STARTING,
        RUNNING,
        STOPPING
    }

    State getState();
}
