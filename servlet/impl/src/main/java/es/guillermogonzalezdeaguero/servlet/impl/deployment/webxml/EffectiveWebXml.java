package es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml;

import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.FilterMappingType;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.FilterType;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.ObjectFactory;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.ServletMappingType;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.ServletType;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.WebApp;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.FilterDescriptor;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.ServletDescriptor;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
                // No Servlet mapped to "/*". Register FileServlet with the default classloader
                servletDescriptors.add(new ServletDescriptor("FileServlet", es.guillermogonzalezdeaguero.servlet.impl.system.FileServlet.class, Set.of("/*")));
            }

            this.filterDescriptors = findFilters(webApp, classLoader);
        } catch (JAXBException | ClassNotFoundException e) {
            throw new WebXmlProcessingException(e);
        }
    }

    private static Set<ServletDescriptor> findServlets(WebApp webApp, ClassLoader classLoader) throws ClassNotFoundException {
        Set<ServletDescriptor> servletDescriptors = new HashSet<>();
        for (ServletType servlet : webApp.getServlets()) {
            List<ServletMappingType> mappings = new ArrayList<>();
            for (ServletMappingType servletMapping : webApp.getServletMappings()) {
                if (servletMapping.getServletName().getValue().equals(servlet.getServletName().getValue())) {
                    mappings.add(servletMapping);
                }
            }
            
            servletDescriptors.add(new ServletDescriptor(servlet, mappings, classLoader));
        }

        return servletDescriptors;
    }

    private static Set<FilterDescriptor> findFilters(WebApp webApp, ClassLoader classLoader) throws ClassNotFoundException {
        Set<FilterDescriptor> filterDescriptors = new HashSet<>();
        
        // TODO: Check whether position counts from definition or mapping of filters
        int position = 0;
        for (FilterType filter : webApp.getFilters()) {
            List<FilterMappingType> mappings = new ArrayList<>();
            for (FilterMappingType filterMapping : webApp.getFilterMappings()) {
                if (filterMapping.getFilterName().getValue().equals(filter.getFilterName().getValue())) {
                    mappings.add(filterMapping);
                }
            }
            filterDescriptors.add(new FilterDescriptor(filter, mappings, classLoader, ++position));
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
