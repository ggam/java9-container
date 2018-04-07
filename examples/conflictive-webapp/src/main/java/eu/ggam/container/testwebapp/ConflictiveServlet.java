package eu.ggam.container.testwebapp;

import eu.ggam.container.DuplicatedClass;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ConflictiveServlet extends HttpServlet {

    private static final String HEADER = "<!DOCTYPE html>\n"
            + "<html>\n"
            + "<head>\n"
            + "    <title>Java 9 Container</title>\n"
            + "</head>\n"
            + "<body>\n"
            + "    <h1>It works!</h1>";
    private static final String FOOTER = "</body>\n"
            + "</html>";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print(HEADER);
        resp.getWriter().print("<p>Message from duplicated class: " + new DuplicatedClass().getConflictiveMessage() + "</p>");
        resp.getWriter().print("<p>Configured parameter: " + getServletConfig().getInitParameter("test_param") + "</p>");
        resp.getWriter().print(FOOTER);
    }

    @Override
    public void destroy() {
        System.out.println(String.format("Destroying Servlet %s!!", ConflictiveServlet.class.getName()));
    }

}
