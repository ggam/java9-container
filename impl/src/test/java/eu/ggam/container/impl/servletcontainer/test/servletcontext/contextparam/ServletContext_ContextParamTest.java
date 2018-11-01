package eu.ggam.container.impl.servletcontainer.test.servletcontext.contextparam;

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
public class ServletContext_ContextParamTest {

    private ServletContext sc;

    @BeforeEach
    public void init() throws URISyntaxException {
        try (InputStream is = getClass().getResourceAsStream("/servletcontext.contextparam/WEB-INF/web.xml");
                BufferedInputStream bis = new BufferedInputStream(is)) {

            WebXml webXml = new WebXml(bis);

            MaterializedWebApp webApp = new MaterializedWebApp(webXml, getClass().getModule());

            sc = webApp.getServletContext();
        } catch (IOException | XMLStreamException e) {
            throw new WebXmlProcessingException(e);
        }
    }

    @Test
    public void contextParam() {
        assertEquals("It works!", sc.getInitParameter("test_context_param"));
    }
}
