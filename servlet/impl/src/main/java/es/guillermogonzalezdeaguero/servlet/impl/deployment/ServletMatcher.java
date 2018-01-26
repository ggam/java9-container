package es.guillermogonzalezdeaguero.servlet.impl.deployment;

import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.ServletDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author guillermo
 */
public class ServletMatcher {

    private final Set<ServletDescriptor> servletDescriptors;

    public ServletMatcher(Set<ServletDescriptor> servletDescriptors) {
        this.servletDescriptors = new HashSet<>(servletDescriptors);
    }

    public ServletDescriptor match(String pathInfo) {
        for (ServletDescriptor servletDescriptor : servletDescriptors) {
            for (String exactPattern : servletDescriptor.getExactPatterns()) {
                if (exactPattern.equals(pathInfo) || (pathInfo == null && "/".equals(exactPattern))) {
                    return servletDescriptor;
                }
            }

            for (String prefixPattern : servletDescriptor.getPrefixPatterns()) {
                String prefix = prefixPattern.substring(0, prefixPattern.length() - 1);
                if (pathInfo != null && pathInfo.startsWith(prefix)) {
                    return servletDescriptor;
                }
            }

            if (pathInfo == null) {
                return null; // TODO: Default Servlet?
            }

            for (String extensionPattern : servletDescriptor.getExtensionPatterns()) {
                String extension = extensionPattern.split(".")[1];
                if (pathInfo.endsWith(extension)) {
                    return servletDescriptor;
                }
            }

        }
        return null;
    }
}
