package eu.ggam.container.impl.servletcontainer.test.servletcontext.contextparam;

import eu.ggam.container.impl.servletcontainer.descriptor.materialized.MaterializedWebApp;
import eu.ggam.container.impl.servletcontainer.jsr154.ServletContextImpl;
import eu.ggam.container.impl.servletcontainer.test.DummyFileServlet;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Guillermo González de Agüero
 */
@Disabled
public class ServletContext_ContextParamTest {

    private ServletContext sc;

    @BeforeEach
    public void init() throws URISyntaxException {
        Path get = Paths.get(getClass().getResource("/servletcontext.contextparam").toURI());

        MaterializedWebApp webApp = new MaterializedWebApp.Builder(getClass().getModule()).defaultServlet(DummyFileServlet.class.getName()).
                build();

        sc = new ServletContextImpl(webApp);
    }

    @Test
    public void contextParam() {
        assertEquals("It works!", sc.getInitParameter("test_context_param"));
    }
}
