package eu.ggam.container.impl.servletcontainer.descriptor;

import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.FilterMappingMetamodel;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.FilterMetamodel;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.InitParamMetamodel;
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

    private FilterDescriptor(FilterMetamodel filterType, List<FilterMappingMetamodel> filterMappingTypes, int position, ClassLoader classLoader) throws ClassNotFoundException {
        this.filterName = filterType.getFilterName();
        this.filterClass = (Class<Filter>) Class.forName(filterType.getFilterClass(), true, classLoader);
        this.position = position;

        for (InitParamMetamodel initParamTypes : filterType.getInitParams()) {
            initParams.put(initParamTypes.getParamName(), initParamTypes.getParamValue());
        }

        for (FilterMappingMetamodel mapping : filterMappingTypes) {
            for (String servletNamePattern : mapping.getServletNames()) {
                matchingMatterns.add(MatchingPattern.createServletNamePattern(servletNamePattern));
            }

            for (String urlPattern : mapping.getUrlPatterns()) {
                matchingMatterns.add(MatchingPattern.createUrlPattern(urlPattern));
            }
        }
    }

    public static FilterDescriptor createFromWebXml(FilterMetamodel filterType, List<FilterMappingMetamodel> filterMappingTypes, int position, ClassLoader classLoader) throws ClassNotFoundException {
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
