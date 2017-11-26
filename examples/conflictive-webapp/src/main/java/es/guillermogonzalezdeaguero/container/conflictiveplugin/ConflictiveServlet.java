package es.guillermogonzalezdeaguero.container.conflictiveplugin;

import es.guillermogonzalezdeaguero.container.DuplicatedClass;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author guillermo
 */
public class ConflictiveServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print(new DuplicatedClass().getConflictiveMessage());
    }
    /*
    @Override
    public String getUrl() {
        return "/conflictive.html";
    }

    @Override
    public String process() {
        return new DuplicatedClass().getConflictiveMessage();
    }*/

}
