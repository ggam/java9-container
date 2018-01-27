package es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor;

import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.ParamValueType;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.ServletMappingType;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.ServletType;
import es.guillermogonzalezdeaguero.servlet.impl.com.sun.java.xml.ns.javaee.UrlPatternType;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.EffectiveWebXml;
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

    private final Set<String> exactPatterns = new HashSet<>();
    private final Set<String> prefixPatterns = new HashSet<>();
    private final Set<String> extensionPatterns = new HashSet<>();

    private final Map<String, String> initParams = new HashMap<>();

    private final boolean defaultServlet;

    /**
     * Specific constructor for default Servlet via {@link EffectiveWebXml}
     *
     * @param servletName
     * @param servletClass
     */
    public ServletDescriptor(String servletName, Class<? extends Servlet> servletClass) {
        this.servletName = servletName;
        this.servletClass = servletClass;
        this.defaultServlet = true;
    }

    public ServletDescriptor(ServletType servletType, List<ServletMappingType> mappings, ClassLoader classLoader) throws ClassNotFoundException {
        this.servletName = servletType.getServletName().getValue();
        this.servletClass = (Class<Servlet>) Class.forName(servletType.getServletClass().getValue(), true, classLoader);

        for (ParamValueType initParamTypes : servletType.getInitParams()) {
            initParams.put(initParamTypes.getParamName().getValue(), initParamTypes.getParamValue().getValue());
        }

        boolean isDefault = false;

        for (ServletMappingType servletMapping : mappings) {
            for (UrlPatternType urlPattern : servletMapping.getUrlPatterns()) {
                String pattern = urlPattern.getValue();

                if ("/".equals(pattern)) {
                    isDefault = true;
                    continue;
                }

                /* Validation */
                if (pattern.startsWith("*") && pattern.endsWith("*")) {
                    throw new IllegalArgumentException("Invalid URL starting and ending with *");
                }

                if (pattern.startsWith("/") && pattern.contains("*") && !pattern.endsWith("*")) {
                    throw new IllegalArgumentException("Invalid URL starting with / and ending with *");
                }

                /* Categorization */
                // Exact match
                if (!pattern.contains("*")) {
                    exactPatterns.add(pattern);
                } else {
                    // Prefix
                    if (pattern.startsWith("/")) {
                        prefixPatterns.add(pattern);
                        break;
                    } else {
                        // Extension
                        if (pattern.contains("*")) {
                            extensionPatterns.add(pattern);
                        }
                    }
                }
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

    public Set<String> getExactPatterns() {
        return new HashSet<>(exactPatterns);
    }

    public Set<String> getPrefixPatterns() {
        return new HashSet<>(prefixPatterns);
    }

    public Set<String> getExtensionPatterns() {
        return new HashSet<>(extensionPatterns);
    }

    @Override
    public ServletContext getServletContext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
