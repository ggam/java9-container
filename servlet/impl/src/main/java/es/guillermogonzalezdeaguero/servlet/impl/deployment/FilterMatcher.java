package es.guillermogonzalezdeaguero.servlet.impl.deployment;

import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.FilterDescriptor;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.ServletDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
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

    public FilterMatcher(Set<FilterDescriptor> filterDescriptors) {
        this.filterDescriptors = new TreeSet<>(filterDescriptors);
        this.filterInstances = new ConcurrentHashMap<>();
    }

    public Queue<Filter> match(String pathInfo, ServletDescriptor matchedServlet) throws ServletException {
        Queue<Filter> matchedFilters = new ArrayDeque<>();
        // TODO: check detection order
        for (FilterDescriptor filterDescriptor : filterDescriptors) {
            for (String exactPattern : filterDescriptor.getExactPatterns()) {
                if (exactPattern.equals(pathInfo) || (pathInfo == null && "/".equals(exactPattern))) {
                    matchedFilters.add(getFilterInstance(filterDescriptor));
                }
            }

            for (String prefixPattern : filterDescriptor.getPrefixPatterns()) {
                String prefix = prefixPattern.substring(0, prefixPattern.length() - 1);
                if (pathInfo != null && pathInfo.startsWith(prefix)) {
                    matchedFilters.add(getFilterInstance(filterDescriptor));
                }
            }

            if (pathInfo == null) {
                // TODO: pending review
                // matchedFilters.add(filterDescriptor);
            }

            for (String extensionPattern : filterDescriptor.getExtensionPatterns()) {
                String extension = extensionPattern.split(".")[1];
                if (pathInfo != null && pathInfo.endsWith(extension)) {
                    matchedFilters.add(getFilterInstance(filterDescriptor));
                }
            }

            for (String servletName : filterDescriptor.getNamedServlets()) {
                if (servletName.equals(matchedServlet.getServletName())) {
                    matchedFilters.add(getFilterInstance(filterDescriptor));
                }
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
