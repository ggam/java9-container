package es.guillermogonzalezdeaguero.servlet.impl;

import es.guillermogonzalezdeaguero.servlet.impl.deployment.ServletMatcher;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.EffectiveWebXml;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.FilterDescriptor;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.ServletDescriptor;
import es.guillermogonzalezdeaguero.servlet.impl.system.FileServlet;
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

        String servletClassName;
        if (servletMatch != null) {
            servletClassName = servletMatch.getClassName();
        } else {
            // Fallback to FileServlet when no match is found
            servletClassName = FileServlet.class.getName();
        }

        Servlet servletInstance = null;
        try {
            Class<?> servletClass = Class.forName(servletClassName, true, module.getClassLoader());

            servletInstance = (Servlet) Class.forName(servletClass.getModule(), servletClass.getName()).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        ArrayDeque<Filter> matchedFilterInstances = new ArrayDeque<>();
        for (FilterDescriptor matchedFilter : matchedFilters) {
            try {
                Class<?> filterClass = Class.forName(matchedFilter.getClassName(), true, module.getClassLoader());

                matchedFilterInstances.add((Filter) Class.forName(filterClass.getModule(), filterClass.getName()).getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

        return new FilterChainImpl(matchedFilterInstances, servletInstance);
    }
}
