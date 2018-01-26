package es.guillermogonzalezdeaguero.servlet.impl;

import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.FilterDescriptor;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.ServletDescriptor;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author guillermo
 */
public class FilterMatcher {

    private final Set<FilterDescriptor> filterDescriptors;

    public FilterMatcher(Set<FilterDescriptor> filterDescriptors) {
        this.filterDescriptors = new HashSet<>(filterDescriptors);
    }

    public Queue<FilterDescriptor> match(String pathInfo, ServletDescriptor matchedServlet) {
        Queue<FilterDescriptor> matchedFilters = new ArrayDeque<>();
        for (FilterDescriptor filterDescriptor : filterDescriptors) {
            for (String exactPattern : filterDescriptor.getExactPatterns()) {
                if (exactPattern.equals(pathInfo) || (pathInfo == null && "/".equals(exactPattern))) {
                    matchedFilters.add(filterDescriptor);
                }
            }

            for (String prefixPattern : filterDescriptor.getPrefixPatterns()) {
                String prefix = prefixPattern.substring(0, prefixPattern.length() - 1);
                if (pathInfo != null && pathInfo.startsWith(prefix)) {
                    matchedFilters.add(filterDescriptor);
                }
            }

            if (pathInfo == null) {
                // TODO: pending review
                // matchedFilters.add(filterDescriptor);
            }

            for (String extensionPattern : filterDescriptor.getExtensionPatterns()) {
                String extension = extensionPattern.split(".")[1];
                if (pathInfo != null && pathInfo.endsWith(extension)) {
                    matchedFilters.add(filterDescriptor);
                }
            }

            for (String servletName : filterDescriptor.getNamedServlets()) {
                if (servletName.equals(matchedServlet.getServletName())) {
                    matchedFilters.add(filterDescriptor);
                }
            }

        }
        return matchedFilters;
    }

}
