package eu.ggam.servlet.impl.deployment.webxml.descriptor;

import eu.ggam.servlet.impl.ServletContextImpl;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ParamValueType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ServletMappingType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ServletType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.UrlPatternType;
import eu.ggam.servlet.impl.deployment.webxml.EffectiveWebXml;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 *
 * @author guillermo
 */
public class ServletDescriptor implements ServletConfig {

    private final String servletName;
    private final Class<? extends Servlet> servletClass;

    private final Set<MatchingPattern> urlPatterns = new HashSet<>();

    private final Map<String, String> initParams = new HashMap<>();

    private final boolean defaultServlet;

    private final ServletContextImpl servletContext;

    /**
     * Specific constructor for default Servlet via {@link EffectiveWebXml}
     *
     * @param servletContext
     * @param servletName
     * @param servletClass
     */
    public ServletDescriptor(ServletContextImpl servletContext, String servletName, Class<? extends Servlet> servletClass) {
        this.servletName = servletName;
        this.servletClass = servletClass;
        this.defaultServlet = true;
        this.servletContext = servletContext;
    }

    public ServletDescriptor(ServletContextImpl servletContext, ServletType servletType, List<ServletMappingType> mappings) throws ClassNotFoundException {
        this.servletName = servletType.getServletName().getValue();
        this.servletClass = (Class<Servlet>) Class.forName(servletType.getServletClass().getValue(), true, servletContext.getWarClassLoader());
        this.servletContext = servletContext;

        for (ParamValueType initParamTypes : servletType.getInitParams()) {
            initParams.put(initParamTypes.getParamName().getValue(), initParamTypes.getParamValue().getValue());
        }

        boolean isDefault = false;

        // TODO: categorize using RegEx to also validate correctness
        for (ServletMappingType servletMapping : mappings) {
            for (UrlPatternType urlPattern : servletMapping.getUrlPatterns()) {
                String pattern = urlPattern.getValue();

                if ("/".equals(pattern)) {
                    isDefault = true;
                    continue;
                }

                urlPatterns.add(MatchingPattern.createUrlPattern(pattern));
            }
        }

        defaultServlet = isDefault;
    }

    @Override
    public String getServletName() {
        return servletName;
    }

    public Class<? extends Servlet> getServletClass() {
        return servletClass;
    }

    public Set<MatchingPattern> getUrlPatterns() {
        return new HashSet<>(urlPatterns);
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
        return Collections.enumeration(initParams.keySet());
    }

    public boolean isDefaultServlet() {
        return defaultServlet;
    }

}
