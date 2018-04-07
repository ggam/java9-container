package eu.ggam.container.impl.http;

import java.io.IOException;

/**
 *
 * @author Guillermo González de Agüero
 */
public class RequestParsingException extends IOException {

    public RequestParsingException(Throwable t) {
        super(t);
    }

}
