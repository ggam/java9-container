package eu.ggam.container.impl.http;

import eu.ggam.container.api.http.HttpRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Guillermo González de Agüero
 */
public final class HttpRequestImpl implements HttpRequest {

    private final String method;
    private final URI uri;
    private final Map<String, List<String>> headers;
    private final InputStream inputStream;

    public HttpRequestImpl(InputStream socketInput) throws RequestParsingException {
        InputStream input = socketInput;
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        String[] requestLine;
        try {
            requestLine = reader.readLine().split(" ");

            String method = requestLine[0];

            URI uri = new URI(URLDecoder.decode(requestLine[1], "UTF-8"));

            Map<String, List<String>> headers = new HashMap<>();

            String line;
            while ((line = reader.readLine()) != null) {
                if ("".equals(line)) {
                    // Headers are done.
                    break;
                }

                String[] header = line.split(":");
                List<String> headerValues = headers.computeIfAbsent(header[0], k -> new ArrayList<>());
                headerValues.add(header[1]);
            }

            this.method = method;
            this.uri = uri;
            this.headers = headers;
            this.inputStream = input;
        } catch (Exception e) {
            throw new RequestParsingException(e);
        }
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
    public Optional<InputStream> getInputStream() {
        if ("POST".equals(getMethod())) {
            return Optional.of(inputStream);
        } else {
            return Optional.empty();
        }
    }

}
