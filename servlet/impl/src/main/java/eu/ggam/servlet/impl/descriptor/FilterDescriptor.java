package eu.ggam.servlet.impl.descriptor;

import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.FilterMappingType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.FilterType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ParamValueType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.UrlPatternType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;

/**
 *
 * @author Guillermo González de Agüero
 */
public class FilterDescriptor implements Comparable<FilterDescriptor> {

    private final String filterName;
    private final Class<? extends Filter> filterClass;
    private final int position;

    private final Set<MatchingPattern> matchingMatterns = new HashSet<>();

    private final Map<String, String> initParams = new HashMap<>();

    private FilterDescriptor(FilterType filterType, List<FilterMappingType> filterMappingTypes, int position, ClassLoader classLoader) throws ClassNotFoundException {
        this.filterName = filterType.getFilterName().getValue();
        this.filterClass = (Class<Filter>) Class.forName(filterType.getFilterClass().getValue(), true, classLoader);
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

    public static FilterDescriptor createFromWebXml(FilterType filterType, List<FilterMappingType> filterMappingTypes, int position, ClassLoader classLoader) throws ClassNotFoundException {
        return new FilterDescriptor(filterType, filterMappingTypes, position, classLoader);
    }

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

    public Map<String, String> getInitParams() {
        return new HashMap<>(initParams);
    }

}
