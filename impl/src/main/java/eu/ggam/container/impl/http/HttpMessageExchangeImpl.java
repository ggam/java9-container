package eu.ggam.container.impl.http;

import eu.ggam.container.api.http.HttpMessageExchange;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author guillermo
 */
public class HttpMessageExchangeImpl implements HttpMessageExchange, AutoCloseable {

    private final String requestMethod;
    private final URI requestUri;
    private final Map<String, List<String>> requestHeaders;
    private final HttpRequestInputStream inputStream;

    private Map<String, List<String>> responseHeaders;
    private final HttpResponseOutputStream responseHeadersStream;
    private final HttpResponseOutputStream responseBodyStream;
    private int responseStatus;

    private boolean responseInitiated;
    private final Object lock;

    private HttpMessageExchangeImpl(String requestMethod, URI requestUri, Map<String, List<String>> requestHeaders, HttpRequestInputStream inputStream, HttpResponseOutputStream outputStream) {
        this.requestMethod = requestMethod;
        this.requestUri = requestUri;
        this.requestHeaders = Collections.unmodifiableMap(requestHeaders);
        this.inputStream = inputStream;
        this.responseHeaders = new HashMap<>();
        this.responseHeadersStream = new HttpResponseOutputStream(4096);
        this.responseBodyStream = outputStream;
        this.lock = new Object();
        this.responseStatus = 200;
    }

    public static HttpMessageExchangeImpl of(HttpRequestInputStream request, HttpResponseOutputStream response) throws RequestParsingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request));

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

            return new HttpMessageExchangeImpl(method, uri, headers, request, response);
        } catch (Exception e) {
            throw new RequestParsingException(e);
        }
    }

    @Override
    public String getRequestMethod() {
        return requestMethod;
    }

    @Override
    public URI getRequestUri() {
        return requestUri;
    }

    @Override
    public Map<String, List<String>> getRequestHeaders() {
        return requestHeaders;
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public void setResponseHeaders(Map<String, List<String>> responseHeaders) {
        Objects.requireNonNull(responseHeaders);
        this.responseHeaders = responseHeaders;
    }

    @Override
    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    @Override
    public OutputStream getOutputStream() {
        return responseBodyStream;
    }

    @Override
    public void setResponseStatus(int status) {
        // TODO: validate status
        this.responseStatus = status;
    }

    @Override
    public int getResponseStatus() {
        return responseStatus;
    }

    public Optional<ByteBuffer> getNextResponseBuffer() {
        if (!responseInitiated) {
            synchronized (lock) {
                if (!responseInitiated) {
                    try {
                        responseInitiated = true;
                        responseHeadersStream.write(("HTTP/1.1 " + getResponseStatus() + " Unknown\n").getBytes());

                        Iterator<Entry<String, List<String>>> iterator = getResponseHeaders().entrySet().iterator();

                        while (iterator.hasNext()) {
                            Entry<String, List<String>> header = iterator.next();
                            for (String headerValue : header.getValue()) {
                                String endOfLine = iterator.hasNext() ? "\n" : "\r\n\r\n";
                                responseHeadersStream.write((header.getKey() + ": " + headerValue + endOfLine).getBytes());
                            }
                        }
                    } catch (IOException ex) {
                        throw new UncheckedIOException(ex);
                    }
                }
            }
        }

        Optional<ByteBuffer> nextBuffer = responseHeadersStream.getNextBuffer();

        if (nextBuffer.isPresent()) {
            return nextBuffer;
        }

        return responseBodyStream.getNextBuffer();
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        responseHeadersStream.close();
        responseBodyStream.close();
    }

}
