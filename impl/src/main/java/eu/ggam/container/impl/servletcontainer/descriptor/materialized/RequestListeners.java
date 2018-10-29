package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import eu.ggam.container.impl.servletcontainer.descriptor.WebXmlProcessingException;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.ListenerMetamodel;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;

/**
 *
 * @author Guillermo González de Agüero
 */
public class RequestListeners {

    private final List<ServletRequestAttributeListener> requestAttributeListeners;
    private final List<ServletRequestListener> requestListeners;

    RequestListeners(Set<ListenerMetamodel> listenerTypes, ClassLoader classLoader) {
        final List<ServletRequestAttributeListener> requestAttributeListenersTemp = new ArrayList<>();
        final List<ServletRequestListener> requestListenersTemp = new ArrayList<>();

        for (ListenerMetamodel listenerType : listenerTypes) {
            String className = listenerType.getListenerClass();
            try {
                Class<?> forName = Class.forName(listenerType.getListenerClass(), false, classLoader);

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
