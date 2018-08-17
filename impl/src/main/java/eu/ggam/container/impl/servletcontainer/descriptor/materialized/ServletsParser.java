package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import eu.ggam.container.impl.servletcontainer.com.sun.java.xml.ns.javaee.ServletMappingType;
import eu.ggam.container.impl.servletcontainer.com.sun.java.xml.ns.javaee.UrlPatternType;
import eu.ggam.container.impl.servletcontainer.com.sun.java.xml.ns.javaee.WebXml;
import eu.ggam.container.impl.servletcontainer.descriptor.MatchingPattern;
import eu.ggam.container.impl.servletcontainer.descriptor.ServletDescriptor;
import eu.ggam.container.impl.servletcontainer.descriptor.WebXmlProcessingException;
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
                        Set<MatchingPattern> urlPatterns = collect.getOrDefault(st.getServletName().getValue(), Collections.emptyList()).
                                stream().
                                map(ServletMappingType::getUrlPatterns).
                                flatMap(List::stream).
                                map(UrlPatternType::getValue).
                                map(MatchingPattern::createUrlPattern).
                                collect(toSet());

                        Map<String, String> initParams = st.getInitParams().
                                stream().
                                collect(Collectors.toMap(it -> it.getParamName().getValue(), it -> it.getParamValue().getValue()));

                        return new ServletDescriptor.Builder(st.getServletName().getValue(), st.getServletClass().getValue(), classLoader).
                                addMappings(urlPatterns).
                                addInitParams(initParams).
                                build();
                    } catch (ClassNotFoundException e) {
                        throw new WebXmlProcessingException(e);
                    }
                }).
                collect(toSet());
    }
}
