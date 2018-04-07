package eu.ggam.servlet.impl.jsr154;

import eu.ggam.servlet.impl.core.matcher.ServletMatch;
import eu.ggam.servlet.impl.descriptor.MatchingPattern;
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

    private final MatchingPattern.UriMatch uriMatch;

    public FilterChainImpl(Queue<Filter> filters, ServletMatch servletMatch) {
        this.filters = filters;
        this.servlet = servletMatch.getServlet();

        this.uriMatch = servletMatch.getUriMatch();
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

    public MatchingPattern.UriMatch getUriMatch() {
        return uriMatch;
    }

}
