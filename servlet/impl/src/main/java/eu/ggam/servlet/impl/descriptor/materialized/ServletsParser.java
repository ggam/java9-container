package eu.ggam.servlet.impl.descriptor.materialized;

import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ServletMappingType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.WebXml;
import eu.ggam.servlet.impl.descriptor.ServletDescriptor;
import eu.ggam.servlet.impl.descriptor.WebXmlProcessingException;
import eu.ggam.servlet.impl.jsr154.ServletContextImpl;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toSet;

/**
 *
 * @author guillermo
 */
public final class ServletsParser {

    private ServletsParser() {
    }

    public static Set<ServletDescriptor> findServlets(ServletContextImpl servletContext, WebXml webApp) {
        Objects.requireNonNull(servletContext);
        
        Map<String, List<ServletMappingType>> collect = webApp.getServletMappings().
                stream().
                collect(Collectors.groupingBy(smt -> smt.getServletName().getValue()));

        return webApp.getServlets().
                stream().
                map(st -> {
                    try {
                        return ServletDescriptor.createFromWebXml(servletContext, st, collect.getOrDefault(st.getServletName().getValue(), Collections.emptyList()));
                    } catch (ClassNotFoundException ex) {
                        throw new WebXmlProcessingException(ex);
                    }
                }).collect(toSet());
    }
}
