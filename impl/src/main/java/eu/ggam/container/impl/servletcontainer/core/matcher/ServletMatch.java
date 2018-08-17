package eu.ggam.container.impl.servletcontainer.core.matcher;

import eu.ggam.container.impl.servletcontainer.descriptor.MatchingPattern;
import javax.servlet.Servlet;

/**
 *
 * @author Guillermo González de Agüero
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
