package eu.ggam.servlet.impl.descriptor.materialized;

import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ListenerType;
import eu.ggam.servlet.impl.descriptor.WebXmlProcessingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextListener;

/**
 *
 * @author guillermo
 */
public class ContextListeners {

    private final List<ServletContextAttributeListener> contextAttributeListeners;
    private final List<ServletContextListener> contextListeners;

    public ContextListeners(List<ListenerType> listenerTypes, ClassLoader classLoader) {

        final List<ServletContextAttributeListener> contextAttributeListenersTemp = new ArrayList<>();
        final List<ServletContextListener> contextListenersTemp = new ArrayList<>();

        for (ListenerType listenerType : listenerTypes) {
            String className = listenerType.getListenerClass().getValue();
            try {
                Class<?> forName = Class.forName(listenerType.getListenerClass().getValue(), false, classLoader);

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
