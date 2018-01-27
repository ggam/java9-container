package es.guillermogonzalezdeaguero.servlet.impl;

import es.guillermogonzalezdeaguero.servlet.impl.deployment.FilterMatcher;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.ServletMatcher;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.EffectiveWebXml;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.ServletDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Queue;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

/**
 *
 * @author guillermo
 */
public class FilterChainFactory {

    private final EffectiveWebXml webXml;

    private final ServletMatcher servletMatcher;
    private final FilterMatcher filterMatcher;

    public FilterChainFactory(Path webXmlPath, ClassLoader classLoader) {
        try ( InputStream is = Files.newInputStream(webXmlPath)) {
            webXml = new EffectiveWebXml(is, classLoader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        servletMatcher = new ServletMatcher(webXml.getServletDescriptors());
        filterMatcher = new FilterMatcher(webXml.getFilterDescriptors());
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
