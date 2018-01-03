package es.guillermogonzalezdeaguero.container.impl.server.deployment.webxml;

import com.sun.java.xml.ns.javaee.FilterMappingType;
import com.sun.java.xml.ns.javaee.FilterType;
import com.sun.java.xml.ns.javaee.ServletMappingType;
import com.sun.java.xml.ns.javaee.ServletType;
import com.sun.java.xml.ns.javaee.UrlPatternType;
import com.sun.java.xml.ns.javaee.WebApp;
import es.guillermogonzalezdeaguero.container.impl.server.deployment.webxml.descriptor.FilterDescriptor;
import es.guillermogonzalezdeaguero.container.impl.server.deployment.webxml.descriptor.ServletDescriptor;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 *
 * @author guillermo
 */
public class WebXmlParser {

    public static EffectiveWebXml parse(Path webXmlPath) {

        try {
            WebApp webApp = (WebApp) JAXBContext.newInstance("com.sun.java.xml.ns.javaee").
                    createUnmarshaller().
                    unmarshal(webXmlPath.toUri().toURL());

            return new EffectiveWebXml(findServlets(webApp), findFilters(webApp));
        } catch (MalformedURLException | JAXBException e) {
            throw new WebXmlParsingException(e);
        }
    }

    private static Set<ServletDescriptor> findServlets(WebApp webApp) {
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
            servletDescriptors.add(new ServletDescriptor(servlet.getServletName().getValue(), servlet.getServletClass().getValue(), urlPatterns));
        }

        return servletDescriptors;
    }

    private static Set<FilterDescriptor> findFilters(WebApp webApp) {
        Set<FilterDescriptor> filterDescriptors = new HashSet<>();
        for (FilterType filter : webApp.getFilters()) {
            Set<String> urlPatterns = new HashSet<>();
            for (FilterMappingType filterMapping : webApp.getFilterMappings()) {
                if (filterMapping.getFilterName().getValue().equals(filter.getFilterName().getValue())) {
                    for (Object urlPattern : filterMapping.getUrlPatterns()) {
                        urlPatterns.add(((UrlPatternType) urlPattern).getValue());
                    }
                }
            }
            filterDescriptors.add(new FilterDescriptor(filter.getFilterName().getValue(), filter.getFilterClass().getValue(), urlPatterns, Collections.emptySet()));
        }

        return filterDescriptors;
    }

}
