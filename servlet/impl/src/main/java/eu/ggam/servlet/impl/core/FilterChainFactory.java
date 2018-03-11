package eu.ggam.servlet.impl.core;

import eu.ggam.servlet.impl.core.matcher.FilterMatcher;
import eu.ggam.servlet.impl.core.matcher.ServletMatch;
import eu.ggam.servlet.impl.core.matcher.ServletMatcher;
import eu.ggam.servlet.impl.descriptor.FilterDescriptor;
import eu.ggam.servlet.impl.descriptor.ServletDescriptor;
import eu.ggam.servlet.impl.jsr154.FilterChainImpl;
import java.util.Queue;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.ServletException;

/**
 *
 * @author guillermo
 */
public class FilterChainFactory implements AutoCloseable {

    private final ServletMatcher servletMatcher;
    private final FilterMatcher filterMatcher;

    public FilterChainFactory(Set<ServletDescriptor> servlets, Set<FilterDescriptor> filters) {
        servletMatcher = new ServletMatcher(servlets);
        filterMatcher = new FilterMatcher(filters);
    }

    public FilterChainImpl create(String pathInfo) {
        try {
            ServletMatch servletMatch = servletMatcher.match(pathInfo);
            Queue<Filter> filterMatches = filterMatcher.match(pathInfo, (ServletDescriptor) servletMatch.getServlet().getServletConfig());

            return new FilterChainImpl(filterMatches, servletMatch);
        } catch (ServletException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public void close() {
        servletMatcher.destroyAll();
        filterMatcher.destroyAll();
    }
}
