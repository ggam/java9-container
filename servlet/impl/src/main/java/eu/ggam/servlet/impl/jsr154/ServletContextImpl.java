package eu.ggam.servlet.impl.jsr154;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletException;

/**
 *
 * @author guillermo
 */
public class ServletContextImpl implements ServletContext {

    private static final Logger LOGGER = Logger.getLogger(ServletContextImpl.class.getName());

    public static class InitParams {

        private static final String BASE = ServletContextImpl.class.getPackageName();

        public static final String WEBAPP_PATH = BASE + "webapp_path";
    }

    private final ClassLoader warClassLoader;
    private final String contextPath;
    private final Map<String, String> initParams;
    private final List<ServletContextAttributeListener> attributeListeners;

    private final Map<String, Object> attributes;
    private final Object attributeLock = new Object(); // Object to hold the lock when accessing the attributes

    public ServletContextImpl(ClassLoader warClassLoader, String contextPath, List<ServletContextAttributeListener> attributeListeners, Map<String, String> initParams) {
        this.warClassLoader = warClassLoader;
        this.contextPath = contextPath;
        this.attributeListeners = Collections.unmodifiableList(attributeListeners);
        this.initParams = Collections.unmodifiableMap(new HashMap<>(initParams));

        this.attributes = new HashMap<>();
    }
    
    @Override
    public String getContextPath() {
        return contextPath;
    }

    @Override
    public ServletContext getContext(String uripath) {
        return null;
    }

    @Override
    public int getMajorVersion() {
        return 2;
    }

    @Override
    public int getMinorVersion() {
        return 5;
    }

    @Override
    public String getMimeType(String file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set getResourcePaths(String path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public URL getResource(String path) throws MalformedURLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InputStream getResourceAsStream(String path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RequestDispatcher getNamedDispatcher(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Servlet getServlet(String name) throws ServletException {
        return null;
    }

    @Override
    public Enumeration getServlets() {
        return Collections.emptyEnumeration();
    }

    @Override
    public Enumeration getServletNames() {
        return Collections.emptyEnumeration();
    }

    @Override
    public void log(String msg) {
        LOGGER.log(Level.INFO, msg);
    }

    @Override
    public void log(Exception exception, String msg) {
        LOGGER.log(Level.SEVERE, msg, exception);
    }

    @Override
    public void log(String message, Throwable throwable) {
        LOGGER.log(Level.SEVERE, message, throwable);
    }

    @Override
    public String getRealPath(String path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getServerInfo() {
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

    @Override
    public Object getAttribute(String name) {
        synchronized (attributeLock) {
            return attributes.get(name);
        }
    }

    @Override
    public Enumeration getAttributeNames() {
        synchronized (attributeLock) {
            return Collections.enumeration(attributes.keySet());
        }
    }

    @Override
    public void setAttribute(String name, Object newValue) {
        synchronized (attributeLock) {
            if (newValue == null) {
                removeAttribute(name);
            } else {
                Object oldValue = getAttribute(name);
                ServletContextAttributeEvent event;

                if (oldValue == null) {
                    // Adding
                    event = new ServletContextAttributeEvent(this, name, newValue);
                } else {
                    // Replacing
                    event = new ServletContextAttributeEvent(this, name, oldValue);
                }

                attributes.put(name, newValue);

                for (ServletContextAttributeListener listener : attributeListeners) {
                    if (oldValue == null) {
                        listener.attributeAdded(event);
                    } else {
                        listener.attributeReplaced(event);
                    }
                }
            }
        }
    }

    @Override
    public void removeAttribute(String name) {
        synchronized (attributeLock) {
            Object value = getAttribute(name);
            if (value == null) {
                return;
            }

            attributes.remove(name);

            ServletContextAttributeEvent event = new ServletContextAttributeEvent(this, name, value);

            for (ServletContextAttributeListener listener : attributeListeners) {
                listener.attributeRemoved(event);
            }
        }

    }

    @Override
    public String getServletContextName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ClassLoader getWarClassLoader() {
        return warClassLoader;
    }

}
