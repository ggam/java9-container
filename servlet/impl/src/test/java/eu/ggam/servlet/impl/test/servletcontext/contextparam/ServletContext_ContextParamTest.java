package eu.ggam.servlet.impl.test.servletcontext.contextparam;

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
 * @author Guillermo González de Agüero
 */
public class ServletContext_ContextParamTest {

    private ServletContext sc;

    @BeforeEach
    public void init() throws URISyntaxException {
        Path get = Paths.get(getClass().getResource("/servletcontext.contextparam").toURI());

        MaterializedWebApp webApp = new MaterializedWebApp.Builder(get, getClass().getClassLoader()).defaultServlet(DummyFileServlet.class.getName()).
                build();

        sc = new ServletContextImpl(webApp);
    }

    @Test
    public void contextParam() {
        assertEquals("It works!", sc.getInitParameter("test_context_param"));
    }
}
