package eu.ggam.container.impl.servletcontainer.test.httpservlet.match.defaultservlet;

import eu.ggam.container.impl.servletcontainer.core.matcher.ServletMatch;
import eu.ggam.container.impl.servletcontainer.core.matcher.ServletMatcher;
import eu.ggam.container.impl.servletcontainer.descriptor.WebXmlProcessingException;
import eu.ggam.container.impl.servletcontainer.descriptor.materialized.MaterializedWebApp;
import eu.ggam.container.impl.servletcontainer.jsr154.ServletContextImpl;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Guillermo González de Agüero
 */
public class DefaultServletMatchTest {

    @Test
    public void userProvidedDefaultServlet() throws URISyntaxException, ServletException {
        Path get = Paths.get(getClass().getResource("/httpservlet.match_default/user").toURI());

        MaterializedWebApp webApp = new MaterializedWebApp.Builder(get, getClass().getClassLoader()).
                build();

        ServletMatcher servletMatcher = new ServletMatcher(new ServletContextImpl(webApp), webApp.getServletDescriptors());

        ServletMatch match = servletMatcher.match("/non-existing-url-default-executes");
        assertEquals(DefaultServlet_User.class, match.getServlet().getClass());
    }

    @Test
    public void creationTimeDefaultServlet() throws URISyntaxException, ServletException {
        Path get = Paths.get(getClass().getResource("/httpservlet.match_default/creation").toURI());

        MaterializedWebApp webApp = new MaterializedWebApp.Builder(get, getClass().getClassLoader()).
                defaultServlet(DefaultServlet_Creation.class.getName()).
                build();

        ServletMatcher servletMatcher = new ServletMatcher(new ServletContextImpl(webApp), webApp.getServletDescriptors());

        ServletMatch match = servletMatcher.match("/non-existing-url-default-executes");
        assertEquals(DefaultServlet_Creation.class, match.getServlet().getClass());
    }

    @Test
    public void withDefaultServlet_Exception() throws URISyntaxException, ServletException {
        Path get = Paths.get(getClass().getResource("/httpservlet.match_default/creation").toURI());

        assertThrows(WebXmlProcessingException.class, () -> {
            MaterializedWebApp webApp = new MaterializedWebApp.Builder(get, getClass().getClassLoader()).
                    build();
        });
    }
}
