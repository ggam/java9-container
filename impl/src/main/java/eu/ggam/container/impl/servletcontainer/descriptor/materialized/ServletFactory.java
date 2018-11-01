package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.InitParamMetamodel;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.ServletMetamodel;
import eu.ggam.container.impl.servletcontainer.jsr154.ServletConfigImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.toMap;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServletFactory {

    private final ServletContext servletContext;

    private final Map<String, UninitializedServlet> uninitializedServlets = new HashMap<>();
    private final Map<String, Object> uninitializedServletLocks = new HashMap<>();

    private final Map<String, Servlet> initializedServlets = new HashMap<>();

    public ServletFactory(ServletContext servletContext, Set<ServletMetamodel> servletMetamodels, ClassLoader classLoader, UninitializedServlet defaultServlet) {
        this.servletContext = servletContext;

        for (ServletMetamodel servletMetamodel : servletMetamodels) {
            Class<?> forName;
            try {
                forName = Class.forName(servletMetamodel.getServletClass(), false, classLoader);
            } catch (ClassNotFoundException | SecurityException | IllegalArgumentException ex) {
                throw new IllegalArgumentException("Could not create class for " + servletMetamodel.getServletClass(), ex);
            }

            if (!Servlet.class.isAssignableFrom(forName)) {
                throw new IllegalArgumentException(servletMetamodel.getServletClass() + " is not a Servlet");
            }

            uninitializedServlets.put(servletMetamodel.getServletName(), new UninitializedServlet(servletMetamodel, (Class<? extends Servlet>) forName));
            uninitializedServletLocks.put(servletMetamodel.getServletName(), new Object());
        }

        // Default Servlet needs to be loaded from a default ClassLoader, hence it is simpler to get it as a separated parameter.
        // The server will only provide default Servlet when the application doesn't already contain one.
        if (defaultServlet != null) {
            uninitializedServlets.put(defaultServlet.getMetamodel().getServletName(), defaultServlet);
            uninitializedServletLocks.put(defaultServlet.getMetamodel().getServletName(), new Object());
        }
    }

    public Servlet getServlet(String name) throws ServletException {
        Servlet servletInstance = initializedServlets.get(name);
        if (servletInstance != null) {
            return servletInstance;
        }

        // Servlet is still not initialized
        // We'll need to initialize it if it's not already being done by another Servlet
        Object servletLock = uninitializedServletLocks.get(name);

        synchronized (servletLock) {
            UninitializedServlet uninitializedServlet = null;
            try {
                // Check if the Servlet has already been initialized
                Servlet servlet = initializedServlets.get(name);
                if (servlet != null) {
                    // Another request has initialized the Servlet for us
                    return servlet;
                }

                uninitializedServlet = uninitializedServlets.get(name);

                Servlet newInstance = uninitializedServlet.getServletClass().getDeclaredConstructor().newInstance();

                Map<String, String> contextParams = uninitializedServlet.getMetamodel().getInitParams().
                        stream().
                        collect(toMap(InitParamMetamodel::getParamName, InitParamMetamodel::getParamValue));

                newInstance.init(new ServletConfigImpl(servletContext, name, contextParams));

                initializedServlets.put(name, newInstance);
                uninitializedServletLocks.remove(name);

                return newInstance;
            } catch (Throwable t) {
                // Restore the value so other request will get another chance to initialize it
                uninitializedServlets.put(name, uninitializedServlet);
                uninitializedServletLocks.put(name, servletLock);

                if (t instanceof Error) {
                    throw (Error) t; // Don't wrap possible VM errors
                }

                throw new RuntimeException(t);
            }
        }
    }

    public void destroyAll() {
        initializedServlets.values().forEach(s -> s.destroy());
    }
}
