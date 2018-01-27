package es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author guillermo
 */
public class FilterDescriptor implements Comparable<FilterDescriptor> {

    private final String filterName;
    private final String className;
    private final int position;

    private final Set<String> exactPatterns;
    private final Set<String> prefixPatterns;
    private final Set<String> extensionPatterns;
    private final Set<String> namedServlets;

    public FilterDescriptor(String filterName, String className, int position, Set<String> urlPatterns, Set<String> namedServlets) {
        this.filterName = filterName;
        this.className = className;
        this.position = position;

        this.namedServlets = Collections.unmodifiableSet(namedServlets);
        exactPatterns = new HashSet<>();
        prefixPatterns = new HashSet<>();
        extensionPatterns = new HashSet<>();

        for (String pattern : urlPatterns) {
            /* Validation */
            if (pattern.startsWith("*") && pattern.endsWith("*")) {
                throw new IllegalArgumentException("Invalid URL starting and ending with *");
            }

            if (pattern.startsWith("/") && pattern.contains("*") && !pattern.endsWith("*")) {
                throw new IllegalArgumentException("Invalid URL starting with / and ending with *");
            }

            /* Categorization */
            // Exact match
            if (!pattern.contains("*")) {
                exactPatterns.add(pattern);
                break;
            }

            // Prefix
            if (pattern.startsWith("/")) {
                prefixPatterns.add(pattern);
                break;
            }

            // Extension
            if (pattern.contains("*")) {
                extensionPatterns.add(pattern);
            }
        }
    }

    public int getPosition() {
        return position;
    }

    public String getFilterName() {
        return filterName;
    }

    public String getClassName() {
        return className;
    }

    public Set<String> getExactPatterns() {
        return new HashSet<>(exactPatterns);
    }

    public Set<String> getPrefixPatterns() {
        return new HashSet<>(prefixPatterns);
    }

    public Set<String> getExtensionPatterns() {
        return new HashSet<>(extensionPatterns);
    }

    public Set<String> getNamedServlets() {
        return namedServlets;
    }

    @Override
    public int compareTo(FilterDescriptor other) {
        return Integer.compare(this.position, other.position);
    }

}
