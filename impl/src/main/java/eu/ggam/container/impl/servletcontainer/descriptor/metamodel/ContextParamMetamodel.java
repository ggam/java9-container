package eu.ggam.container.impl.servletcontainer.descriptor.metamodel;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ContextParamMetamodel {

    public static final String TAG_NAME = "context-param";

    private static final String PARAM_NAME = "param-name";
    private static final String PARAM_VALUE = "param-value";

    private final String paramName;
    private final String paramValue;

    public ContextParamMetamodel(XMLStreamReader reader) throws XMLStreamException {
        String paramNameTmp = null;
        String paramValueTmp = null;

        outer_loop:
        while (reader.hasNext()) {
            switch (reader.next()) {
                case XMLEvent.START_ELEMENT:
                    switch (reader.getLocalName()) {
                        case ContextParamMetamodel.PARAM_NAME:
                            paramNameTmp = reader.getElementText();
                            break;
                        case ContextParamMetamodel.PARAM_VALUE:
                            paramNameTmp = reader.getElementText();
                            break;
                    }
                case XMLEvent.END_ELEMENT:
                    if (TAG_NAME.equals(reader.getLocalName())) {
                        break outer_loop;
                    }
                    break;
            }
        }

        this.paramName = paramNameTmp;
        this.paramValue = paramValueTmp;
    }

    public String getParamName() {
        return paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

}
