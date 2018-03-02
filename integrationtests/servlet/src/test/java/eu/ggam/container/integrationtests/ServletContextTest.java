package eu.ggam.container.integrationtests;

import static eu.ggam.container.integrationtests.Constants.BASE_URL;
import javax.ws.rs.client.ClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author guillermo
 */
public class ServletContextTest {

    @Test
    public void initParam() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/servletcontext/dynamic-test.html?test=context_param").
                request().
                get(String.class);

        Assertions.assertEquals("Init: It works!", get);
    }

    @Test
    public void setAttribute() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/servletcontext/dynamic-test.html?test=set_attribute").
                request().
                get(String.class);

        Assertions.assertEquals("AttributeValue: newAdded", get);
    }

    @Test
    public void removeAttribute() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/servletcontext/dynamic-test.html?test=remove_attribute").
                request().
                get(String.class);

        Assertions.assertEquals("AttributeValue: newAdded - OldValue: newAddedRemoved - AttributeValue: null", get);
    }

    @Test
    public void replaceAttribute() {
        String get = ClientBuilder.newClient().
                target(BASE_URL).
                path("/servletcontext/dynamic-test.html?test=replace_attribute").
                request().
                get(String.class);

        Assertions.assertEquals("OriginalValue: newAddedReplaced - CurrentValue: replacement", get);
    }
}
