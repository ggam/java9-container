package eu.ggam.servlet.impl.descriptor.materialized;

import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.FilterMappingType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.FilterType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.WebXml;
import eu.ggam.servlet.impl.descriptor.FilterDescriptor;
import eu.ggam.servlet.impl.jsr154.ServletContextImpl;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author guillermo
 */
public final class FiltersParser {

    private FiltersParser() {

    }

    public static Set<FilterDescriptor> findFilters(ServletContextImpl servletContext, WebXml webApp) throws ClassNotFoundException {
        Objects.requireNonNull(servletContext);
        
        Map<String, List<FilterMappingType>> collect = webApp.getFilterMappings().
                stream().
                collect(Collectors.groupingBy(s -> s.getFilterName().getValue()));

        Set<FilterDescriptor> filters = new HashSet<>();

        // TODO: Check whether position counts from definition or mapping of filters
        int position = 0;
        for (FilterType filter : webApp.getFilters()) {
            filters.add(FilterDescriptor.createFromWebXml(servletContext, filter, collect.getOrDefault(filter.getFilterName().getValue(), Collections.emptyList()), ++position));
        }

        return filters;
    }
}
