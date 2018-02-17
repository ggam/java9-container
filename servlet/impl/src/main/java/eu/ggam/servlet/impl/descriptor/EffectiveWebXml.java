package eu.ggam.servlet.impl.descriptor;

import eu.ggam.servlet.impl.jsr154.ServletContextImpl;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.FilterMappingType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.FilterType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ListenerType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ObjectFactory;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ParamValueType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ServletMappingType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.WebApp;
import eu.ggam.servlet.impl.rootwebapp.FileServlet;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.toSet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeListener;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 *
 * @author guillermo
 */
public class EffectiveWebXml {

    private final Set<ServletDescriptor> servletDescriptors;
    private final Set<FilterDescriptor> filterDescriptors;
    private ServletContextImpl servletContext;

    public EffectiveWebXml(String contextPath, InputStream webXmlPath, ClassLoader warClassLoader) {
        try {
            WebApp webApp = (WebApp) JAXBContext.newInstance(ObjectFactory.class.getPackageName()).
                    createUnmarshaller().
                    unmarshal(webXmlPath);

            servletContext = createServletContext(webApp, contextPath, warClassLoader);

            servletDescriptors = findServlets(servletContext, webApp);
            filterDescriptors = findFilters(servletContext, webApp);

            validate();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | JAXBException | ClassNotFoundException e) {
            throw new WebXmlProcessingException(e);
        }
    }

    private ServletContextImpl createServletContext(WebApp webApp, String contextPath, ClassLoader classLoader) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        List<ListenerType> listenerTypes = webApp.getListeners();

        List<ServletContextAttributeListener> attributeListeners = new ArrayList<>();
        for (ListenerType listenerType : listenerTypes) {
            Class<?> forName = Class.forName(listenerType.getListenerClass().getValue(), false, classLoader);
            if (ServletContextAttributeListener.class.isAssignableFrom(forName)) {
                attributeListeners.add((ServletContextAttributeListener) forName.getDeclaredConstructor().newInstance());
            }
        }

        Map<String, String> initParams = new HashMap<>();
        for (ParamValueType contextParams : webApp.getContextParams()) {
            initParams.put(contextParams.getParamName().getValue(), contextParams.getParamValue().getValue());
        }

        return new ServletContextImpl(classLoader, contextPath, attributeListeners, initParams);
    }

    private Set<ServletDescriptor> findServlets(ServletContextImpl servletContext, WebApp webApp) {
        Map<String, List<ServletMappingType>> collect = webApp.getServletMappings().
                stream().
                collect(Collectors.groupingBy(smt -> smt.getServletName().getValue()));

        return webApp.getServlets().
                stream().
                map(st -> {
                    try {
                        return new ServletDescriptor(servletContext, st, collect.getOrDefault(st.getServletName().getValue(), Collections.emptyList()));
                    } catch (ClassNotFoundException ex) {
                        throw new WebXmlProcessingException(ex);
                    }
                }).collect(toSet());
    }

    private Set<FilterDescriptor> findFilters(ServletContextImpl servletContext, WebApp webApp) throws ClassNotFoundException {
        Map<String, List<FilterMappingType>> collect = webApp.getFilterMappings().
                stream().
                collect(Collectors.groupingBy(s -> s.getFilterName().getValue()));

        Set<FilterDescriptor> filterDescriptors = new HashSet<>();

        // TODO: Check whether position counts from definition or mapping of filters
        int position = 0;
        for (FilterType filter : webApp.getFilters()) {
            filterDescriptors.add(new FilterDescriptor(servletContext, filter, collect.getOrDefault(filter.getFilterName().getValue(), Collections.emptyList()), ++position));
        }

        return filterDescriptors;
    }

    private void validate() {
        int count = servletDescriptors.stream().
                filter(ServletDescriptor::isDefaultServlet).
                collect(counting()).
                intValue();

        switch (count) {
            case 1:
                // Default Servlet is already set
                break;
            case 0:
                // No default Servlet. Add a new one with the server ClassLoader
                Class<FileServlet> fileServlet = eu.ggam.servlet.impl.rootwebapp.FileServlet.class;
                servletDescriptors.add(new ServletDescriptor(servletContext, fileServlet.getSimpleName(), fileServlet));
                break;
            default:
                // More than one default Servlet
                throw new WebXmlProcessingException("Multiple Servlets mapped as default");
        }
    }

    public Set<ServletDescriptor> getServletDescriptors() {
        return servletDescriptors;
    }

    public Set<FilterDescriptor> getFilterDescriptors() {
        return filterDescriptors;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

}
