package eu.ggam.container.integrationtests.servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Guillermo González de Agüero
 */
public class TestFilter implements Filter {

    private String forbiddenParam;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        forbiddenParam = filterConfig.getInitParameter("forbidden_param");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        if (httpRequest.getParameterMap().containsKey(forbiddenParam)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Fobidden!");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

}
