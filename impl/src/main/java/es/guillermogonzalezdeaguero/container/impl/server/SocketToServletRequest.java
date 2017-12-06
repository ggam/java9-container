package es.guillermogonzalezdeaguero.container.impl.server;

import es.guillermogonzalezdeaguero.container.impl.servlet.HttpServletRequestImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author guillermo
 */
public class SocketToServletRequest {

    public static HttpServletRequest convert(BufferedReader reader) {
        String uri;
        try {
            uri = reader.readLine().split("GET ")[1].split(" HTTP")[0];
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }

        return new HttpServletRequestImpl(uri);
    }
}
