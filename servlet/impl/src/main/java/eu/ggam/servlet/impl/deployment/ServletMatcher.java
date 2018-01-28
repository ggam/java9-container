package eu.ggam.servlet.impl.deployment;

import eu.ggam.servlet.impl.deployment.webxml.descriptor.ServletDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

/**
 *
 * @author guillermo
 */
public class ServletMatcher {

    private static final Logger LOGGER = Logger.getLogger(ServletMatcher.class.getName());

    private final Set<ServletDescriptor> servletDescriptors;
    private final ServletDescriptor defaultServlet;

    private final ConcurrentHashMap<String, Servlet> servletInstances;

    public ServletMatcher(Set<ServletDescriptor> servletDescriptors) {
        this.servletDescriptors = new HashSet<>(servletDescriptors);
        this.servletInstances = new ConcurrentHashMap<>();

        // There will always be one and only one default Servlet at this point of time
        defaultServlet = servletDescriptors.stream().
                filter(ServletDescriptor::isDefaultServlet).
                findAny().
                get();
    }

    public Servlet match(String pathInfo) throws ServletException {
        // TODO: pathInfor == null?

        // Look for exact patterns
        for (ServletDescriptor servletDescriptor : servletDescriptors) {
            for (String exactPattern : servletDescriptor.getExactPatterns()) {
                if (exactPattern.equals(pathInfo) || (pathInfo == null && "/".equals(exactPattern))) {
                    LOGGER.log(Level.INFO, "Requrest to {0} will be processed by Servlet {1}", new Object[]{pathInfo, servletDescriptor.getServletName()});

                    return getServletInstance(servletDescriptor);
                }
            }
        }

        // Check prefix patterns
        for (ServletDescriptor servletDescriptor : servletDescriptors) {
            for (String prefixPattern : servletDescriptor.getPrefixPatterns()) {
                String prefix = prefixPattern.substring(0, prefixPattern.length() - 1);
                if (pathInfo != null && pathInfo.startsWith(prefix)) {
                    LOGGER.log(Level.INFO, "Requrest to {0} will be processed by Servlet {1}", new Object[]{pathInfo, servletDescriptor.getServletName()});

                    return getServletInstance(servletDescriptor);
                }
            }
        }

        // Finally, verify extension patterns
        for (ServletDescriptor servletDescriptor : servletDescriptors) {
            for (String extensionPattern : servletDescriptor.getExtensionPatterns()) {
                String extension = extensionPattern.split("\\.")[1];
                if (pathInfo != null && pathInfo.endsWith(extension)) {
                    LOGGER.log(Level.INFO, "Requrest to {0} will be processed by Servlet {1}", new Object[]{pathInfo, servletDescriptor.getServletName()});

                    return getServletInstance(servletDescriptor);
                }
            }

        }

        LOGGER.log(Level.INFO, "Requrest to {0} will be processed by default Servlet, {1}", new Object[]{pathInfo, defaultServlet.getServletName()});
        return getServletInstance(defaultServlet);
    }

    private Servlet getServletInstance(ServletDescriptor descriptor) throws ServletException {
        try {
            return servletInstances.computeIfAbsent(descriptor.getServletName(), (String s) -> {
                try {
                    Servlet newInstance = descriptor.getServletClass().getDeclaredConstructor().newInstance();

                    newInstance.init(descriptor);

                    return newInstance;
                } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ServletException ex) {
                    throw new RuntimeException(ex);
                }
            });
        } catch (RuntimeException e) {
            if (e.getCause() != null && e.getCause() instanceof ServletException) {
                throw (ServletException) e.getCause();
            }
            throw e;
        }
    }
}
