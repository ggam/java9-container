package eu.ggam.container.impl.servletcontainer.jsr154;

import eu.ggam.container.impl.servletcontainer.descriptor.materialized.RequestUriMatch;
import java.io.IOException;
import java.util.Queue;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author Guillermo González de Agüero
 */
public class FilterChainImpl implements FilterChain {

    private final Queue<Filter> filters;
    private final Servlet servlet;

    private final RequestUriMatch uriMatch;

    public FilterChainImpl(Queue<Filter> filters, Servlet servlet, RequestUriMatch uriMatch) {
        this.filters = filters;
        this.servlet = servlet;

        this.uriMatch = uriMatch;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        Filter filter = filters.poll();

        if (filter != null) {
            filter.doFilter(request, response, this);
        } else {
            // No more filters, go for the Servlet
            servlet.service(request, response);
        }
    }

    public RequestUriMatch getUriMatch() {
        return uriMatch;
    }

}
