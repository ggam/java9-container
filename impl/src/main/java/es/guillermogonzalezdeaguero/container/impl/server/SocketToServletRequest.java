package es.guillermogonzalezdeaguero.container.impl.server;

import es.guillermogonzalezdeaguero.container.impl.servlet.HttpServletRequestImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author guillermo
 */
public class SocketToServletRequest {

    public static HttpServletRequest convert(BufferedReader reader) {
        try {
            String[] requestLine = reader.readLine().split(" ");

            String method = requestLine[0];

            String[] uriQueryParams = requestLine[1].split("\\?");
            String uri = uriQueryParams[0];
            if (uriQueryParams.length == 2) {
                String queryParams = uriQueryParams[1]; // TODO: support query params
            }

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
                System.out.println(line);
                headerValues.add(header[1]);
            }
            
            // TODO: process entity body

            return new HttpServletRequestImpl(method, uri, headers);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }

    }
}
