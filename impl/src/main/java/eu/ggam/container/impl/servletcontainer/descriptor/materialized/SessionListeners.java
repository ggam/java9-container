package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import eu.ggam.container.impl.servletcontainer.descriptor.WebXmlProcessingException;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.ListenerMetamodel;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingListener;

/**
 *
 * @author Guillermo González de Agüero
 */
public class SessionListeners {

    private final List<HttpSessionActivationListener> sessionActivationListeners;
    private final List<HttpSessionAttributeListener> sessionAttributeListeners;
    private final List<HttpSessionBindingListener> sessionBindingListeners;

    SessionListeners(Set<ListenerMetamodel> listenerTypes, ClassLoader classLoader) {
        final List<HttpSessionActivationListener> sessionActivationListenersTemp = new ArrayList<>();
        final List<HttpSessionAttributeListener> sessionAttributeListenersTemp = new ArrayList<>();
        final List<HttpSessionBindingListener> sessionBindingListenersTemp = new ArrayList<>();

        for (ListenerMetamodel listenerType : listenerTypes) {
            String className = listenerType.getListenerClass();
            try {
                Class<?> forName = Class.forName(listenerType.getListenerClass(), false, classLoader);

                if (HttpSessionActivationListener.class.isAssignableFrom(forName)) {
                    sessionActivationListenersTemp.add((HttpSessionActivationListener) forName.getDeclaredConstructor().newInstance());
                } else if (HttpSessionAttributeListener.class.isAssignableFrom(forName)) {
                    sessionAttributeListenersTemp.add((HttpSessionAttributeListener) forName.getDeclaredConstructor().newInstance());
                } else if (HttpSessionBindingListener.class.isAssignableFrom(forName)) {
                    sessionBindingListenersTemp.add((HttpSessionBindingListener) forName.getDeclaredConstructor().newInstance());
                }
            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new WebXmlProcessingException("Could not create listener for class " + className, ex);
            }
        }

        this.sessionActivationListeners = Collections.unmodifiableList(sessionActivationListenersTemp);
        this.sessionAttributeListeners = Collections.unmodifiableList(sessionAttributeListenersTemp);
        this.sessionBindingListeners = Collections.unmodifiableList(sessionBindingListenersTemp);
    }

    public List<HttpSessionActivationListener> getSessionActivationListeners() {
        return sessionActivationListeners;
    }

    public List<HttpSessionAttributeListener> getSessionAttributeListeners() {
        return sessionAttributeListeners;
    }

    public List<HttpSessionBindingListener> getSessionBindingListeners() {
        return sessionBindingListeners;
    }

}
