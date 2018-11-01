package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.ContextParamMetamodel;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.ServletMappingMetamodel;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.ServletMetamodel;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.WebXml;
import eu.ggam.container.impl.servletcontainer.jsr154.ServletContextImpl;
import eu.ggam.container.impl.servletcontainer.rootwebapp.FileServlet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.toMap;
import javax.servlet.ServletContext;

/**
 *
 * @author Guillermo González de Agüero
 */
public final class MaterializedWebApp {

    private static final String DEFAULT_SERVLET_NAME = "eu.ggam.servlet.DefaultServlet";

    private final ServletContext servletContext;
    private final UrlPatternsParser urlPatternsParser;
    private final ServletFactory servletFactory;
    private final FilterFactory filterFactory;

    public MaterializedWebApp(WebXml webXml, Module warModule) {
        ListenerParser listenerParser = new ListenerParser(webXml.getListeners(), warModule.getClassLoader());

        Map<String, String> contextParams = webXml.getContextParams().
                stream().
                collect(toMap(ContextParamMetamodel::getParamName, ContextParamMetamodel::getParamValue));
        contextParams.put(ServletContextImpl.InitParams.WEBAPP_MODULE, warModule.getName());

        // TODO: find a way to defer instantiation
        servletContext = new ServletContextFactory(
                contextParams,
                listenerParser.getListenersForType(ListenerParser.ListenerType.SERVLET_CONTEXT_LISTENER),
                listenerParser.getListenersForType(ListenerParser.ListenerType.SERVLET_CONTEXT_ATTRIBUTELISTENER),
                warModule.getClassLoader()).get();

        Set<ServletMappingMetamodel> servletMappings = new HashSet<>(webXml.getServletMappings());
        UninitializedServlet fileServlet = null;

        // Check if there's a Servlet mapped to / (default Servlet)
        long count = webXml.getServletMappings().
                stream().
                map(ServletMappingMetamodel::getUrlPatterns).
                flatMap(Set::stream).
                filter("/"::equals).
                count();
        if (count == 0) {
            // No default Servlet. Add the FileServlet
            fileServlet = new UninitializedServlet(new ServletMetamodel(DEFAULT_SERVLET_NAME, FileServlet.class.getName()), FileServlet.class);
            servletMappings.add(new ServletMappingMetamodel(DEFAULT_SERVLET_NAME, Set.of("/")));
        }

        urlPatternsParser = new UrlPatternsParser(servletMappings, webXml.getFilterMappings());

        servletFactory = new ServletFactory(servletContext, webXml.getServlets(), warModule.getClassLoader(), fileServlet);
        filterFactory = new FilterFactory(servletContext, webXml.getFilters(), warModule.getClassLoader());
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public UrlPatternsParser getUrlPatternsParser() {
        return urlPatternsParser;
    }

    public ServletFactory getServletFactory() {
        return servletFactory;
    }

    public FilterFactory getFilterFactory() {
        return filterFactory;
    }

}
