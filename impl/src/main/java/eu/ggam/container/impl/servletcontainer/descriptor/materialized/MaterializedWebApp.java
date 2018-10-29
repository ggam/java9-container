package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import eu.ggam.container.impl.servletcontainer.descriptor.FilterDescriptor;
import eu.ggam.container.impl.servletcontainer.descriptor.ServletDescriptor;
import eu.ggam.container.impl.servletcontainer.descriptor.WebXmlProcessingException;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.ContextParamMetamodel;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.WebXml;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.module.ModuleReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.counting;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Guillermo González de Agüero
 */
public final class MaterializedWebApp {

    private static final String WEB_XML_LOCATION = "_ROOT_/WEB-INF/web.xml";
    
    public static final String DEFAULT_SERVLET_NAME = "eu.ggam.servlet.DefaultServlet";

    public static class Builder {

        private final Module module;

        private final Map<String, String> contextParams = new HashMap<>();

        private String defaultServletClassName;

        private boolean built;

        public Builder(Module module) {
            this.module = module;
        }

        public Builder contextParam(String param, String value) {
            ensureNotBuilt();

            contextParams.put(param, value);

            return this;
        }

        public Builder defaultServlet(String className) {
            ensureNotBuilt();

            defaultServletClassName = className;

            return this;
        }

        public MaterializedWebApp build() {
            built = true;

            
            try (ModuleReader reader = module.getLayer().configuration().findModule(module.getName()).get().reference().open();
                    InputStream is = reader.open(WEB_XML_LOCATION).get();
                    BufferedInputStream bis = new BufferedInputStream(is)) {
                
                WebXml webXml = new WebXml(bis);
                
                return new MaterializedWebApp(webXml, getClass().getClassLoader(), contextParams, defaultServletClassName);
            } catch (IOException | XMLStreamException e) {
                throw new WebXmlProcessingException(e);
            }
        }

        private void ensureNotBuilt() {
            if (built) {
                throw new IllegalStateException("Object already built");
            }
        }
    }

    private final ClassLoader classLoader;
    private final Map<String, String> contextParams;
    private final Set<ServletDescriptor> servletDescriptors;
    private final Set<FilterDescriptor> filterDescriptors;

    private final ContextListeners contextListeners;
    private final RequestListeners requestListeners;
    private final SessionListeners sessionListeners;

    private MaterializedWebApp(WebXml webXml, ClassLoader warClassLoader, Map<String, String> servletContextParams, String fileServletClassName) {
        try {
            this.classLoader = warClassLoader;
            this.contextParams = Collections.unmodifiableMap(createServletContext(webXml.getContextParams(), servletContextParams));
            this.contextListeners = new ContextListeners(webXml.getListeners(), warClassLoader);

            this.requestListeners = new RequestListeners(webXml.getListeners(), warClassLoader);
            this.sessionListeners = new SessionListeners(webXml.getListeners(), warClassLoader);

            this.servletDescriptors = ServletsParser.findServlets(webXml, warClassLoader);
            this.filterDescriptors = FiltersParser.findFilters(webXml, warClassLoader);

            validate(fileServletClassName);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
            throw new WebXmlProcessingException(e);
        }
    }

    private Map<String, String> createServletContext(Set<ContextParamMetamodel> xmlContextParams, Map<String, String> additionalInitParams) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<String, String> initParams = new HashMap<>();
        for (ContextParamMetamodel xmlContextParam : xmlContextParams) {
            initParams.put(xmlContextParam.getParamName(), xmlContextParam.getParamValue());
        }

        // Override user provided params with our own
        initParams.putAll(additionalInitParams);

        return initParams;
    }

    private void validate(String defaultServletClassName) throws ClassNotFoundException {
        int count = servletDescriptors.stream().
                filter(ServletDescriptor::isDefaultServlet).
                collect(counting()).
                intValue();

        if (count > 1) {
            throw new WebXmlProcessingException("Multiple Servlets mapped as default");
        }

        if (count == 0 && defaultServletClassName == null) {
            throw new WebXmlProcessingException("No default Servlet provided");
        }

        if (count == 0) {
            // No default Servlet. Add a new one with the server ClassLoader
            servletDescriptors.add(new ServletDescriptor.Builder(DEFAULT_SERVLET_NAME, defaultServletClassName, classLoader).
                    defaultServlet().
                    build());
        }
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public Map<String, String> getContextParams() {
        return contextParams;
    }

    public Set<ServletDescriptor> getServletDescriptors() {
        return servletDescriptors;
    }

    public Set<FilterDescriptor> getFilterDescriptors() {
        return filterDescriptors;
    }

    public ContextListeners getContextListeners() {
        return contextListeners;
    }

    public SessionListeners getSessionListeners() {
        return sessionListeners;
    }

    public RequestListeners getRequestListeners() {
        return requestListeners;
    }

}
