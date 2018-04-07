package eu.ggam.servlet.impl.descriptor;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Guillermo González de Agüero
 */
public final class MatchingPattern {

    public enum MatchType {

        SERVLET_NAME, // Only for filters
        EXACT,
        PREFIX,
        EXTENSION
    }

    private final String servletName;
    private final Pattern pattern;
    private final MatchType matchType;

    private MatchingPattern(String servletName, Pattern pattern, MatchType matchType) {
        if (matchType == MatchType.SERVLET_NAME && (servletName == null || pattern != null)) {
            throw new IllegalArgumentException("Cannot create " + matchType + " matching pattern");
        }

        if (matchType != MatchType.SERVLET_NAME && (servletName != null || pattern == null)) {
            throw new IllegalArgumentException("Cannot create " + matchType + " matching pattern");
        }

        this.servletName = servletName;
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
        if (!pattern.contains("*")) {
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

        return new MatchingPattern(null, Pattern.compile(pattern), matchType);
    }

    public static MatchingPattern createServletNamePattern(String servletName) {
        return new MatchingPattern(servletName, null, MatchType.SERVLET_NAME);
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public boolean matchesUri(String uri) {
        return uriMatch(uri).isPresent();
    }

    public Optional<UriMatch> uriMatch(String uri) {
        if (matchType == MatchType.SERVLET_NAME) {
            throw new IllegalStateException("This pattern is of type " + matchType);
        }

        Matcher m = pattern.matcher(uri);
        if (m.find()) {
            if (matchType == MatchType.EXTENSION || matchType == MatchType.EXACT) {
                return Optional.of(new UriMatch(m.group(0), null));
            }

            String servletPath = m.group(1);
            String pathInfo = m.group(2);
            return Optional.of(new UriMatch(servletPath.substring(0, servletPath.length() - 1), "/" + pathInfo));
        }

        return Optional.empty();
    }

    public boolean matchesServletName(String servletName) {
        Objects.requireNonNull(servletName);
        if (matchType != MatchType.SERVLET_NAME) {
            throw new IllegalStateException("This pattern is of type " + matchType);
        }

        return this.servletName.equals(servletName);
    }

    public static class UriMatch {

        private final String servletInfo;
        private final String pathInfo;

        public UriMatch(String servletInfo, String pathInfo) {
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.servletName);
        hash = 97 * hash + Objects.hashCode(this.pattern);
        hash = 97 * hash + Objects.hashCode(this.matchType);
        return hash;
    }
}
