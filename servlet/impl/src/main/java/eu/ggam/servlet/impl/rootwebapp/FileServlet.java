package eu.ggam.servlet.impl.rootwebapp;

import eu.ggam.servlet.impl.jsr154.ServletContextImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author guillermo
 */
public class FileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Path path = createPath(getServletContext(), request.getPathInfo());
        if (Files.exists(path)) {
            Files.newInputStream(path).
                    transferTo(response.getOutputStream());

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            response.setContentType("text/html");
        }
    }

    public static boolean fileExists(ServletContext servletContext, String pathInfo) {
        return Files.exists(createPath(servletContext, pathInfo));
    }
    
    private static Path createPath(ServletContext servletContext, String pathInfo) {
        String deploymentPath = servletContext.getInitParameter(ServletContextImpl.InitParams.WEBAPP_PATH);
        return Paths.get(deploymentPath, pathInfo);
    }
}
