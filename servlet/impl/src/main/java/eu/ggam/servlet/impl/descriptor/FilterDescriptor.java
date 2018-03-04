package eu.ggam.servlet.impl.descriptor;

import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.FilterMappingType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.FilterType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ParamValueType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.UrlPatternType;
import eu.ggam.servlet.impl.jsr154.ServletContextImpl;
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
    private final ServletContextImpl servletContext;
    private final int position;

    private final Set<MatchingPattern> matchingMatterns = new HashSet<>();

    private final Map<String, String> initParams = new HashMap<>();

    private FilterDescriptor(ServletContextImpl servletContext, FilterType filterType, List<FilterMappingType> filterMappingTypes, int position) throws ClassNotFoundException {
        this.filterName = filterType.getFilterName().getValue();
        this.filterClass = (Class<Filter>) Class.forName(filterType.getFilterClass().getValue(), true, servletContext.getWarClassLoader());
        this.servletContext = servletContext;
        this.position = position;

        for (ParamValueType initParamTypes : filterType.getInitParams()) {
            initParams.put(initParamTypes.getParamName().getValue(), initParamTypes.getParamValue().getValue());
        }

        for (FilterMappingType mapping : filterMappingTypes) {
            for (UrlPatternType servletNamePattern : mapping.getServletNames()) {
                matchingMatterns.add(MatchingPattern.createServletNamePattern(servletNamePattern.getValue()));
            }

            for (UrlPatternType urlPattern : mapping.getUrlPatterns()) {
                matchingMatterns.add(MatchingPattern.createUrlPattern(urlPattern.getValue()));
            }
        }
    }

    public static FilterDescriptor createFromWebXml(ServletContextImpl servletContext, FilterType filterType, List<FilterMappingType> filterMappingTypes, int position) throws ClassNotFoundException {
        return new FilterDescriptor(servletContext, filterType, filterMappingTypes, position);
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

    public Set<MatchingPattern> getMatchingMatterns() {
        return new HashSet<>(matchingMatterns);
    }

    @Override
    public int compareTo(FilterDescriptor other) {
        return Integer.compare(this.position, other.position);
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
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
