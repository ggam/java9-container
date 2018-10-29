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
public class ServletMappingMetamodel {

    public static final String TAG_NAME = "servlet-mapping";

    private static final String SERVLET_NAME = "servlet-name";
    private static final String URL_PATTERN = "url-pattern";

    private final String servletName;
    private final Set<String> urlPatterns;

    public ServletMappingMetamodel(XMLStreamReader reader) throws XMLStreamException {
        String servletNameTmp = null;
        Set<String> urlPatternsTmp = new HashSet<>();

        outer_loop:
        while (reader.hasNext()) {
            switch (reader.next()) {
                case XMLEvent.START_ELEMENT:
                    switch (reader.getLocalName()) {
                        case SERVLET_NAME:
                            servletNameTmp = reader.getElementText();
                            break;
                        case URL_PATTERN:
                            urlPatternsTmp.add(reader.getElementText());
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

        this.servletName = servletNameTmp;
        this.urlPatterns = Collections.unmodifiableSet(urlPatternsTmp);
    }

    public String getServletName() {
        return servletName;
    }

    public Set<String> getUrlPatterns() {
        return urlPatterns;
    }

}
