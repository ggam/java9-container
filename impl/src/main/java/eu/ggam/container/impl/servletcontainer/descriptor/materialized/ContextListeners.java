package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import eu.ggam.container.impl.servletcontainer.descriptor.WebXmlProcessingException;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.ListenerMetamodel;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ContextListeners {

    private final List<ServletContextAttributeListener> contextAttributeListeners;
    private final List<ServletContextListener> contextListeners;

    public ContextListeners(Set<ListenerMetamodel> listenerTypes, ClassLoader classLoader) {

        final List<ServletContextAttributeListener> contextAttributeListenersTemp = new ArrayList<>();
        final List<ServletContextListener> contextListenersTemp = new ArrayList<>();

        for (ListenerMetamodel listenerType : listenerTypes) {
            String className = listenerType.getListenerClass();
            try {
                Class<?> forName = Class.forName(listenerType.getListenerClass(), false, classLoader);

                if (ServletContextAttributeListener.class.isAssignableFrom(forName)) {
                    contextAttributeListenersTemp.add((ServletContextAttributeListener) forName.getDeclaredConstructor().newInstance());
                } else if (ServletContextListener.class.isAssignableFrom(forName)) {
                    contextListenersTemp.add((ServletContextListener) forName.getDeclaredConstructor().newInstance());
                }
            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new WebXmlProcessingException("Could not create listener for class " + className, ex);
            }
        }

        contextAttributeListeners = Collections.unmodifiableList(contextAttributeListenersTemp);
        contextListeners = Collections.unmodifiableList(contextListenersTemp);
    }

    public List<ServletContextAttributeListener> getContextAttributeListeners() {
        return contextAttributeListeners;
    }

    public List<ServletContextListener> getContextListeners() {
        return contextListeners;
    }

}
