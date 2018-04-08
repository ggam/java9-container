package eu.ggam.servlet.impl.test.httpservlet.match;

import eu.ggam.servlet.impl.core.matcher.ServletMatch;
import eu.ggam.servlet.impl.core.matcher.ServletMatcher;
import eu.ggam.servlet.impl.descriptor.materialized.MaterializedWebApp;
import eu.ggam.servlet.impl.jsr154.ServletContextImpl;
import eu.ggam.servlet.impl.test.DummyFileServlet;
import eu.ggam.servlet.impl.test.httpservlet.match.servlets.DefaultServlet;
import eu.ggam.servlet.impl.test.httpservlet.match.servlets.ExactPatternServlet;
import eu.ggam.servlet.impl.test.httpservlet.match.servlets.PrefixPatternServlet;
import eu.ggam.servlet.impl.test.httpservlet.match.servlets.SufixPatternServlet;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
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
                fileServlet(DummyFileServlet.class.getName()).
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

    @Test
    public void fileServlet() throws ServletException {
        ServletMatch match = servletMatcher.match("/file.html");
        
        Assertions.assertEquals(DummyFileServlet.class, match.getServlet().getClass());
    }

}
