package es.guillermogonzalezdeaguero.container.impl.servlet;

import es.guillermogonzalezdeaguero.container.impl.servlet.HttpServletResponseImpl;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

/**
 *
 * @author guillermo
 */
public class ServletResponseToSocket {

    public static void convert(HttpServletResponseImpl response, Writer writer) {
        try {
            writer.append("HTTP/1.1 " + response.getStatus() + " Unknown\r\n\r\n");
            if (response.isErrorSent() && response.getStatusMessage() != null) {
                writer.append(response.getStatusMessage());
            } else {
                writer.append(response.getStringWriter().toString());
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }

    }
}
