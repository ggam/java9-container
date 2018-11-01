package eu.ggam.container.impl.servletcontainer.test.httpservlet.match;

import eu.ggam.container.impl.servletcontainer.descriptor.WebXmlProcessingException;
import eu.ggam.container.impl.servletcontainer.descriptor.materialized.MaterializedWebApp;
import eu.ggam.container.impl.servletcontainer.descriptor.materialized.RequestUriMatch;
import eu.ggam.container.impl.servletcontainer.descriptor.materialized.UrlPatternsParser;
import eu.ggam.container.impl.servletcontainer.descriptor.metamodel.WebXml;
import eu.ggam.container.impl.servletcontainer.test.httpservlet.match.servlets.DefaultServlet;
import eu.ggam.container.impl.servletcontainer.test.httpservlet.match.servlets.ExactPatternServlet;
import eu.ggam.container.impl.servletcontainer.test.httpservlet.match.servlets.PrefixPatternServlet;
import eu.ggam.container.impl.servletcontainer.test.httpservlet.match.servlets.SufixPatternServlet;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import javax.servlet.ServletException;
import javax.xml.stream.XMLStreamException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Guillermo González de Agüero
 */
public class UrlMatchTest {

    private UrlPatternsParser urlPatternsParser;

    @BeforeEach
    public void init() throws URISyntaxException {
        try (InputStream is = getClass().getResourceAsStream("/httpservlet.match/WEB-INF/web.xml");
                BufferedInputStream bis = new BufferedInputStream(is)) {

            WebXml webXml = new WebXml(bis);

            MaterializedWebApp webApp = new MaterializedWebApp(webXml, getClass().getModule());

            urlPatternsParser = webApp.getUrlPatternsParser();
        } catch (IOException | XMLStreamException e) {
            throw new WebXmlProcessingException(e);
        }
    }

    @Test
    public void prefix() throws ServletException {
        RequestUriMatch match = urlPatternsParser.match("/pattern/not-existing-servlet");

        assertEquals(PrefixPatternServlet.class.getSimpleName(), match.getServletName());
    }

    @Test
    public void prefix_sufixNotCalled() throws ServletException {
        RequestUriMatch match = urlPatternsParser.match("/pattern/index.php");

        assertEquals(PrefixPatternServlet.class.getSimpleName(), match.getServletName());
    }

    @Test
    public void sufix() throws ServletException {
        RequestUriMatch match = urlPatternsParser.match("/pattern2/index.php");

        assertEquals(SufixPatternServlet.class.getSimpleName(), match.getServletName());
    }

    @Test
    public void exact() throws ServletException {
        RequestUriMatch match = urlPatternsParser.match("/pattern/exact");

        assertEquals(ExactPatternServlet.class.getSimpleName(), match.getServletName());
    }

    @Test
    public void exact_encodingQueryString() throws ServletException {
        RequestUriMatch match = urlPatternsParser.match("/pattern/exact%3Fparam=value");

        assertEquals(ExactPatternServlet.class.getSimpleName(), match.getServletName());
    }

    @Test
    public void defaultServlet() throws ServletException {
        RequestUriMatch match = urlPatternsParser.match("/non-existing-url-default-executes");
        assertEquals(DefaultServlet.class.getSimpleName(), match.getServletName());
    }

    @Disabled
    @Test
    public void exactMatchNotAllowsPrefix() throws ServletException {
        // TODO: requires fix of regular expression to verify the path is really equal
    }

}
