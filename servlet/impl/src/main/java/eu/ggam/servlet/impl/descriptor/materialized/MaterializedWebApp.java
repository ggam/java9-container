package eu.ggam.servlet.impl.descriptor.materialized;

import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ObjectFactory;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ParamValueType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.WebXml;
import eu.ggam.servlet.impl.descriptor.FilterDescriptor;
import eu.ggam.servlet.impl.descriptor.ServletDescriptor;
import eu.ggam.servlet.impl.descriptor.WebXmlProcessingException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.counting;
import javax.servlet.Servlet;
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

        private String fileServletName;
        private String fileServletClassName;

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

        public Builder fileServlet(String servletName, String className) {
            ensureNotBuilt();

            fileServletName = servletName;
            fileServletClassName = className;

            return this;
        }

        public MaterializedWebApp build() {
            if (fileServletName == null || fileServletClassName == null) {
                throw new IllegalStateException("File Servlet not set");
            }

            built = true;

            WebXml webXml;
            try (InputStream is = Files.newInputStream(appPath.resolve(Paths.get("WEB-INF", "web.xml")))) {
                webXml = (WebXml) JAXBContext.newInstance(ObjectFactory.class.getPackageName()).
                        createUnmarshaller().
                        unmarshal(is);
            } catch (JAXBException | IOException e) {
                throw new RuntimeException(e); // TODO: Deployment exception
            }

            return new MaterializedWebApp(webXml, contextPath, classLoader, contextParams, fileServletName, fileServletClassName);
        }

        private void ensureNotBuilt() {
            if (built) {
                throw new IllegalStateException("Object already built");
            }
        }
    }

    private final ClassLoader classLoader;
    private final String contextPath;
    private final Map<String, String> contextParams;
    private final Set<ServletDescriptor> servletDescriptors;
    private final Set<FilterDescriptor> filterDescriptors;

    private final ContextListeners contextListeners;
    private final RequestListeners requestListeners;
    private final SessionListeners sessionListeners;

    private MaterializedWebApp(WebXml webXml, String contextPath, ClassLoader warClassLoader, Map<String, String> servletContextParams, String fileServletName, String fileServletClassName) {
        try {
            this.classLoader = warClassLoader;
            this.contextPath = contextPath;
            this.contextParams = Collections.unmodifiableMap(createServletContext(webXml.getContextParams(), servletContextParams));
            this.contextListeners = new ContextListeners(webXml.getListeners(), warClassLoader);

            this.requestListeners = new RequestListeners(webXml.getListeners(), warClassLoader);
            this.sessionListeners = new SessionListeners(webXml.getListeners(), warClassLoader);

            this.servletDescriptors = ServletsParser.findServlets(webXml, warClassLoader);
            this.filterDescriptors = FiltersParser.findFilters(webXml, warClassLoader);

            validate(fileServletName, fileServletClassName);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
            throw new WebXmlProcessingException(e);
        }
    }

    private Map<String, String> createServletContext(List<ParamValueType> xmlContextParams, Map<String, String> additionalInitParams) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<String, String> initParams = new HashMap<>();
        for (ParamValueType xmlContextParam : xmlContextParams) {
            initParams.put(xmlContextParam.getParamName().getValue(), xmlContextParam.getParamValue().getValue());
        }

        // Override user provided params with our own
        initParams.putAll(additionalInitParams);

        return initParams;
    }

    private void validate(String fileServletName, String fileServletClassName) throws ClassNotFoundException {
        int count = servletDescriptors.stream().
                filter(ServletDescriptor::isDefaultServlet).
                collect(counting()).
                intValue();

        Class<? extends Servlet> fileServlet = (Class<? extends Servlet>) Class.forName(fileServletClassName, true, classLoader);
        switch (count) {
            case 1:
                // Default Servlet is already set. FileServlet is just another Servlet
                servletDescriptors.add(ServletDescriptor.createWithoutMappings(fileServletName, fileServlet));
                break;
            case 0:
                // No default Servlet. Add a new one with the server ClassLoader
                servletDescriptors.add(ServletDescriptor.createDefault(fileServletClassName, fileServlet));
                break;
            default:
                // More than one default Servlet
                throw new WebXmlProcessingException("Multiple Servlets mapped as default");
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