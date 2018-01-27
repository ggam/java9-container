package es.guillermogonzalezdeaguero.servlet.impl.deployment;

import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.ServletDescriptor;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guillermo
 */
public class ServletMatcher {

    private static final Logger LOGGER = Logger.getLogger(ServletMatcher.class.getName());
    
    private final Set<ServletDescriptor> servletDescriptors;

    public ServletMatcher(Set<ServletDescriptor> servletDescriptors) {
        this.servletDescriptors = new HashSet<>(servletDescriptors);
    }

    public ServletDescriptor match(String pathInfo) {
        // TODO: pathInfor == null?

        // Look for exact patterns
        for (ServletDescriptor servletDescriptor : servletDescriptors) {
            for (String exactPattern : servletDescriptor.getExactPatterns()) {
                if (exactPattern.equals(pathInfo) || (pathInfo == null && "/".equals(exactPattern))) {
                    LOGGER.log(Level.INFO, "{0} Servlet will process the request", servletDescriptor.getServletName());
                    
                    return servletDescriptor;
                }
            }
        }

        // Check prefix patterns
        for (ServletDescriptor servletDescriptor : servletDescriptors) {
            for (String prefixPattern : servletDescriptor.getPrefixPatterns()) {
                String prefix = prefixPattern.substring(0, prefixPattern.length() - 1);
                if (pathInfo != null && pathInfo.startsWith(prefix)) {
                    LOGGER.log(Level.INFO, "{0} Servlet will process the request", servletDescriptor.getServletName());
                    
                    return servletDescriptor;
                }
            }
        }

        // Finally, verify extension patterns
        for (ServletDescriptor servletDescriptor : servletDescriptors) {
            for (String extensionPattern : servletDescriptor.getExtensionPatterns()) {
                String extension = extensionPattern.split(".")[1];
                if (pathInfo != null && pathInfo.endsWith(extension)) {
                    LOGGER.log(Level.INFO, "{0} Servlet will process the request", servletDescriptor.getServletName());
                    
                    return servletDescriptor;
                }
            }

        }

        throw new RuntimeException("No Servlet (not even FileServet) found to process the request");
    }
}
