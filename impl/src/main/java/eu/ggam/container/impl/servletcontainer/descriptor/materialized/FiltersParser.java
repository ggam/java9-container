package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import eu.ggam.container.impl.servletcontainer.descriptor.FilterDescriptor;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.FilterMappingMetamodel;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.FilterMetamodel;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.WebXml;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Guillermo González de Agüero
 */
public final class FiltersParser {

    private FiltersParser() {

    }

    public static Set<FilterDescriptor> findFilters(WebXml webXml, ClassLoader classLoader) throws ClassNotFoundException {
        Map<String, List<FilterMappingMetamodel>> collect = webXml.getFilterMappings().
                stream().
                collect(Collectors.groupingBy(s -> s.getFilterName()));

        Set<FilterDescriptor> filters = new HashSet<>();

        // TODO: Check whether position counts from definition or mapping of filters
        int position = 0;
        for (FilterMetamodel filter : webXml.getFilters()) {
            filters.add(FilterDescriptor.createFromWebXml(filter, collect.getOrDefault(filter.getFilterName(), Collections.emptyList()), ++position, classLoader));
        }

        return filters;
    }
}
