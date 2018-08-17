package eu.ggam.container.impl.servletcontainer.core.matcher;

import eu.ggam.container.impl.servletcontainer.descriptor.FilterDescriptor;
import eu.ggam.container.impl.servletcontainer.descriptor.MatchingPattern;
import eu.ggam.container.impl.servletcontainer.descriptor.MatchingPattern.MatchType;
import eu.ggam.container.impl.servletcontainer.descriptor.ServletDescriptor;
import eu.ggam.container.impl.servletcontainer.jsr154.FilterConfigImpl;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *
 * @author Guillermo González de Agüero
 */
public class FilterMatcher {

    private final ServletContext servletContext;

    private final TreeSet<FilterDescriptor> filterDescriptors;
    private final ConcurrentHashMap<String, Filter> filterInstances;

    private static final EnumSet<MatchType> URL_MATCHING_TYPES = EnumSet.of(MatchType.EXACT, MatchType.EXTENSION, MatchType.PREFIX);

    public FilterMatcher(ServletContext servletContext, Set<FilterDescriptor> filterDescriptors) {
        this.servletContext = servletContext;
        this.filterDescriptors = new TreeSet<>(filterDescriptors);
        this.filterInstances = new ConcurrentHashMap<>();
    }

    public Queue<Filter> match(String pathInfo, ServletDescriptor matchedServlet) throws ServletException {
        Queue<Filter> matchedFilters = new ArrayDeque<>();
        // TODO: check detection order

        for (MatchType matchType : URL_MATCHING_TYPES) {
            for (FilterDescriptor filterDescriptor : filterDescriptors) {
                Optional<MatchingPattern> findAny = filterDescriptor.getMatchingMatterns().
                        stream().
                        filter(p -> p.getMatchType() == matchType).
                        filter(p -> p.matchesUri(pathInfo)).
                        findAny();

                if (findAny.isPresent()) {
                    matchedFilters.add(getFilterInstance(filterDescriptor));
                }
            }
        }

        for (FilterDescriptor filterDescriptor : filterDescriptors) {
            Optional<MatchingPattern> findAny = filterDescriptor.getMatchingMatterns().
                    stream().
                    filter(p -> p.getMatchType() == MatchType.SERVLET_NAME).
                    filter(p -> p.matchesServletName(matchedServlet.getServletName())).
                    findAny();

            if (findAny.isPresent()) {
                matchedFilters.add(getFilterInstance(filterDescriptor));
            }
        }
        return matchedFilters;
    }

    private Filter getFilterInstance(FilterDescriptor descriptor) throws ServletException {
        try {
            return filterInstances.computeIfAbsent(descriptor.getFilterName(), (String s) -> {
                try {
                    Filter newInstance = descriptor.getFilterClass().getDeclaredConstructor().newInstance();

                    newInstance.init(new FilterConfigImpl(servletContext, descriptor));

                    return newInstance;
                } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ServletException ex) {
                    throw new RuntimeException(ex);
                }
            });
        } catch (RuntimeException e) {
            if (e.getCause() != null && e.getCause() instanceof ServletException) {
                throw (ServletException) e.getCause();
            }
            throw e;
        }
    }

    public void destroyAll() {
        filterInstances.values().forEach(f -> f.destroy());
    }
}
