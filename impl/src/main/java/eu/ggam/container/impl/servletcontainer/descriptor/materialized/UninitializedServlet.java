package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.ServletMetamodel;
import javax.servlet.Servlet;

/**
 *
 * @author Guillermo González de Agüero
 */
public class UninitializedServlet {

    private final ServletMetamodel metamodel;
    private final Class<? extends Servlet> servletClass;

    public UninitializedServlet(ServletMetamodel metamodel, Class<? extends Servlet> servletClass) {
        this.metamodel = metamodel;
        this.servletClass = servletClass;
    }

    public ServletMetamodel getMetamodel() {
        return metamodel;
    }

    public Class<? extends Servlet> getServletClass() {
        return servletClass;
    }

}
