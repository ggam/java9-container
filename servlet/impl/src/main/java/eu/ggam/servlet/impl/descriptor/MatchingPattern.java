package eu.ggam.servlet.impl.descriptor;

import java.util.Objects;

/**
 *
 * @author guillermo
 */
public class MatchingPattern {

    public enum MatchType {

        SERVLET_NAME, // Only for filters
        EXACT,
        PREFIX,
        EXTENSION
    }

    private final String servletName;
    private final String pattern;
    private final MatchType matchType;

    private MatchingPattern(String servletName, String pattern, MatchType matchType) {
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
            pattern = pattern.replace("*", ".*");
            if (pattern.startsWith("/")) {
                // Prefix
                matchType = MatchingPattern.MatchType.PREFIX;
            } else {
                // Extension
                matchType = MatchingPattern.MatchType.EXTENSION;
            }
        }

        return new MatchingPattern(null, pattern, matchType);
    }

    public static MatchingPattern createServletNamePattern(String servletName) {
        return new MatchingPattern(servletName, null, MatchType.SERVLET_NAME);
    }

    public String getPattern() {
        return pattern;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public boolean matchesPathInfo(String pathInfo) {
        if (matchType == MatchType.SERVLET_NAME) {
            throw new IllegalStateException("This pattern is of type " + matchType);
        }

        // TODO: how to handle null pathInfo?
        return pathInfo != null && pathInfo.matches(pattern);
    }

    public boolean matchesServletName(String servletName) {
        Objects.requireNonNull(servletName);
        if (matchType != MatchType.SERVLET_NAME) {
            throw new IllegalStateException("This pattern is of type " + matchType);
        }

        return this.servletName.equals(servletName);
    }

}
