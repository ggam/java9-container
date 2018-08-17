package eu.ggam.container.impl.servletcontainer.test.servletcontext.attributelistener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

/**
 *
 * @author Guillermo González de Agüero
 */
public class SCAttributeListener implements ServletContextAttributeListener {

    @Override
    public void attributeAdded(ServletContextAttributeEvent scab) {
        Attribute attribute = (Attribute) scab.getValue();
        attribute.addValue("Added");
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent scab) {
        Attribute attribute = (Attribute) scab.getValue();
        attribute.addValue("Removed");
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent scab) {
        Attribute attribute = (Attribute) scab.getValue();
        attribute.addValue("Replaced");
    }

}
