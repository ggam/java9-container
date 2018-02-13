package eu.ggam.container.impl.server.handler;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 *
 * @author guillermo
 */
public interface Handler {

    public void handle(SelectionKey key) throws IOException;

    public boolean handles(int readyOps);
}
