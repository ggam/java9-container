package eu.ggam.container.impl.http;

import java.io.IOException;

/**
 *
 * @author guillermo
 */
public class RequestParsingException extends IOException {

    public RequestParsingException(Throwable t) {
        super(t);
    }

}
