package eu.ggam.servlet.impl.descriptor.materialized;

import eu.ggam.servlet.impl.com.sun.java.xml.ns.javaee.ListenerType;
import eu.ggam.servlet.impl.descriptor.WebXmlProcessingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;

/**
 *
 * @author Guillermo González de Agüero
 */
public class RequestListeners {

    private final List<ServletRequestAttributeListener> requestAttributeListeners;
    private final List<ServletRequestListener> requestListeners;

    RequestListeners(List<ListenerType> listenerTypes, ClassLoader classLoader) {
        final List<ServletRequestAttributeListener> requestAttributeListenersTemp = new ArrayList<>();
        final List<ServletRequestListener> requestListenersTemp = new ArrayList<>();

        for (ListenerType listenerType : listenerTypes) {
            String className = listenerType.getListenerClass().getValue();
            try {
                Class<?> forName = Class.forName(listenerType.getListenerClass().getValue(), false, classLoader);

                if (ServletRequestAttributeListener.class.isAssignableFrom(forName)) {
                    requestAttributeListenersTemp.add((ServletRequestAttributeListener) forName.getDeclaredConstructor().newInstance());
                } else if (ServletRequestListener.class.isAssignableFrom(forName)) {
                    requestListenersTemp.add((ServletRequestListener) forName.getDeclaredConstructor().newInstance());
                }
            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new WebXmlProcessingException("Could not create listener for class " + className, ex);
            }
        }

        requestAttributeListeners = Collections.unmodifiableList(requestAttributeListenersTemp);
        requestListeners = Collections.unmodifiableList(requestListenersTemp);
    }

    public List<ServletRequestAttributeListener> getRequestAttributeListeners() {
        return requestAttributeListeners;
    }

    public List<ServletRequestListener> getRequestListeners() {
        return requestListeners;
    }

}
