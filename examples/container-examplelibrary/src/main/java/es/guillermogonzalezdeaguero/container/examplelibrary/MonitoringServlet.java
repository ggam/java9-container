package eu.ggam.container.examplelibrary;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author guillermo
 */
public class MonitoringServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MonitoringServlet.class.getName());
    
    @Override
    public void init() throws ServletException {
        LOGGER.log(Level.INFO, "Initializing {0}", getServletConfig().getServletName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("Session count: <unavailable>\n");
        resp.getWriter().println("Request count: <unavailable>\n");
    }

}
