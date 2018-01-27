package es.guillermogonzalezdeaguero.servlet.impl;

import es.guillermogonzalezdeaguero.servlet.impl.deployment.FilterMatcher;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.ServletMatcher;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.EffectiveWebXml;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.FilterDescriptor;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.ServletDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.Queue;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.Servlet;

/**
 *
 * @author guillermo
 */
public class FilterChainFactory {

    private final EffectiveWebXml webXml;
    private final Module module;

    private final ServletMatcher servletMatcher;
    private final FilterMatcher filterMatcher;

    public FilterChainFactory(EffectiveWebXml webXml, Module module) {
        this.webXml = webXml;
        this.module = module;

        servletMatcher = new ServletMatcher(webXml.getServletDescriptors());
        filterMatcher = new FilterMatcher(webXml.getFilterDescriptors());
    }

    public FilterChain create(String pathInfo) {
        ServletDescriptor servletMatch = servletMatcher.match(pathInfo);
        Queue<FilterDescriptor> matchedFilters = filterMatcher.match(pathInfo, servletMatch);

        Servlet servletInstance;
        try {
            servletInstance = servletMatch.getServletClass().getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }

        ArrayDeque<Filter> matchedFilterInstances = new ArrayDeque<>();
        try {
            for (FilterDescriptor matchedFilter : matchedFilters) {
                matchedFilterInstances.add(matchedFilter.getFilterClass().getDeclaredConstructor().newInstance());
            }
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }

        return new FilterChainImpl(matchedFilterInstances, servletInstance);
    }
}
