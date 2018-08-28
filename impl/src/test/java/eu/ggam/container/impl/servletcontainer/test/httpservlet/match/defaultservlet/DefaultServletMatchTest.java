package eu.ggam.container.impl.servletcontainer.test.httpservlet.match.defaultservlet;

/**
 *
 * @author Guillermo González de Agüero
 */
public class DefaultServletMatchTest {
/*
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
    }*/
}
