package eu.ggam.container.impl.server;

import eu.ggam.container.impl.http.HttpMessageExchangeImpl;
import eu.ggam.container.impl.http.HttpRequestInputStream;
import eu.ggam.container.impl.http.RequestParsingException;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

/**
 *
 * @author guillermo
 */
public class HttpRequestHolder {

    private final ArrayDeque<ByteBuffer> input = new ArrayDeque<>();
    private final ArrayDeque<ByteBuffer> output = new ArrayDeque<>();

    private HttpMessageExchangeImpl exchange;

    public synchronized ByteBuffer readBuffer() {
        if (exchange != null) {
            throw new IllegalStateException("Exchange already created");
        }

        ByteBuffer buf = ByteBuffer.allocate(4096);
        input.add(buf);
        return buf;
    }

    public synchronized ByteBuffer writeBuffer() {
        if (exchange == null) {
            throw new IllegalStateException("Exchange not created");
        }

        ByteBuffer buf = ByteBuffer.allocate(4096);
        output.add(buf);
        return buf;
    }

    public synchronized HttpMessageExchangeImpl createExchange() throws RequestParsingException {
        if (this.exchange == null) {
            this.exchange = HttpMessageExchangeImpl.of(new HttpRequestInputStream(new ArrayList<>(input)), output);
        }

        return exchange;
    }

    public Queue<ByteBuffer> getOutput() {
        return output;
    }

}
