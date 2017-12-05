package es.guillermogonzalezdeaguero.container.impl.servlet;

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
 * @author guillermo
 */
public class FilterChainImpl implements FilterChain {

    private final Queue<Filter> filters;
    private final Servlet servlet;

    public FilterChainImpl(Queue<Filter> filters, Servlet servlet) {
        this.filters = filters;
        this.servlet = servlet;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        Filter filter = filters.poll();

        if (filter != null) {
            filter.doFilter(request, response, this);
            return;
        }

        servlet.service(request, response);
    }

}
