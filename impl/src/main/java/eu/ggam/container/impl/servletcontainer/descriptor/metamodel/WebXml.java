package eu.ggam.container.impl.servletcontainer.descriptor.metamodel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Guillermo González de Agüero
 */
public class WebXml {

    private final Set<ServletMetamodel> servlets;
    private final Set<ServletMappingMetamodel> servletMappings;

    private final Set<FilterMetamodel> filters;
    private final List<FilterMappingMetamodel> filterMappings;

    private final Set<ContextParamMetamodel> contextParams;
    private final List<ListenerMetamodel> listeners;

    public WebXml(InputStream inputStream) throws XMLStreamException {
        XMLStreamReader reader = XMLInputFactory.newFactory().createXMLStreamReader(inputStream);

        // TODO: check ordering and uniqueness constraints
        Set<ServletMetamodel> servletsTmp = new HashSet<>();
        Set<ServletMappingMetamodel> servletMappingsTmp = new HashSet<>();
        Set<FilterMetamodel> filtersTmp = new HashSet<>();
        List<FilterMappingMetamodel> filterMappingsTmp = new ArrayList<>();
        Set<ContextParamMetamodel> contextParamsTmp = new HashSet<>();
        List<ListenerMetamodel> listenersTmp = new ArrayList<>();

        while (reader.hasNext()) {
            if (reader.next() != XMLEvent.START_ELEMENT) {
                continue;
            }

            switch (reader.getLocalName()) {
                case ServletMetamodel.TAG_NAME:
                    servletsTmp.add(new ServletMetamodel(reader));
                    break;
                case ServletMappingMetamodel.TAG_NAME:
                    servletMappingsTmp.add(new ServletMappingMetamodel(reader));
                    break;
                case FilterMetamodel.TAG_NAME:
                    filtersTmp.add(new FilterMetamodel(reader));
                    break;
                case FilterMappingMetamodel.TAG_NAME:
                    filterMappingsTmp.add(new FilterMappingMetamodel(reader));
                    break;
                case ContextParamMetamodel.TAG_NAME:
                    contextParamsTmp.add(new ContextParamMetamodel(reader));
                    break;
                case ListenerMetamodel.TAG_NAME:
                    listenersTmp.add(new ListenerMetamodel(reader));
                    break;
            }
        }

        servlets = Collections.unmodifiableSet(servletsTmp);
        servletMappings = Collections.unmodifiableSet(servletMappingsTmp);
        filters = Collections.unmodifiableSet(filtersTmp);
        filterMappings = Collections.unmodifiableList(filterMappingsTmp);
        contextParams = Collections.unmodifiableSet(contextParamsTmp);
        listeners = Collections.unmodifiableList(listenersTmp);
    }

    public Set<ServletMetamodel> getServlets() {
        return servlets;
    }

    public Set<ServletMappingMetamodel> getServletMappings() {
        return servletMappings;
    }

    public Set<FilterMetamodel> getFilters() {
        return filters;
    }

    public List<FilterMappingMetamodel> getFilterMappings() {
        return filterMappings;
    }

    public Set<ContextParamMetamodel> getContextParams() {
        return contextParams;
    }

    public List<ListenerMetamodel> getListeners() {
        return listeners;
    }

}
