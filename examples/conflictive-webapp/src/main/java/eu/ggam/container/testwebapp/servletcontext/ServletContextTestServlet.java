package eu.ggam.container.testwebapp.servletcontext;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author guillermo
 */
public class ServletContextTestServlet extends HttpServlet {

    private static final String ATTR_TEST_SET_ATTRIBUTE = ServletContextTestServlet.class.getName() + "attribute1";
    private static final String ATTR_TEST_REMOVE_ATTRIBUTE = ServletContextTestServlet.class.getName() + "attribute2";
    private static final String ATTR_TEST_REPLACE_ATTRIBUTE = ServletContextTestServlet.class.getName() + "attribute3";

    public static final List<String> ATTRIBUTES = Arrays.asList(ATTR_TEST_SET_ATTRIBUTE, ATTR_TEST_REMOVE_ATTRIBUTE, ATTR_TEST_REPLACE_ATTRIBUTE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        String test = req.getParameter("test");

        ATTRIBUTES.forEach(sc::removeAttribute);

        switch (test) {
            case "context_param":
                String value = sc.getInitParameter("test_context_param");
                resp.getWriter().print("Init: " + value);
                break;
            case "set_attribute":
                sc.setAttribute(ATTR_TEST_SET_ATTRIBUTE, new Attribute("new"));
                resp.getWriter().print("AttributeValue: " + sc.getAttribute(ATTR_TEST_SET_ATTRIBUTE));
                break;
            case "remove_attribute": {
                Attribute original = new Attribute("new");
                sc.setAttribute(ATTR_TEST_REMOVE_ATTRIBUTE, original); // newAdded

                resp.getWriter().print("AttributeValue: " + sc.getAttribute(ATTR_TEST_REMOVE_ATTRIBUTE));
                sc.removeAttribute(ATTR_TEST_REMOVE_ATTRIBUTE); // newAddedRemoved

                resp.getWriter().print(" - OldValue: " + original); // null
                resp.getWriter().print(" - AttributeValue: " + sc.getAttribute(ATTR_TEST_REMOVE_ATTRIBUTE)); // null
                break;
            }
            case "replace_attribute":
                Attribute original = new Attribute("new");
                sc.setAttribute(ATTR_TEST_REPLACE_ATTRIBUTE, original); // newAdded

                sc.setAttribute(ATTR_TEST_REPLACE_ATTRIBUTE, new Attribute("replacement")); // newAdded

                resp.getWriter().print("OriginalValue: " + original); // newAddedReplaced
                resp.getWriter().print(" - CurrentValue: " + sc.getAttribute(ATTR_TEST_REPLACE_ATTRIBUTE)); // replacement
                break;
        }
    }

}
