package eu.ggam.container.impl.servletcontainer.test.servletcontext.attributelistener;

import eu.ggam.container.impl.servletcontainer.descriptor.WebXmlProcessingException;
import eu.ggam.container.impl.servletcontainer.descriptor.materialized.MaterializedWebApp;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.WebXml;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import javax.servlet.ServletContext;
import javax.xml.stream.XMLStreamException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServletContext_ContextAttributeListenerTest {

    private ServletContext sc;

    @BeforeEach
    public void init() throws URISyntaxException {
        try (InputStream is = getClass().getResourceAsStream("/servletcontext.contextattributelistener/WEB-INF/web.xml");
                BufferedInputStream bis = new BufferedInputStream(is)) {

            WebXml webXml = new WebXml(bis);

            MaterializedWebApp webApp = new MaterializedWebApp(webXml, getClass().getModule());

            sc = webApp.getServletContext();
        } catch (IOException | XMLStreamException e) {
            throw new WebXmlProcessingException(e);
        }
    }

    @Test
    public void setAttribute() {
        sc.setAttribute("set_attribute", new Attribute("new"));
        sc.getAttribute("set_attribute");

        assertEquals(new Attribute("newAdded"), sc.getAttribute("set_attribute"));
    }

    @Test
    public void removeAttribute() {
        Attribute original = new Attribute("new");
        sc.setAttribute("remove_attribute", original); // newAdded

        assertEquals(new Attribute("newAdded"), sc.getAttribute("remove_attribute"));

        sc.removeAttribute("remove_attribute"); // newAddedRemoved

        assertEquals(new Attribute("newAddedRemoved"), original);
        assertEquals(null, sc.getAttribute("remove_attribute"));
    }

    @Test
    public void replaceAttribute() {
        Attribute original = new Attribute("new");
        sc.setAttribute("replace_attribute", original); // newAdded

        sc.setAttribute("replace_attribute", new Attribute("replacement")); // newAddedReplaced

        assertEquals(new Attribute("newAddedReplaced"), original);
        assertEquals(new Attribute("replacement"), sc.getAttribute("replace_attribute"));
    }
}
