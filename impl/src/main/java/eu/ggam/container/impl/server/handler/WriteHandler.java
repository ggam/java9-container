package eu.ggam.container.impl.server.handler;

import eu.ggam.container.impl.http.HttpResponseImpl;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;

/**
 *
 * @author guillermo
 */
public class WriteHandler implements Handler {

    private final Map<SocketChannel, HttpResponseImpl> inflightResponses;

    public WriteHandler(Map<SocketChannel, HttpResponseImpl> inflightResponses) {
        this.inflightResponses = inflightResponses;
    }

    @Override
    public void handle(SelectionKey selectionKey) throws IOException {
        SocketChannel channel = (SocketChannel) selectionKey.channel();

        HttpResponseImpl response = inflightResponses.get(channel);

        HttpResponseImpl.ResponseStatus status = response.write(channel);

        switch (status) {
            case CONNECTION_LOST:
                inflightResponses.remove(channel);
                channel.close();
            case ALL_SENT:
                inflightResponses.remove(channel);
                selectionKey.interestOps(SelectionKey.OP_READ);
                break;
            default:
            // There's some data pending
        }
    }

    @Override
    public boolean handles(int readyOps) {
        return (readyOps & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE;
    }

}
