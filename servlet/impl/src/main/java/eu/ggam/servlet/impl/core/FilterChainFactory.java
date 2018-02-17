package eu.ggam.servlet.impl.core;

import eu.ggam.servlet.impl.jsr154.FilterChainImpl;
import eu.ggam.servlet.impl.core.matcher.FilterMatcher;
import eu.ggam.servlet.impl.core.matcher.ServletMatcher;
import eu.ggam.servlet.impl.descriptor.FilterDescriptor;
import eu.ggam.servlet.impl.descriptor.ServletDescriptor;
import java.util.Queue;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

/**
 *
 * @author guillermo
 */
public class FilterChainFactory {

    private final ServletMatcher servletMatcher;
    private final FilterMatcher filterMatcher;

    public FilterChainFactory(Set<ServletDescriptor> servlets, Set<FilterDescriptor> filters) {
        servletMatcher = new ServletMatcher(servlets);
        filterMatcher = new FilterMatcher(filters);
    }

    public FilterChain create(String pathInfo) {
        try {
            Servlet servletMatch = servletMatcher.match(pathInfo);
            Queue<Filter> filterMatches = filterMatcher.match(pathInfo, (ServletDescriptor) servletMatch.getServletConfig());

            return new FilterChainImpl(filterMatches, servletMatch);
        } catch (ServletException ex) {
            throw new RuntimeException(ex);
        }
    }
}
