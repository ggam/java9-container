package es.guillermogonzalezdeaguero.container.testwebapp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author guillermo
 */
public class TestFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(TestFilter.class.getName());
    private String forbiddenParam;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        forbiddenParam = filterConfig.getInitParameter("forbidden_param");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        LOGGER.log(Level.INFO, "Intercepting request to {0}", httpRequest.getRequestURI());
        
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
