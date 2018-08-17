package eu.ggam.container.exampleplugin;

import eu.ggam.container.DuplicatedClass;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ExampleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class<?> forName = Class.forName("eu.ggam.servlet.impl.deployer.DeploymentRegistry");
            resp.getWriter().print(forName.getDeclaredConstructors()[0].newInstance());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException | InvocationTargetException ex) {
            throw new ServletException(ex);
        }

        resp.getWriter().print(new DuplicatedClass().getExampleMessage());
    }
}
