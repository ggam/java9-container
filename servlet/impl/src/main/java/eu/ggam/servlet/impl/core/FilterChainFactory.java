package eu.ggam.servlet.impl.core;

import eu.ggam.servlet.impl.core.matcher.FilterMatcher;
import eu.ggam.servlet.impl.core.matcher.ServletMatch;
import eu.ggam.servlet.impl.core.matcher.ServletMatcher;
import eu.ggam.servlet.impl.descriptor.materialized.MaterializedWebApp;
import eu.ggam.servlet.impl.jsr154.FilterChainImpl;
import eu.ggam.servlet.impl.jsr154.ServletConfigImpl;
import java.util.Queue;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *
 * @author Guillermo González de Agüero
 */
public class FilterChainFactory implements AutoCloseable {

    private final ServletMatcher servletMatcher;
    private final FilterMatcher filterMatcher;

    public FilterChainFactory(ServletContext servletContext, MaterializedWebApp webApp) {
        servletMatcher = new ServletMatcher(servletContext, webApp.getServletDescriptors());
        filterMatcher = new FilterMatcher(servletContext, webApp.getFilterDescriptors());
    }

    public FilterChainImpl create(String pathInfo) {
        try {
            ServletMatch servletMatch = servletMatcher.match(pathInfo);
            Queue<Filter> filterMatches = filterMatcher.match(pathInfo, ((ServletConfigImpl) servletMatch.getServlet().getServletConfig()).getServletDescriptor());

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
