package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.ListenerMetamodel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ListenerParser {

    public static enum ListenerType {
        SERVLET_CONTEXT_LISTENER(ServletContextListener.class),
        SERVLET_CONTEXT_ATTRIBUTELISTENER(ServletContextAttributeListener.class),
        SERVLET_REQUEST_LISTENER(ServletRequestListener.class),
        SERVLET_REQUEST_ATTRIBUTE_LISTENER(ServletRequestAttributeListener.class),
        HTTP_SESSION_LISTENER(HttpSessionListener.class),
        HTTP_SESSION_ACTIVATION_LISTENER(HttpSessionActivationListener.class),
        HTTP_SESSION_ATTRIBUTE_LISTENER(HttpSessionAttributeListener.class),
        HTTP_SESSION_BINDING_LISTENER(HttpSessionBindingListener.class);

        private final Class<?> listenerClass;

        private ListenerType(Class<?> listenerClass) {
            this.listenerClass = listenerClass;
        }

        public Class<?> getListenerClass() {
            return listenerClass;
        }
    }

    private Map<ListenerType, List<Class<?>>> listenersMap = new HashMap<>();

    public ListenerParser(List<ListenerMetamodel> listeners, ClassLoader classLoader) {
        for (ListenerMetamodel listener : listeners) {
            Class<?> forName;
            try {
                forName = Class.forName(listener.getListenerClass(), false, classLoader);
            } catch (ClassNotFoundException | SecurityException | IllegalArgumentException ex) {
                throw new IllegalArgumentException("Could not create class for " + listener.getListenerClass(), ex);
            }

            boolean foundMatch = false;
            for (ListenerType listenerType : ListenerType.values()) {
                if (listenerType.getListenerClass().isAssignableFrom(forName)) {
                    listenersMap.putIfAbsent(listenerType, new ArrayList<>());
                    var putIfAbsent = listenersMap.get(listenerType);
                    putIfAbsent.add(forName);
                    foundMatch = true;
                }
            }

            if (!foundMatch) {
                throw new IllegalArgumentException("Class " + listener.getListenerClass() + " is no a listener");
            }
        }
    }

    public <T> List<Class<? extends T>> getListenersForType(ListenerType type) {
        // Ugly but still safe conversion
        return (List) Collections.unmodifiableList(listenersMap.getOrDefault(type, Collections.emptyList()));
    }
}
