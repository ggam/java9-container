package eu.ggam.container.impl.servletcontainer.jsr154;

import eu.ggam.container.impl.servletcontainer.descriptor.FilterDescriptor;
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
    private final FilterDescriptor filterDescriptor;
    private final Map<String, String> initParams;
    private final Enumeration parameterNames;

    public FilterConfigImpl(ServletContext servletContext, FilterDescriptor filterDescriptor) {
        this.servletContext = servletContext;
        this.filterDescriptor = filterDescriptor;
        this.initParams = new HashMap<>(filterDescriptor.getInitParams());
        this.parameterNames = Collections.enumeration(initParams.keySet());
    }

    @Override
    public String getFilterName() {
        return filterDescriptor.getFilterName();
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

    public FilterDescriptor getFilterDescriptor() {
        return filterDescriptor;
    }

}
