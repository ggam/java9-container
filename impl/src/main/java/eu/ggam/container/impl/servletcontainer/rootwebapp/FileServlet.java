package eu.ggam.container.impl.servletcontainer.rootwebapp;

import eu.ggam.container.impl.servletcontainer.jsr154.ServletContextImpl;
import java.io.IOException;
import java.io.InputStream;
import java.lang.module.ModuleReader;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Guillermo González de Agüero
 */
public class FileServlet extends HttpServlet {

    private static final String BASE_PATH = "_ROOT_";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (ModuleReader reader = createReader()) {
            Optional<InputStream> optionalStream = reader.open(BASE_PATH + request.getPathInfo());
            reader.list().collect(toList());
            if (optionalStream.isPresent()) {
                try (InputStream is = optionalStream.get()) {
                    is.transferTo(response.getOutputStream());

                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("text/html");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
                response.setContentType("text/html");
            }
        }
    }

    private ModuleReader createReader() throws IOException {
        String webAppModule = getServletContext().getInitParameter(ServletContextImpl.InitParams.WEBAPP_MODULE);
        return getClass().
                getModule().
                getLayer().
                configuration().
                findModule(webAppModule).
                get().
                reference().
                open();
    }
}
