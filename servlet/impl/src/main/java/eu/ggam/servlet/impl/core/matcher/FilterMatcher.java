package eu.ggam.servlet.impl.core.matcher;

import eu.ggam.servlet.impl.descriptor.FilterDescriptor;
import eu.ggam.servlet.impl.descriptor.MatchingPattern;
import eu.ggam.servlet.impl.descriptor.MatchingPattern.MatchType;
import eu.ggam.servlet.impl.descriptor.ServletDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.Filter;
import javax.servlet.ServletException;

/**
 *
 * @author guillermo
 */
public class FilterMatcher {

    private final TreeSet<FilterDescriptor> filterDescriptors;
    private final ConcurrentHashMap<String, Filter> filterInstances;

    private static final EnumSet<MatchType> URL_MATCHING_TYPES = EnumSet.of(MatchType.EXACT, MatchType.EXTENSION, MatchType.PREFIX);

    public FilterMatcher(Set<FilterDescriptor> filterDescriptors) {
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

                    newInstance.init(descriptor);

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
}
