package eu.ggam.servlet.impl.descriptor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.servlet.Servlet;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServletDescriptor {

    private static final MatchingPattern DEFAULT_SERVLET_PATTERN = MatchingPattern.createUrlPattern("/");

    public static final class Builder {

        private final String servletName;
        private final String className;
        private ClassLoader classLoader;
        private final Set<MatchingPattern> patterns;

        private final Map<String, String> initParams = new HashMap<>();

        public Builder(String servletName, String className, ClassLoader classLoader) {
            this.servletName = servletName;
            this.className = className;
            this.classLoader = classLoader;

            this.patterns = new HashSet<>();
        }

        public Builder addMapping(MatchingPattern pattern) {
            this.patterns.add(pattern);
            return this;
        }

        public Builder addMappings(Set<MatchingPattern> patterns) {
            this.patterns.addAll(patterns);
            return this;
        }

        public Builder addInitParams(Map<String, String> initParams) {
            this.initParams.putAll(initParams);
            return this;
        }

        public Builder defaultServlet() {
            patterns.add(DEFAULT_SERVLET_PATTERN);

            return this;
        }

        public ServletDescriptor build() throws ClassNotFoundException {
            ServletDescriptor servletDescriptor = new ServletDescriptor(servletName, className, patterns, initParams, classLoader);

            classLoader = null; // Remove references to classLoader

            return servletDescriptor;
        }
    }

    private final String servletName;
    private final Class<? extends Servlet> servletClass;

    private final Set<MatchingPattern> urlPatterns;
    private final Map<String, String> initParams;

    private final boolean defaultServlet;

    private ServletDescriptor(String servletName, String className, Set<MatchingPattern> patterns, Map<String, String> initParams, ClassLoader classLoader) throws ClassNotFoundException {
        this.servletName = servletName;
        this.servletClass = (Class<Servlet>) Class.forName(className, true, classLoader);
        this.initParams = new HashMap<>(initParams);

        Optional<MatchingPattern> defaultMapping = patterns.stream().
                filter(p -> p.getMatchType() == MatchingPattern.MatchType.EXACT && p.matchesUri("/")).
                findAny();

        if (defaultMapping.isPresent()) {
            patterns.remove(defaultMapping.get());
            this.defaultServlet = true;
        } else {
            this.defaultServlet = false;
        }

        this.urlPatterns = new HashSet<>(patterns);
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
