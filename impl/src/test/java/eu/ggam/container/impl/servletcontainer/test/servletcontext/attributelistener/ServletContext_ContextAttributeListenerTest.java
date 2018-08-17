package eu.ggam.container.impl.servletcontainer.test.servletcontext.attributelistener;

import eu.ggam.container.impl.servletcontainer.descriptor.materialized.MaterializedWebApp;
import eu.ggam.container.impl.servletcontainer.jsr154.ServletContextImpl;
import eu.ggam.container.impl.servletcontainer.test.DummyFileServlet;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
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
        Path webAppPath = Paths.get(getClass().getResource("/servletcontext.contextattributelistener").toURI());

        MaterializedWebApp webApp = new MaterializedWebApp.Builder(webAppPath, getClass().getClassLoader()).defaultServlet(DummyFileServlet.class.getName()).
                build();

        sc = new ServletContextImpl(webApp);
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
