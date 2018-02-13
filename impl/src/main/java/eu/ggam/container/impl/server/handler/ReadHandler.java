package eu.ggam.container.impl.server.handler;

import eu.ggam.container.api.http.HttpMessageExchange;
import eu.ggam.container.impl.RequestHandler;
import eu.ggam.container.impl.http.HttpResponseOutputStream;
import eu.ggam.container.impl.server.HttpRequestHolder;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;

/**
 *
 * @author guillermo
 */
public class ReadHandler implements Handler {

    private final Map<SocketChannel, HttpRequestHolder> pendingData;
    private final RequestHandler requestHandler;

    public ReadHandler(Map<SocketChannel, HttpRequestHolder> pendingData, RequestHandler requestHandler) {
        this.pendingData = pendingData;
        this.requestHandler = requestHandler;
    }

    @Override
    public void handle(SelectionKey selectionKey) throws IOException {
        SocketChannel clientSocket = (SocketChannel) selectionKey.channel();

        ByteBuffer buf = pendingData.get(clientSocket).readBuffer();
        int read = clientSocket.read(pendingData.get(clientSocket).readBuffer());
        if (read == -1) {
            // Closed keepalive connection?
            pendingData.remove(clientSocket);
            clientSocket.close();
            return;
        }

        if (read > 0) {
            String lastRead = new String(buf.array());
            int length = lastRead.length();

            if ("".equals(lastRead.substring(length - 3, length - 1).trim())) {
                HttpResponseOutputStream output = new HttpResponseOutputStream(clientSocket.socket().getSendBufferSize());

                HttpMessageExchange httpMessage = pendingData.get(clientSocket).createExchange();

                requestHandler.handleRequest(httpMessage);
                selectionKey.interestOps(SelectionKey.OP_WRITE);
            }
        }
    }

    @Override
    public boolean handles(int readyOps) {
        return (readyOps & SelectionKey.OP_READ) == SelectionKey.OP_READ;
    }
}
