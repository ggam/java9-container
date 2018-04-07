package eu.ggam.servlet.impl.descriptor;

import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ParamValueType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ServletMappingType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ServletType;
import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.UrlPatternType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.Servlet;

/**
 *
 * @author guillermo
 */
public class ServletDescriptor {

    private final String servletName;
    private final Class<? extends Servlet> servletClass;

    private final Set<MatchingPattern> urlPatterns = new HashSet<>();

    private final Map<String, String> initParams = new HashMap<>();

    private final boolean defaultServlet;

    private ServletDescriptor(String servletName, Class<? extends Servlet> servletClass, boolean defaultServlet) {
        this.servletName = servletName;
        this.servletClass = servletClass;
        this.defaultServlet = defaultServlet;
    }

    private ServletDescriptor(ServletType servletType, List<ServletMappingType> mappings, ClassLoader classLoader) throws ClassNotFoundException {
        this.servletName = servletType.getServletName().getValue();
        this.servletClass = (Class<Servlet>) Class.forName(servletType.getServletClass().getValue(), true, classLoader);

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

    public static ServletDescriptor createDefault(String servletName, Class<? extends Servlet> servletClass) {
        return new ServletDescriptor(servletName, servletClass, true);
    }

    public static ServletDescriptor createWithoutMappings(String servletName, Class<? extends Servlet> servletClass) {
        return new ServletDescriptor(servletName, servletClass, false);
    }

    public static ServletDescriptor createFromWebXml(ServletType servletType, List<ServletMappingType> mappings, ClassLoader classLoader) throws ClassNotFoundException {
        return new ServletDescriptor(servletType, mappings, classLoader);
    }

    public String getServletName() {
        return servletName;
    }

    public Class<? extends Servlet> getServletClass() {
        return servletClass;
    }

    public Set<MatchingPattern> getUrlPatterns() {
        return new HashSet<>(urlPatterns);
    }

    public Map<String, String> getInitParams() {
        return new HashMap<>(initParams);
    }

    public boolean isDefaultServlet() {
        return defaultServlet;
    }

}
