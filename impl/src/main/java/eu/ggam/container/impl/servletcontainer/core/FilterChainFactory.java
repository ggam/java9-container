package eu.ggam.container.impl.servletcontainer.core;

import eu.ggam.container.impl.servletcontainer.descriptor.materialized.FilterFactory;
import eu.ggam.container.impl.servletcontainer.descriptor.materialized.RequestUriMatch;
import eu.ggam.container.impl.servletcontainer.descriptor.materialized.ServletFactory;
import eu.ggam.container.impl.servletcontainer.descriptor.materialized.UrlPatternsParser;
import eu.ggam.container.impl.servletcontainer.jsr154.FilterChainImpl;
import java.util.ArrayDeque;
import java.util.Queue;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

/**
 *
 * @author Guillermo González de Agüero
 */
public class FilterChainFactory implements AutoCloseable {

    private final ServletFactory servletFactory;
    private final FilterFactory filterFactory;
    private final UrlPatternsParser urlPatternsParser;

    public FilterChainFactory(ServletFactory servletFactory, FilterFactory filterFactory, UrlPatternsParser urlPatternsParser) {
        this.servletFactory = servletFactory;
        this.filterFactory = filterFactory;
        this.urlPatternsParser = urlPatternsParser;
    }

    public FilterChainImpl create(String uri) throws ServletException {
        RequestUriMatch uriMatch = urlPatternsParser.match(uri);

        Servlet servlet = servletFactory.getServlet(uriMatch.getServletName());
        Queue<Filter> filterMatches = new ArrayDeque<>();
        for(String filterName :  uriMatch.getFilterNames()) {
            filterMatches.add(filterFactory.getFilter(filterName));
        }

        return new FilterChainImpl(filterMatches, servlet, uriMatch);
    }

    @Override
    public void close() {
        servletFactory.destroyAll();
        filterFactory.destroyAll();
    }
}
