package es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml;

import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.FilterMappingType;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.FilterType;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.ObjectFactory;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.ServletMappingType;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.WebApp;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.FilterDescriptor;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.ServletDescriptor;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toSet;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 *
 * @author guillermo
 */
public class EffectiveWebXml {

    private Set<ServletDescriptor> servletDescriptors;
    private Set<FilterDescriptor> filterDescriptors;

    public EffectiveWebXml(InputStream webXmlPath, ClassLoader warClassLoader) {
        try {
            WebApp webApp = (WebApp) JAXBContext.newInstance(ObjectFactory.class.getPackageName()).
                    createUnmarshaller().
                    unmarshal(webXmlPath);

            this.servletDescriptors = findServlets(webApp, warClassLoader);

            boolean anyMatch = servletDescriptors.stream().
                    map(ServletDescriptor::getExactPatterns).
                    anyMatch(p -> p.contains("/*"));
            if (!anyMatch) {
                // No Servlet mapped to "/*". Register FileServlet with the default classloader
                servletDescriptors.add(new ServletDescriptor("FileServlet", es.guillermogonzalezdeaguero.servlet.impl.system.FileServlet.class, Set.of("/*")));
            }

            this.filterDescriptors = findFilters(webApp, warClassLoader);
        } catch (JAXBException | ClassNotFoundException e) {
            throw new WebXmlProcessingException(e);
        }
    }

    private static Set<ServletDescriptor> findServlets(WebApp webApp, ClassLoader classLoader) {
        Map<String, List<ServletMappingType>> collect = webApp.getServletMappings().
                stream().
                collect(Collectors.groupingBy(smt -> smt.getServletName().getValue()));

        return webApp.getServlets().
                stream().
                map(st -> {
                    try {
                        return new ServletDescriptor(st, collect.getOrDefault(st.getServletName().getValue(), Collections.emptyList()), classLoader);
                    } catch (ClassNotFoundException ex) {
                        throw new WebXmlProcessingException(ex);
                    }
                }).collect(toSet());
    }

    private static Set<FilterDescriptor> findFilters(WebApp webApp, ClassLoader classLoader) throws ClassNotFoundException {
        Map<String, List<FilterMappingType>> collect = webApp.getFilterMappings().
                stream().
                collect(Collectors.groupingBy(s -> s.getFilterName().getValue()));

        Set<FilterDescriptor> filterDescriptors = new HashSet<>();

        // TODO: Check whether position counts from definition or mapping of filters
        int position = 0;
        for (FilterType filter : webApp.getFilters()) {
            filterDescriptors.add(new FilterDescriptor(filter, collect.getOrDefault(filter.getFilterName().getValue(), Collections.emptyList()), classLoader, ++position));
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
