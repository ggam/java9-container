package eu.ggam.servlet.impl.descriptor.materialized;

import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ServletMappingType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.WebXml;
import eu.ggam.servlet.impl.descriptor.ServletDescriptor;
import eu.ggam.servlet.impl.descriptor.WebXmlProcessingException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toSet;

/**
 *
 * @author Guillermo González de Agüero
 */
public final class ServletsParser {

    private ServletsParser() {
    }

    public static Set<ServletDescriptor> findServlets(WebXml webXml, ClassLoader classLoader) {
        Map<String, List<ServletMappingType>> collect = webXml.getServletMappings().
                stream().
                collect(Collectors.groupingBy(smt -> smt.getServletName().getValue()));

        return webXml.getServlets().
                stream().
                map(st -> {
                    try {
                        return ServletDescriptor.createFromWebXml(st, collect.getOrDefault(st.getServletName().getValue(), Collections.emptyList()), classLoader);
                    } catch (ClassNotFoundException ex) {
                        throw new WebXmlProcessingException(ex);
                    }
                }).collect(toSet());
    }
}
