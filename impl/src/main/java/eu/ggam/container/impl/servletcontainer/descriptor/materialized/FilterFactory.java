package eu.ggam.container.impl.servletcontainer.descriptor.materialized;

import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.FilterMetamodel;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.InitParamMetamodel;
import eu.ggam.container.impl.servletcontainer.jsr154.FilterConfigImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.toMap;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *
 * @author Guillermo González de Agüero
 */
public class FilterFactory {

    private final ServletContext servletContext;
    
    private Map<String, UninitializedFilter> uninitializedFilters = new HashMap<>();
    private Map<String, Object> uninitializedFilterLocks = new HashMap<>();

    private Map<String, Filter> initializedFilters = new HashMap<>();

    public FilterFactory(ServletContext servletContext, Set<FilterMetamodel> filterMetamodels, ClassLoader classLoader) {
        this.servletContext = servletContext;
        
        for (FilterMetamodel filterMetamodel : filterMetamodels) {
            Class<?> forName;
            try {
                forName = Class.forName(filterMetamodel.getFilterClass(), false, classLoader);
            } catch (ClassNotFoundException | SecurityException | IllegalArgumentException ex) {
                throw new IllegalArgumentException("Could not create class for " + filterMetamodel.getFilterClass(), ex);
            }

            if (!Filter.class.isAssignableFrom(forName)) {
                throw new IllegalArgumentException(filterMetamodel.getFilterClass() + " is not a Filter");
            }

            uninitializedFilters.put(filterMetamodel.getFilterName(), new UninitializedFilter(filterMetamodel, (Class<? extends Filter>) forName));
            uninitializedFilterLocks.put(filterMetamodel.getFilterName(), new Object());
        }
    }

    public Filter getFilter(String name) throws ServletException {
        Filter servletInstance = initializedFilters.get(name);
        if (servletInstance != null) {
            return servletInstance;
        }

        // Servlet is still not initialized
        // We'll need to initialize it if it's not already being done by another Servlet
        Object filterLock = uninitializedFilterLocks.get(name);

        synchronized (filterLock) {
            UninitializedFilter uninitializedFilter = null;
            try {
                // Check if the Servlet has already been initialized
                Filter filter = initializedFilters.get(name);
                if (filter != null) {
                    // Another request has initialized the Servlet for us
                    return filter;
                }

                uninitializedFilter = uninitializedFilters.get(name);

                Filter newInstance = uninitializedFilter.getFilterClass().getDeclaredConstructor().newInstance();

                Map<String, String> contextParams = uninitializedFilter.getMetamodel().getInitParams().
                        stream().
                        collect(toMap(InitParamMetamodel::getParamName, InitParamMetamodel::getParamValue));

                newInstance.init(new FilterConfigImpl(servletContext, name, contextParams));

                initializedFilters.put(name, newInstance);
                uninitializedFilterLocks.remove(name);

                return newInstance;
            } catch (Throwable t) {
                // Restore the value so other request will get another chance to initialize it
                uninitializedFilters.put(name, uninitializedFilter);
                uninitializedFilterLocks.put(name, filterLock);

                if (t instanceof Error) {
                    throw (Error) t; // Don't wrap possible VM errors
                }

                throw new RuntimeException(t);
            }
        }
    }

    public void destroyAll() {
        initializedFilters.values().forEach(s -> s.destroy());
    }
}
