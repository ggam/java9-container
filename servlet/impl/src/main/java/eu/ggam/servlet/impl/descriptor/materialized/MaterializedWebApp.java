package eu.ggam.servlet.impl.descriptor.materialized;

import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ObjectFactory;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ParamValueType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.WebXml;
import eu.ggam.servlet.impl.descriptor.FilterDescriptor;
import eu.ggam.servlet.impl.descriptor.ServletDescriptor;
import eu.ggam.servlet.impl.descriptor.WebXmlProcessingException;
import eu.ggam.servlet.impl.jsr154.ServletContextImpl;
import eu.ggam.servlet.impl.rootwebapp.FileServlet;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.counting;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 *
 * @author guillermo
 */
public final class MaterializedWebApp {

    public static class Builder {

        private final Path appPath;
        private final ClassLoader classLoader;
        private final String contextPath;

        private final Map<String, String> contextParams = new HashMap<>();

        private boolean built;

        public Builder(Path appPath, ClassLoader classLoader, String contextPath) {
            this.appPath = appPath;
            this.classLoader = classLoader;
            this.contextPath = contextPath;
        }

        public Builder contextParam(String param, String value) {
            ensureNotBuilt();

            contextParams.put(param, value);

            return this;
        }

        public MaterializedWebApp build() {
            built = true;

            WebXml webXml;
            try (InputStream is = Files.newInputStream(appPath.resolve(Paths.get("WEB-INF", "web.xml")))) {
                webXml = (WebXml) JAXBContext.newInstance(ObjectFactory.class.getPackageName()).
                        createUnmarshaller().
                        unmarshal(is);
            } catch (JAXBException | IOException e) {
                throw new RuntimeException(e); // TODO: Deployment exception
            }

            return new MaterializedWebApp(webXml, contextPath, classLoader, contextParams);
        }

        private void ensureNotBuilt() {
            if (built) {
                throw new IllegalStateException("Object already built");
            }
        }
    }

    private ServletContextImpl servletContext;
    private final Set<ServletDescriptor> servletDescriptors;
    private final Set<FilterDescriptor> filterDescriptors;

    private final ContextListeners contextListeners;
    private final RequestListeners requestListeners;
    private final SessionListeners sessionListeners;

    private MaterializedWebApp(WebXml webXml, String contextPath, ClassLoader warClassLoader, Map<String, String> servletContextParams) {
        try {

            contextListeners = new ContextListeners(webXml.getListeners(), warClassLoader);
            requestListeners = new RequestListeners(webXml.getListeners(), warClassLoader);
            sessionListeners = new SessionListeners(webXml.getListeners(), warClassLoader);

            servletContext = createServletContext(webXml, contextPath, warClassLoader, new HashMap<>(servletContextParams));
            
            servletDescriptors = ServletsParser.findServlets(servletContext, webXml);
            filterDescriptors = FiltersParser.findFilters(servletContext, webXml);

            validate();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
            throw new WebXmlProcessingException(e);
        }
    }

    private ServletContextImpl createServletContext(WebXml webApp, String contextPath, ClassLoader classLoader, Map<String, String> additionalInitParams) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<String, String> initParams = new HashMap<>();
        for (ParamValueType contextParams : webApp.getContextParams()) {
            initParams.put(contextParams.getParamName().getValue(), contextParams.getParamValue().getValue());
        }

        initParams.putAll(additionalInitParams);

        return new ServletContextImpl(classLoader, contextPath, contextListeners.getContextAttributeListeners(), initParams);
    }

    private void validate() {
        int count = servletDescriptors.stream().
                filter(ServletDescriptor::isDefaultServlet).
                collect(counting()).
                intValue();

        Class<FileServlet> fileServlet = eu.ggam.servlet.impl.rootwebapp.FileServlet.class;
        switch (count) {
            case 1:
                // Default Servlet is already set. FileServlet is just another Servlet
                servletDescriptors.add(ServletDescriptor.createWithoutMappings(servletContext, fileServlet.getSimpleName(), fileServlet));
                break;
            case 0:
                // No default Servlet. Add a new one with the server ClassLoader
                servletDescriptors.add(ServletDescriptor.createDefault(servletContext, fileServlet.getSimpleName(), fileServlet));
                break;
            default:
                // More than one default Servlet
                throw new WebXmlProcessingException("Multiple Servlets mapped as default");
        }
    }

    public ServletContextImpl getServletContext() {
        return servletContext;
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
