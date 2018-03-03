package eu.ggam.servlet.impl.core.matcher;

import eu.ggam.servlet.impl.descriptor.MatchingPattern;
import javax.servlet.Servlet;

/**
 *
 * @author guillermo
 */
public class ServletMatch {

    private final Servlet servlet;
    private final MatchingPattern.UriMatch uriMatch;

    public ServletMatch(Servlet servlet, MatchingPattern.UriMatch uriMatch) {
        this.servlet = servlet;
        this.uriMatch = uriMatch;
    }

    public Servlet getServlet() {
        return servlet;
    }

    public MatchingPattern.UriMatch getUriMatch() {
        return uriMatch;
    }

}
