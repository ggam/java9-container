package es.guillermogonzalezdeaguero.container.integrationtests;

import static es.guillermogonzalezdeaguero.container.integrationtests.Constants.BASE_URL;
import java.io.IOException;
import javax.ws.rs.client.ClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author guillermo
 */
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
    public void defaultServlet() throws IOException {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/non-existing-url-default-executes").
                request().
                get(String.class);

        Assertions.assertEquals("default Servlet", get);
    }

}
