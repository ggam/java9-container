package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.FilterMetamodel;
import javax.servlet.Filter;

/**
 *
 * @author Guillermo González de Agüero
 */
public class UninitializedFilter {

    private final FilterMetamodel metamodel;
    private final Class<? extends Filter> filterClass;

    public UninitializedFilter(FilterMetamodel metamodel, Class<? extends Filter> filterClass) {
        this.metamodel = metamodel;
        this.filterClass = filterClass;
    }

    public FilterMetamodel getMetamodel() {
        return metamodel;
    }

    public Class<? extends Filter> getFilterClass() {
        return filterClass;
    }

}
