package eu.ggam.container.integrationtests;

import static eu.ggam.container.integrationtests.Constants.BASE_URL;
import javax.ws.rs.client.ClientBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author guillermo
 */
public class HttpServletRequestTest {

    @Test
    public void servletPath_pathInfo_Lawn() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/lawn/index.html").
                request().
                get(String.class);

        assertEquals("servletPath: /lawn\n"
                + "pathInfo: /index.html", get);
    }

    @Test
    public void servletPath_pathInfo_Garden() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/garden/implements/").
                request().
                get(String.class);

        assertEquals("servletPath: /garden\n"
                + "pathInfo: /implements/", get);
    }

    @Test
    public void servletPath_pathInfo_Jsp() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/help/feedback.jsp").
                request().
                get(String.class);

        assertEquals("servletPath: /help/feedback.jsp\n"
                + "pathInfo: null", get);
    }

    @Test
    public void servletPath_pathInfo_Exact() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/pathInfo_exact").
                request().
                get(String.class);

        assertEquals("servletPath: /pathInfo_exact\n"
                + "pathInfo: null", get);
    }
}
