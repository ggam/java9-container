package eu.ggam.container.impl.servletcontainer.jsr154;

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

    private final String servletName;
    private final Map<String, String> initParams;
    private final Enumeration parameterNames;

    public ServletConfigImpl(ServletContext servletContext, String servletName, Map<String, String> initParams) {
        this.servletContext = servletContext;
        this.servletName = servletName;
        this.initParams = new HashMap<>(initParams);
        this.parameterNames = Collections.enumeration(initParams.keySet());
    }

    @Override
    public String getServletName() {
        return servletName;
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

}
