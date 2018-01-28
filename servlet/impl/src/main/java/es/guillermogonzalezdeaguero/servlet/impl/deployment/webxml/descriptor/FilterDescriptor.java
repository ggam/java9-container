package eu.ggam.servlet.impl.deployment.webxml.descriptor;

import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.FilterMappingType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.FilterType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ParamValueType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.UrlPatternType;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

/**
 *
 * @author guillermo
 */
public class FilterDescriptor implements Comparable<FilterDescriptor>, FilterConfig {

    private final String filterName;
    private final Class<? extends Filter> filterClass;
    private final int position;

    private final Set<String> exactPatterns = new HashSet<>();
    private final Set<String> prefixPatterns = new HashSet<>();
    private final Set<String> extensionPatterns = new HashSet<>();
    private final Set<String> namedServlets = new HashSet<>();
    
    private final Map<String, String> initParams = new HashMap<>();

    public FilterDescriptor(FilterType filterType, List<FilterMappingType> filterMappingTypes, ClassLoader classLoader, int position) throws ClassNotFoundException {
        this.filterName = filterType.getFilterName().getValue();
        this.filterClass = (Class<Filter>) Class.forName(filterType.getFilterClass().getValue(), true, classLoader);
        this.position = position;

        for (ParamValueType initParamTypes : filterType.getInitParams()) {
            initParams.put(initParamTypes.getParamName().getValue(), initParamTypes.getParamValue().getValue());
        }
        
        for (FilterMappingType mapping : filterMappingTypes) {
            for (UrlPatternType servletNamePattern : mapping.getServletNames()) {
                namedServlets.add(servletNamePattern.getValue());
            }

            for (UrlPatternType urlPattern : mapping.getUrlPatterns()) {
                String pattern = urlPattern.getValue();

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
    }

    @Override
    public String getFilterName() {
        return filterName;
    }

    public Class<? extends Filter> getFilterClass() {
        return filterClass;
    }

    public int getPosition() {
        return position;
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

    @Override
    public ServletContext getServletContext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getInitParameter(String name) {
        return initParams.get(name);
    }

    @Override
    public Enumeration getInitParameterNames() {
        return Collections.enumeration(initParams.keySet());
    }

}
