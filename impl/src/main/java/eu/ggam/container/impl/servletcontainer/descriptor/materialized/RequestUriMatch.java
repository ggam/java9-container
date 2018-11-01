package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import java.util.List;

/**
 *
 * @author Guillermo González de Agüero
 */
public class RequestUriMatch {

    private final String servletName;
    private final List<String> filterNames;

    private final MatchingPattern matchingPattern;
    private final String servletPath;
    private final String pathInfo;

    public RequestUriMatch(String servletName, List<String> filterNames, MatchingPattern matchingPattern, String servletPath, String pathInfo) {
        this.servletName = servletName;
        this.filterNames = filterNames;
        this.matchingPattern = matchingPattern;
        this.servletPath = servletPath;
        this.pathInfo = pathInfo;
    }

    public String getServletName() {
        return servletName;
    }

    public List<String> getFilterNames() {
        return filterNames;
    }

    public MatchingPattern getMatchingPattern() {
        return matchingPattern;
    }

    public String getServletPath() {
        return servletPath;
    }

    public String getPathInfo() {
        return pathInfo;
    }

}
