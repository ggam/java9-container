package eu.ggam.container.impl.server.handler;

import eu.ggam.container.impl.RequestHandler;
import eu.ggam.container.impl.http.HttpRequestBuilder;
import eu.ggam.container.impl.http.HttpResponseImpl;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;

/**
 *
 * @author guillermo
 */
public class ReadHandler implements Handler {

    private final Map<SocketChannel, HttpRequestBuilder> inflightRequests;
    private final Map<SocketChannel, HttpResponseImpl> inflightResponses;

    private final RequestHandler requestHandler;

    public ReadHandler(Map<SocketChannel, HttpRequestBuilder> inflightRequests, Map<SocketChannel, HttpResponseImpl> inflightResponses, RequestHandler requestHandler) {
        this.inflightRequests = inflightRequests;
        this.inflightResponses = inflightResponses;
        this.requestHandler = requestHandler;
    }

    @Override
    public void handle(SelectionKey selectionKey) throws IOException {
        SocketChannel channel = (SocketChannel) selectionKey.channel();

        HttpRequestBuilder requestBuilder = inflightRequests.computeIfAbsent(channel, (k) -> HttpRequestBuilder.newBuilder());

        int read = requestBuilder.read(channel);
        if (read == -1) {
            // Closed keepalive connection?
            inflightRequests.remove(channel);
            channel.close();
            return;
        }

        if (requestBuilder.headersFinished()) {
            HttpRequestBuilder.HttpRequestImpl request = requestBuilder.build();

            HttpResponseImpl response = new HttpResponseImpl();
            requestHandler.handle(request, response);
            
            inflightRequests.remove(channel);
            inflightResponses.put(channel, response);
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }

    @Override
    public boolean handles(int readyOps) {
        return (readyOps & SelectionKey.OP_READ) == SelectionKey.OP_READ;
    }
}
