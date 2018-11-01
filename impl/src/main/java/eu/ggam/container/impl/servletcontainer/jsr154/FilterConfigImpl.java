package eu.ggam.container.impl.servletcontainer.jsr154;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

/**
 *
 * @author Guillermo González de Agüero
 */
public class FilterConfigImpl implements FilterConfig {

    private final ServletContext servletContext;

    private final String filterName;
    private final Map<String, String> initParams;
    private final Enumeration parameterNames;

    public FilterConfigImpl(ServletContext servletContext, String filterName, Map<String, String> initParams) {
        this.servletContext = servletContext;
        this.filterName = filterName;
        this.initParams = new HashMap<>(initParams);
        this.parameterNames = Collections.enumeration(initParams.keySet());
    }

    @Override
    public String getFilterName() {
        return filterName;
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
