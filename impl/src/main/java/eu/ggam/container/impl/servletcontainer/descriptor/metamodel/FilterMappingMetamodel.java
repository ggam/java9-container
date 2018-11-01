package eu.ggam.container.impl.servletcontainer.descriptor.metamodel;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Guillermo González de Agüero
 */
public class FilterMappingMetamodel {

    public static final String TAG_NAME = "filter-mapping";

    private static final String FILTER_NAME = "filter-name";
    private static final String URL_PATTERN = "url-pattern";
    private static final String SERVLET_NAME = "servlet-name";

    private final String filterName;
    private final Set<String> urlPatterns;
    private final Set<String> servletNames;

    public FilterMappingMetamodel(XMLStreamReader reader) throws XMLStreamException {
        String filterNameTmp = null;
        Set<String> urlPatternsTmp = new HashSet<>();
        Set<String> servletNamesTmp = new HashSet<>();

        outer_loop:
        while (reader.hasNext()) {
            switch (reader.next()) {
                case XMLEvent.START_ELEMENT:
                    switch (reader.getLocalName()) {
                        case FILTER_NAME:
                            filterNameTmp = reader.getElementText();
                            break;
                        case URL_PATTERN:
                            urlPatternsTmp.add(reader.getElementText());
                            break;
                        case SERVLET_NAME:
                            servletNamesTmp.add(reader.getElementText());
                            break;
                    }
                    break;
                case XMLEvent.END_ELEMENT:
                    if (TAG_NAME.equals(reader.getLocalName())) {
                        break outer_loop;
                    }
                    break;
            }
        }

        this.filterName = filterNameTmp;
        this.urlPatterns = Collections.unmodifiableSet(urlPatternsTmp);
        this.servletNames = Collections.unmodifiableSet(servletNamesTmp);
    }

    public FilterMappingMetamodel(String filterName, Set<String> urlPatterns, Set<String> servletNames) {
        this.filterName = filterName;
        this.urlPatterns = Collections.unmodifiableSet(new HashSet<>(urlPatterns));
        this.servletNames = Collections.unmodifiableSet(new HashSet<>(servletNames));
    }

    public String getFilterName() {
        return filterName;
    }

    public Set<String> getUrlPatterns() {
        return urlPatterns;
    }

    public Set<String> getServletNames() {
        return servletNames;
    }

}
