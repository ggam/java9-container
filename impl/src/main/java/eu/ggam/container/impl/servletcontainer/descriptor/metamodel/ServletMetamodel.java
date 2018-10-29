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
public class ServletMetamodel {

    public static final String TAG_NAME = "servlet";

    private static final String SERVLET_NAME = "servlet-name";
    private static final String SERVLET_CLASS = "servlet-class";

    private final String servletName;
    private final String servletClass;
    private final Set<InitParamMetamodel> initParams;

    public ServletMetamodel(XMLStreamReader reader) throws XMLStreamException {
        String servletNameTmp = null;
        String servletClassTmp = null;
        Set<InitParamMetamodel> initParamsTmp = new HashSet<>();

        outer_loop: while (reader.hasNext()) {
            switch (reader.next()) {
                case XMLEvent.START_ELEMENT:
                    switch (reader.getLocalName()) {
                        case InitParamMetamodel.TAG_NAME:
                            initParamsTmp.add(new InitParamMetamodel(reader));
                            break;
                        case SERVLET_NAME:
                            servletNameTmp = reader.getElementText();
                            break;
                        case SERVLET_CLASS:
                            servletClassTmp = reader.getElementText();
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
        this.servletClass = servletClassTmp;
        this.initParams = Collections.unmodifiableSet(initParamsTmp);
    }

    public String getServletName() {
        return servletName;
    }

    public String getServletClass() {
        return servletClass;
    }

    public Set<InitParamMetamodel> getInitParams() {
        return initParams;
    }
}
