package es.guillermogonzalezdeaguero.container.impl.servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author guillermo
 */
public class RequestCompletionFilter implements Filter {

    private final String contextPath;

    public RequestCompletionFilter(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            // Completes the HttpRequest with the missing information
            request = new MatchedHttpServletRequestImpl(contextPath, (HttpServletRequest) request);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
