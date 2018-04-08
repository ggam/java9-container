package eu.ggam.container.integrationtests;

import static eu.ggam.container.integrationtests.Constants.BASE_URL;
import javax.ws.rs.client.ClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Guillermo González de Agüero
 */
@Deprecated
public class ServletMatchTest {

    @Test
    public void prefix() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/pattern/not-existing-servlet").
                request().
                get(String.class);

        Assertions.assertEquals("prefix match", get);
    }

    @Test
    public void prefix_sufixNotCalled() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/pattern/index.php").
                request().
                get(String.class);

        Assertions.assertEquals("prefix match", get);
    }

    @Test
    public void sufix() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/pattern2/index.php").
                request().
                get(String.class);

        Assertions.assertEquals("sufix match", get);
    }

    @Test
    public void exact() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/pattern/exact").
                request().
                get(String.class);

        Assertions.assertEquals("exact match", get);
    }

    @Test
    public void exact_encodingQueryString() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/pattern/exact%3Fparam=value").
                request().
                get(String.class);

        Assertions.assertEquals("exact match", get);
    }

    @Test
    public void defaultServlet() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/non-existing-url-default-executes").
                request().
                get(String.class);

        Assertions.assertEquals("default Servlet", get);
    }

    @Test
    public void fileServlet() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/file.html").
                request().
                get(String.class);

        Assertions.assertEquals("<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>HTML file</title>\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <div>This is an HTML file!</div>\n"
                + "    </body>\n"
                + "</html>\n"
                + "", get);
    }

}
