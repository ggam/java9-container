package eu.ggam.servlet.impl.test.servletcontext.attributelistener;

import eu.ggam.servlet.impl.descriptor.materialized.MaterializedWebApp;
import eu.ggam.servlet.impl.jsr154.ServletContextImpl;
import eu.ggam.servlet.impl.test.DummyFileServlet;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author guillermo
 */
public class ServletContext_ContextAttributeListenerTest {

    private ServletContext sc;

    @BeforeEach
    public void init() throws URISyntaxException {
        Path get = Paths.get(getClass().getResource("/servletcontext.contextattributelistener").toURI());

        MaterializedWebApp webApp = new MaterializedWebApp.Builder(get, getClass().getClassLoader(), "test1").
                fileServlet("dummy", DummyFileServlet.class.getName()).
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
