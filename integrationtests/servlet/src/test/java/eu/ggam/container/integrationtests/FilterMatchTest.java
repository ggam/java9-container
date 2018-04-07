package eu.ggam.container.integrationtests;

import static eu.ggam.container.integrationtests.Constants.BASE_URL;
import javax.ws.rs.client.ClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Guillermo González de Agüero
 */
public class FilterMatchTest {

    @Test
    public void prefix() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/filter_match/prefix/*").
                request().
                get(String.class);

        Assertions.assertEquals("prefix Filter", get);
    }

    @Test
    public void sufix() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/special_url_filtered/index.filtered").
                request().
                get(String.class);

        Assertions.assertEquals("sufix Filter", get);
    }

    @Test
    public void exact() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/filter_match/exact").
                request().
                get(String.class);

        Assertions.assertEquals("exact Filter", get);
    }

    @Test
    public void namedServlet() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/special-named-servlet").
                request().
                get(String.class);

        Assertions.assertEquals("named Servlet Filter", get);
    }

    @Test
    public void filterOrder() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/filters/ordered-filter").
                request().
                get(String.class);

        Assertions.assertEquals("filter 1filter 2", get);
    }
}
