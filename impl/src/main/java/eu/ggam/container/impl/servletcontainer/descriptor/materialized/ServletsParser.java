package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import eu.ggam.container.impl.servletcontainer.descriptor.MatchingPattern;
import eu.ggam.container.impl.servletcontainer.descriptor.ServletDescriptor;
import eu.ggam.container.impl.servletcontainer.descriptor.WebXmlProcessingException;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.ServletMappingMetamodel;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.WebXml;
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
        Map<String, List<ServletMappingMetamodel>> collect = webXml.getServletMappings().
                stream().
                collect(Collectors.groupingBy(smt -> smt.getServletName()));

        return webXml.getServlets().
                stream().
                map(st -> {
                    try {
                        Set<MatchingPattern> urlPatterns = collect.getOrDefault(st.getServletName(), Collections.emptyList()).
                                stream().
                                map(ServletMappingMetamodel::getUrlPatterns).
                                flatMap(Set::stream).
                                map(MatchingPattern::createUrlPattern).
                                collect(toSet());

                        Map<String, String> initParams = st.getInitParams().
                                stream().
                                collect(Collectors.toMap(it -> it.getParamName(), it -> it.getParamValue()));

                        return new ServletDescriptor.Builder(st.getServletName(), st.getServletClass(), classLoader).
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
