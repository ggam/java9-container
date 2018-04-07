package eu.ggam.servlet.impl.core.matcher;

import eu.ggam.servlet.impl.descriptor.MatchingPattern;
import eu.ggam.servlet.impl.descriptor.MatchingPattern.MatchType;
import eu.ggam.servlet.impl.descriptor.ServletDescriptor;
import eu.ggam.servlet.impl.jsr154.ServletConfigImpl;
import eu.ggam.servlet.impl.rootwebapp.FileServlet;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServletMatcher {

    private static final Logger LOGGER = Logger.getLogger(ServletMatcher.class.getName());
    private final ServletContext servletContext;

    private final Set<ServletDescriptor> servletDescriptors;
    private final ServletDescriptor defaultServlet;
    private final ServletDescriptor fileServlet;

    private final ConcurrentHashMap<String, Servlet> servletInstances;

    private static final EnumSet<MatchType> MATCHING_TYPES = EnumSet.of(MatchType.EXACT, MatchType.EXTENSION, MatchType.PREFIX);

    public ServletMatcher(ServletContext servletContext, Set<ServletDescriptor> servletDescriptors) {
        this.servletContext = servletContext;
        this.servletDescriptors = new HashSet<>(servletDescriptors);
        this.servletInstances = new ConcurrentHashMap<>();

        // There will always be one and only one default Servlet at this point of time
        defaultServlet = servletDescriptors.stream().
                filter(ServletDescriptor::isDefaultServlet).
                findAny().
                get();

        fileServlet = servletDescriptors.stream().
                filter(sd -> sd.getServletClass().equals(FileServlet.class)).
                findAny().
                get();
    }

    public ServletMatch match(String uri) throws ServletException {
        for (MatchType matchType : MATCHING_TYPES) {
            for (ServletDescriptor servletDescriptor : servletDescriptors) {
                Optional<MatchingPattern> findAny = servletDescriptor.getUrlPatterns().
                        stream().
                        filter(p -> p.getMatchType() == matchType).
                        filter(p -> p.matchesUri(uri)).
                        findAny();

                if (findAny.isPresent()) {
                    Optional<MatchingPattern.UriMatch> uriMatch = findAny.get().uriMatch(uri);

                    LOGGER.log(Level.FINE, "Request to {0} will be processed by Servlet {1} ({2} match)", new Object[]{uri, servletDescriptor.getServletName(), matchType});

                    return new ServletMatch(getServletInstance(servletDescriptor), uriMatch.get());
                }
            }
        }

        if (!defaultServlet.equals(fileServlet) && FileServlet.fileExists(servletContext, uri)) {
            // FileServlet is not the default Servlet, but a file exists. The file should be sent
            LOGGER.log(Level.FINE, "Request to {0} will be processed by " + FileServlet.class.getSimpleName(), new Object[]{uri});
            return new ServletMatch(getServletInstance(fileServlet), new MatchingPattern.UriMatch("", uri));
        }

        LOGGER.log(Level.FINE, "Request to {0} will be processed by default Servlet, {1}", new Object[]{uri, defaultServlet.getServletName()});
        return new ServletMatch(getServletInstance(defaultServlet), new MatchingPattern.UriMatch("", uri));
    }

    private Servlet getServletInstance(ServletDescriptor descriptor) throws ServletException {
        try {
            return servletInstances.computeIfAbsent(descriptor.getServletName(), (String s) -> {
                try {
                    Servlet newInstance = descriptor.getServletClass().getDeclaredConstructor().newInstance();

                    newInstance.init(new ServletConfigImpl(servletContext, descriptor));

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

    public void destroyAll() {
        servletInstances.values().forEach(s -> s.destroy());
    }
}
