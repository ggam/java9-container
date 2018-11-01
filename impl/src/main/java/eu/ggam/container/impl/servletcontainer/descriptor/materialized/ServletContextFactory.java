package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import eu.ggam.container.impl.servletcontainer.jsr154.ServletContextImpl;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServletContextFactory implements Supplier<ServletContext> {

    private final Map<String, String> contextParams;
    private final List<Class<? extends ServletContextAttributeListener>> attributeListeners;
    private final List<Class<? extends ServletContextListener>> contextListeners;

    public ServletContextFactory(Map<String, String> contextParams, List<Class<? extends ServletContextListener>> contextListeners, List<Class<? extends ServletContextAttributeListener>> attributeListeners, ClassLoader classLoader) {
        this.contextParams = new HashMap<>(contextParams);
        this.contextListeners = new ArrayList<>(contextListeners);
        this.attributeListeners = new ArrayList<>(attributeListeners);
    }

    @Override
    public ServletContext get() {
        return new ServletContextImpl(contextParams, instantiateAttributeListeners(attributeListeners));
    }

    private List<ServletContextAttributeListener> instantiateAttributeListeners(List<Class<? extends ServletContextAttributeListener>> listenerClasses) {
        List<ServletContextAttributeListener> listeners = new ArrayList<>();
        for (Class<? extends ServletContextAttributeListener> listenerClass : listenerClasses) {
            try {
                listeners.add(listenerClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                throw new IllegalArgumentException("Could not instantiate listener: " + listenerClass.getName(), ex);
            }
        }

        return listeners;
    }
}
