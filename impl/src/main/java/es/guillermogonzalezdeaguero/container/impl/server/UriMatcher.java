package es.guillermogonzalezdeaguero.container.impl.server;

import es.guillermogonzalezdeaguero.container.impl.server.deployment.WebApplication;
import es.guillermogonzalezdeaguero.container.impl.server.deployment.webxml.EffectiveWebXml;
import es.guillermogonzalezdeaguero.container.impl.server.deployment.webxml.descriptor.FilterDescriptor;
import es.guillermogonzalezdeaguero.container.impl.server.deployment.webxml.descriptor.ServletDescriptor;
import es.guillermogonzalezdeaguero.container.impl.servlet.FilterChainImpl;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.Servlet;

/**
 *
 * @author guillermo
 */
public class UriMatcher {

    private final Set<WebApplication> webApps;

    public UriMatcher(Set<WebApplication> webApps) {
        this.webApps = new HashSet<>(webApps);
    }

    public Optional<FilterChain> match(String url) {
        System.out.println("matching url " + url);

        Optional<WebApplication> optionalWebApp = webApps.stream().
                filter(webApp -> url.startsWith(webApp.getContextPath())).
                findAny();

        if (!optionalWebApp.isPresent()) {
            // No application matches the context path
            return Optional.empty();
        }

        WebApplication webApp = optionalWebApp.get();
        EffectiveWebXml effectiveWebXml = webApp.getEffectiveWebXml();

        String pathInfo = url.substring(webApp.getContextPath().length(), url.length()); // TODO: pathInfo may be null?
        System.out.println("pathInfo" + pathInfo);

        ServletDescriptor servletMatch = findServletMatch(effectiveWebXml.getServletDescriptors(), pathInfo);
        Queue<FilterDescriptor> matchedFilters = findFilterMatches(effectiveWebXml.getFilterDescriptors(), pathInfo);

        if (servletMatch == null && matchedFilters.isEmpty()) {
            return Optional.empty();
        }

        Servlet servletInstance = null;
        if (servletMatch != null) {
            try {
                Class<?> servletClass = Class.forName(servletMatch.getClassName(), true, webApp.getWarModule().getClassLoader());

                servletInstance = (Servlet) Class.forName(servletClass.getModule(), servletClass.getName()).getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

        Queue<Filter> matchedFilterInstances = new ArrayDeque<>();
        for (FilterDescriptor matchedFilter : matchedFilters) {
            try {
                Class<?> filterClass = Class.forName(matchedFilter.getClassName(), true, webApp.getWarModule().getClassLoader());

                matchedFilterInstances.add((Filter) Class.forName(filterClass.getModule(), filterClass.getName()).getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

        return Optional.of(new FilterChainImpl(matchedFilterInstances, servletInstance));
    }

    public ServletDescriptor findServletMatch(Set<ServletDescriptor> servletDescriptors, String pathInfo) {
        for (ServletDescriptor servletDescriptor : servletDescriptors) {
            for (String exactPattern : servletDescriptor.getExactPatterns()) {
                if (exactPattern.equals(pathInfo) || (pathInfo == null && "/".equals(exactPattern))) {
                    return servletDescriptor;
                }
            }

            for (String prefixPattern : servletDescriptor.getPrefixPatterns()) {
                String prefix = prefixPattern.substring(0, prefixPattern.length() - 1);
                if (pathInfo != null && pathInfo.startsWith(prefix)) {
                    return servletDescriptor;
                }
            }

            if (pathInfo == null) {
                return null; // TODO: Default Servlet?
            }

            for (String extensionPattern : servletDescriptor.getExtensionPatterns()) {
                String extension = extensionPattern.split(".")[1];
                if (pathInfo.endsWith(extension)) {
                    return servletDescriptor;
                }
            }

        }
        return null;
    }

    public Queue<FilterDescriptor> findFilterMatches(Set<FilterDescriptor> filterDescriptors, String pathInfo) {
        Queue<FilterDescriptor> matchedFilters = new ArrayDeque<>();
        for (FilterDescriptor filterDescriptor : filterDescriptors) {
            for (String exactPattern : filterDescriptor.getExactPatterns()) {
                if (exactPattern.equals(pathInfo) || (pathInfo == null && "/".equals(exactPattern))) {
                    matchedFilters.add(filterDescriptor);
                }
            }

            for (String prefixPattern : filterDescriptor.getPrefixPatterns()) {
                String prefix = prefixPattern.substring(0, prefixPattern.length() - 1);
                if (pathInfo != null && pathInfo.startsWith(prefix)) {
                    matchedFilters.add(filterDescriptor);
                }
            }

            if (pathInfo == null) {
                // TODO: pending review
                // matchedFilters.add(filterDescriptor);
            }

            for (String extensionPattern : filterDescriptor.getExtensionPatterns()) {
                String extension = extensionPattern.split(".")[1];
                if (pathInfo != null && pathInfo.endsWith(extension)) {
                    matchedFilters.add(filterDescriptor);
                }
            }

        }
        return matchedFilters;
    }
}
