package es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml;

import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.FilterMappingType;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.FilterType;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.ObjectFactory;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.ServletMappingType;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.ServletType;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.UrlPatternType;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.WebApp;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.FilterDescriptor;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.ServletDescriptor;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 *
 * @author guillermo
 */
public class EffectiveWebXml {

    private Set<ServletDescriptor> servletDescriptors;
    private Set<FilterDescriptor> filterDescriptors;

    public EffectiveWebXml(InputStream webXmlPath, ClassLoader classLoader) {
        try {
            WebApp webApp = (WebApp) JAXBContext.newInstance(ObjectFactory.class.getPackageName()).
                    createUnmarshaller().
                    unmarshal(webXmlPath);

            this.servletDescriptors = findServlets(webApp, classLoader);

            boolean anyMatch = servletDescriptors.stream().
                    map(ServletDescriptor::getExactPatterns).
                    anyMatch(p -> p.contains("/*"));
            if (!anyMatch) {
                // No Servlet mapped to "/*". Register FileServlet
                servletDescriptors.add(new ServletDescriptor("FileServlet", es.guillermogonzalezdeaguero.servlet.impl.system.FileServlet.class.getName(), getClass().getClassLoader(), Set.of("/*")));
            }

            this.filterDescriptors = findFilters(webApp, classLoader);
        } catch (JAXBException | ClassNotFoundException e) {
            throw new WebXmlProcessingException(e);
        }
    }

    private static Set<ServletDescriptor> findServlets(WebApp webApp, ClassLoader classLoader) throws ClassNotFoundException {
        Set<ServletDescriptor> servletDescriptors = new HashSet<>();
        for (ServletType servlet : webApp.getServlets()) {
            Set<String> urlPatterns = new HashSet<>();
            for (ServletMappingType servletMapping : webApp.getServletMappings()) {
                if (servletMapping.getServletName().getValue().equals(servlet.getServletName().getValue())) {
                    for (UrlPatternType urlPattern : servletMapping.getUrlPatterns()) {
                        urlPatterns.add(urlPattern.getValue());
                    }
                }
            }
            servletDescriptors.add(new ServletDescriptor(servlet.getServletName().getValue(), servlet.getServletClass().getValue(), classLoader, urlPatterns));
        }

        return servletDescriptors;
    }

    private static Set<FilterDescriptor> findFilters(WebApp webApp, ClassLoader classLoader) throws ClassNotFoundException {
        Set<FilterDescriptor> filterDescriptors = new HashSet<>();
        
        // Check whether position counts from definition or mapping of filters
        int position = 0;
        for (FilterType filter : webApp.getFilters()) {
            Set<String> urlPatterns = new HashSet<>();
            for (FilterMappingType filterMapping : webApp.getFilterMappings()) {
                if (filterMapping.getFilterName().getValue().equals(filter.getFilterName().getValue())) {
                    for (Object urlPattern : filterMapping.getUrlPatterns()) {
                        urlPatterns.add(((UrlPatternType) urlPattern).getValue());
                    }
                }
            }
            filterDescriptors.add(new FilterDescriptor(filter.getFilterName().getValue(), filter.getFilterClass().getValue(), classLoader, ++position, urlPatterns, Collections.emptySet()));
        }

        return filterDescriptors;
    }

    public Set<ServletDescriptor> getServletDescriptors() {
        return servletDescriptors;
    }

    public Set<FilterDescriptor> getFilterDescriptors() {
        return filterDescriptors;
    }

}
