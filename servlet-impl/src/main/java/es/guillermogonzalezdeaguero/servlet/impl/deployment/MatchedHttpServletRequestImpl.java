package es.guillermogonzalezdeaguero.servlet.impl.deployment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 *
 * @author guillermo
 */
public class MatchedHttpServletRequestImpl extends HttpServletRequestWrapper {

    private final String contextPath;

    public MatchedHttpServletRequestImpl(String contextPath, HttpServletRequest request) {
        super(request);
        this.contextPath = contextPath;
    }

    @Override
    public String getContextPath() {
        return contextPath;
    }

}
