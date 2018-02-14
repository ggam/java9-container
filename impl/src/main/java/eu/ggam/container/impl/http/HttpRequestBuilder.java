package eu.ggam.container.impl.http;

import eu.ggam.container.impl.internal.ByteBufferInputStream;
import eu.ggam.container.api.http.HttpRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author guillermo
 */
public final class HttpRequestBuilder {

    private Queue<ByteBuffer> buffers = new ArrayDeque<>();
    private ByteBuffer lastBuffer;

    private HttpRequestBuilder() {
        // Private constructor
    }

    public static HttpRequestBuilder newBuilder() {
        return new HttpRequestBuilder();
    }

    public int read(SocketChannel channel) throws IOException {
        lastBuffer = ByteBuffer.allocate(4096);
        buffers.add(lastBuffer);

        return channel.read(lastBuffer);
    }

    public boolean headersFinished() {
        if (lastBuffer == null) {
            return false;
        }

        String lastRead = new String(lastBuffer.array());
        int length = lastRead.length();

        return "".equals(lastRead.substring(length - 3, length - 1).trim());
    }

    public HttpRequestImpl build() throws RequestParsingException {

        ByteBufferInputStream input = new ByteBufferInputStream(new ArrayList<>(buffers));
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        String[] requestLine;
        try {
            requestLine = reader.readLine().split(" ");

            String method = requestLine[0];

            URI uri = new URI(URLDecoder.decode(requestLine[1], "UTF-8"));

            String httpVersion = requestLine[2];

            Map<String, List<String>> headers = new HashMap<>();

            String line;
            while ((line = reader.readLine()) != null) {
                if ("".equals(line)) {
                    // Headers are done.
                    break;
                }

                String[] header = line.split(":");
                List<String> headerValues = headers.computeIfAbsent(header[0], k -> new ArrayList<>());
                //System.out.println(line);
                headerValues.add(header[1]);
            }

            return new HttpRequestImpl(method, uri, headers, input);
        } catch (Exception e) {
            throw new RequestParsingException(e);
        }
    }

    public final class HttpRequestImpl implements HttpRequest {

        private final String method;
        private final URI uri;
        private final Map<String, List<String>> headers;
        private final ByteBufferInputStream inputStream;

        private HttpRequestImpl(String requestMethod, URI requestUri, Map<String, List<String>> requestHeaders, ByteBufferInputStream inputStream) {
            this.method = requestMethod;
            this.uri = requestUri;
            this.headers = requestHeaders;
            this.inputStream = inputStream;
        }

        @Override
        public String getMethod() {
            return method;
        }

        @Override
        public URI getUri() {
            return uri;
        }

        @Override
        public Map<String, List<String>> getHeaders() {
            return headers;
        }

        @Override
        public ByteBufferInputStream getInputStream() {
            return inputStream;
        }

    }
}
