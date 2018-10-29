package eu.ggam.container.impl.servletcontainer.descriptor.metamodel;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ListenerMetamodel {

    public static final String TAG_NAME = "listener";

    private static final String LISTENER_CLASS = "listener-class";

    private final String listenerClass;

    public ListenerMetamodel(XMLStreamReader reader) throws XMLStreamException {
        String listenerClassTmp = null;

        outer_loop:
        while (reader.hasNext()) {
            switch (reader.next()) {
                case XMLEvent.START_ELEMENT:
                    if (LISTENER_CLASS.equals(reader.getLocalName())) {
                        listenerClassTmp = reader.getElementText();
                    }
                    break;
                case XMLEvent.END_ELEMENT:
                    if (TAG_NAME.equals(reader.getLocalName())) {
                        break outer_loop;
                    }
                    break;
            }
        }
        this.listenerClass = listenerClassTmp;
    }

    public String getListenerClass() {
        return listenerClass;
    }

}
