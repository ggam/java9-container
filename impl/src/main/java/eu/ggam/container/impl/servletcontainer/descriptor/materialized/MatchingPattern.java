package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Guillermo González de Agüero
 */
public final class MatchingPattern {

    public enum MatchType {
        EXACT,
        PREFIX,
        EXTENSION,
        DEFAULT
    }

    private final Pattern pattern;
    private final MatchType matchType;

    private MatchingPattern(Pattern pattern, MatchType matchType) {
        this.pattern = pattern;
        this.matchType = matchType;
    }

    public static MatchingPattern createUrlPattern(String pattern) {
        // TODO: categorize using RegEx to also better validate correctness
        // TODO: check whether there are differences between Servlets and Filters

        /* Validation */
        if (pattern.startsWith("*") && pattern.endsWith("*")) {
            throw new IllegalArgumentException("Invalid URL starting and ending with *");
        }

        if (pattern.startsWith("/") && pattern.contains("*") && !pattern.endsWith("*")) {
            throw new IllegalArgumentException("Invalid URL starting with / and ending with *");
        }

        /* Categorization */
        MatchType matchType;
        if ("/".equals(pattern)) {
            matchType = MatchType.DEFAULT;
        } else if (!pattern.contains("*")) {
            // Exact match
            matchType = MatchingPattern.MatchType.EXACT;
        } else {
            if (pattern.startsWith("/")) {
                // Prefix

                pattern = "(" + pattern;

                int indexOf = pattern.indexOf("*");

                pattern = pattern.substring(0, indexOf) + ")" + pattern.substring(indexOf, pattern.length());

                matchType = MatchingPattern.MatchType.PREFIX;
            } else {
                // Extension
                matchType = MatchingPattern.MatchType.EXTENSION;
            }

            pattern = pattern.replace("*", "(.*)");
        }

        return new MatchingPattern(Pattern.compile(pattern), matchType);
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public boolean matchesUri(String uri) {
        return uriMatch(uri).isPresent();
    }

    public Optional<ServletUriMatch> uriMatch(String uri) {
        if("/".equals(pattern.pattern())) {
            // Default Servlet always matches
            return Optional.of(new ServletUriMatch("", uri));
        }
        
        Matcher m = pattern.matcher(uri);
        if (m.find()) {
            if (matchType == MatchType.EXTENSION || matchType == MatchType.EXACT) {
                return Optional.of(new ServletUriMatch(m.group(0), null));
            }

            String servletPath = m.group(1);
            String pathInfo = m.group(2);
            return Optional.of(new ServletUriMatch(servletPath.substring(0, servletPath.length() - 1), "/" + pathInfo));
        }

        return Optional.empty();
    }

    public static class ServletUriMatch {

        private final String servletInfo;
        private final String pathInfo;

        public ServletUriMatch(String servletInfo, String pathInfo) {
            this.servletInfo = servletInfo;
            this.pathInfo = pathInfo;
        }

        public String getServletPath() {
            return servletInfo;
        }

        public String getPathInfo() {
            return pathInfo;
        }

    }
}
