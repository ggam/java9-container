package es.guillermogonzalezdeaguero.servlet.impl;

import es.guillermogonzalezdeaguero.servlet.impl.deployment.FilterMatcher;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.ServletMatcher;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.FilterDescriptor;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.ServletDescriptor;
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
