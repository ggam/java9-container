package eu.ggam.servlet.impl.rootwebapp;

import eu.ggam.servlet.impl.jsr154.ServletContextImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author guillermo
 */
public class FileServlet extends HttpServlet {

    private String deploymentPath;

    @Override
    public void init() throws ServletException {
        deploymentPath = getServletContext().getInitParameter(ServletContextImpl.InitParams.WEBAPP_PATH);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestedFile = request.getPathInfo();

        Path resolve = Paths.get(deploymentPath, requestedFile);

        if (Files.exists(resolve)) {
            Files.newInputStream(resolve).
                    transferTo(response.getOutputStream());

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            response.setContentType("text/html");
        }
    }

}
