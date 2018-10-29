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
public class FilterMetamodel {

    public static final String TAG_NAME = "filter";

    private static final String FILTER_NAME = "filter-name";
    private static final String FILTER_CLASS = "filter-class";

    private final String filterName;
    private final String filterClass;
    private final Set<InitParamMetamodel> initParams;

    public FilterMetamodel(XMLStreamReader reader) throws XMLStreamException {
        String filterNameTmp = null;
        String filterClassTmp = null;
        Set<InitParamMetamodel> initParamsTmp = new HashSet<>();

        outer_loop:
        while (reader.hasNext()) {
            switch (reader.next()) {
                case XMLEvent.START_ELEMENT:
                    switch (reader.getLocalName()) {
                        case InitParamMetamodel.TAG_NAME:
                            initParamsTmp.add(new InitParamMetamodel(reader));
                            break;
                        case FILTER_NAME:
                            filterNameTmp = reader.getElementText();
                            break;
                        case FILTER_CLASS:
                            filterClassTmp = reader.getElementText();
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
        this.filterClass = filterClassTmp;
        this.initParams = Collections.unmodifiableSet(initParamsTmp);
    }

    public String getFilterName() {
        return filterName;
    }

    public String getFilterClass() {
        return filterClass;
    }

    public Set<InitParamMetamodel> getInitParams() {
        return initParams;
    }
}
