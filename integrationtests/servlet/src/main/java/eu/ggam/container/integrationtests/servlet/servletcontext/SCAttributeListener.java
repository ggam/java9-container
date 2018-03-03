package eu.ggam.container.integrationtests.servlet.servletcontext;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

/**
 *
 * @author guillermo
 */
public class SCAttributeListener implements ServletContextAttributeListener {

    @Override
    public void attributeAdded(ServletContextAttributeEvent scab) {
        if (ServletContextTestServlet.ATTRIBUTES.contains(scab.getName())) {
            Attribute attribute = (Attribute) scab.getValue();
            attribute.addValue("Added");
        }
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent scab) {
        if (ServletContextTestServlet.ATTRIBUTES.contains(scab.getName())) {
            Attribute attribute = (Attribute) scab.getValue();
            attribute.addValue("Removed");
        }
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent scab) {
        if (ServletContextTestServlet.ATTRIBUTES.contains(scab.getName())) {
            Attribute attribute = (Attribute) scab.getValue();
            attribute.addValue("Replaced");
        }
    }

}
