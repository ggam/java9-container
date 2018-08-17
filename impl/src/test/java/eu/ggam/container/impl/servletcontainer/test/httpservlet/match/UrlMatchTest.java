package eu.ggam.container.impl.servletcontainer.test.httpservlet.match;

import eu.ggam.container.impl.servletcontainer.core.matcher.ServletMatch;
import eu.ggam.container.impl.servletcontainer.core.matcher.ServletMatcher;
import eu.ggam.container.impl.servletcontainer.descriptor.materialized.MaterializedWebApp;
import eu.ggam.container.impl.servletcontainer.jsr154.ServletContextImpl;
import eu.ggam.container.impl.servletcontainer.test.DummyFileServlet;
import eu.ggam.container.impl.servletcontainer.test.httpservlet.match.servlets.DefaultServlet;
import eu.ggam.container.impl.servletcontainer.test.httpservlet.match.servlets.ExactPatternServlet;
import eu.ggam.container.impl.servletcontainer.test.httpservlet.match.servlets.PrefixPatternServlet;
import eu.ggam.container.impl.servletcontainer.test.httpservlet.match.servlets.SufixPatternServlet;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Guillermo González de Agüero
 */
public class UrlMatchTest {

    private ServletMatcher servletMatcher;
    
    @BeforeEach
    public void init() throws URISyntaxException {
        Path get = Paths.get(getClass().getResource("/httpservlet.match").toURI());

        MaterializedWebApp webApp = new MaterializedWebApp.Builder(get, getClass().getClassLoader()).
                defaultServlet(DummyFileServlet.class.getName()).
                build();

        ServletContext sc = new ServletContextImpl(webApp);
        servletMatcher = new ServletMatcher(sc, webApp.getServletDescriptors());
    }

    @Test
    public void prefix() throws ServletException {
        ServletMatch match = servletMatcher.match("/pattern/not-existing-servlet");

        assertEquals(PrefixPatternServlet.class, match.getServlet().getClass());
    }

    @Test
    public void prefix_sufixNotCalled() throws ServletException {
        ServletMatch match = servletMatcher.match("/pattern/index.php");

        assertEquals(PrefixPatternServlet.class, match.getServlet().getClass());
    }

    @Test
    public void sufix() throws ServletException {
        ServletMatch match = servletMatcher.match("/pattern2/index.php");

        assertEquals(SufixPatternServlet.class, match.getServlet().getClass());
    }

    @Test
    public void exact() throws ServletException {
        ServletMatch match = servletMatcher.match("/pattern/exact");

        assertEquals(ExactPatternServlet.class, match.getServlet().getClass());
    }

    @Test
    public void exact_encodingQueryString() throws ServletException {
        ServletMatch match = servletMatcher.match("/pattern/exact%3Fparam=value");

        assertEquals(ExactPatternServlet.class, match.getServlet().getClass());
    }

    @Test
    public void defaultServlet() throws ServletException {
        ServletMatch match = servletMatcher.match("/non-existing-url-default-executes");
        
        assertEquals(DefaultServlet.class, match.getServlet().getClass());
    }

}
