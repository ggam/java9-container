package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guillermo González de Agüero
 */
public class KnownUrlPattern {

    private final MatchingPattern matchingPattern;
    private final String servletName;
    private final List<String> filterNames;

    public KnownUrlPattern(String urlPattern, String servletName, List<String> filterNames) {
        this.matchingPattern = MatchingPattern.createUrlPattern(urlPattern);
        this.servletName = servletName;
        this.filterNames = new ArrayList<>(filterNames);
    }

    public MatchingPattern getMatchingPattern() {
        return matchingPattern;
    }

    public String getServletName() {
        return servletName;
    }

    public List<String> getFilterNames() {
        return filterNames;
    }

}
