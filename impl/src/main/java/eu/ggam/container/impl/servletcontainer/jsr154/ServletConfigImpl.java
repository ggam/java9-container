package eu.ggam.container.impl.servletcontainer.jsr154;

import eu.ggam.container.impl.servletcontainer.descriptor.ServletDescriptor;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServletConfigImpl implements ServletConfig {

    private final ServletContext servletContext;
    private final ServletDescriptor servletDescriptor;
    private final Map<String, String> initParams;
    private final Enumeration parameterNames;

    public ServletConfigImpl(ServletContext servletContext, ServletDescriptor servletDescriptor) {
        this.servletContext = servletContext;
        this.servletDescriptor = servletDescriptor;
        this.initParams = new HashMap<>(servletDescriptor.getInitParams());
        this.parameterNames = Collections.enumeration(initParams.keySet());
    }

    @Override
    public String getServletName() {
        return servletDescriptor.getServletName();
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public String getInitParameter(String name) {
        return initParams.get(name);
    }

    @Override
    public Enumeration getInitParameterNames() {
        return parameterNames;
    }

    public ServletDescriptor getServletDescriptor() {
        return servletDescriptor;
    }

}
