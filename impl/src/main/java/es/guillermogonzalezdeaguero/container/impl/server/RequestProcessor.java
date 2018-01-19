package es.guillermogonzalezdeaguero.container.impl.server;

import es.guillermogonzalezdeaguero.container.impl.deployment.DeploymentRegistry;
import es.guillermogonzalezdeaguero.container.impl.servlet.FilterChainFactory;
import es.guillermogonzalezdeaguero.container.impl.servlet.HttpServletResponseImpl;
import es.guillermogonzalezdeaguero.container.impl.servlet.PreMatchingHttpServletRequestImpl;
import es.guillermogonzalezdeaguero.container.impl.servlet.ServletResponseToSocket;
import es.guillermogonzalezdeaguero.container.impl.servlet.SocketToServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;

/**
 *
 * @author guillermo
 */
public class RequestProcessor {

    private static final Logger LOGGER = Logger.getLogger(RequestProcessor.class.getName());
    
    private final DeploymentRegistry deploymentRegistry;
    private final FilterChainFactory filterChainFactory;

    public RequestProcessor(DeploymentRegistry deploymentRegistry) {
        this.deploymentRegistry = deploymentRegistry;
        this.filterChainFactory = new FilterChainFactory(deploymentRegistry);
    }

    public void process(BufferedReader input, PrintWriter output) throws IOException, ServletException {
        PreMatchingHttpServletRequestImpl servletRequest = SocketToServletRequest.convert(input);

        FilterChain filterChain = filterChainFactory.match(servletRequest.getRequestURI());

        HttpServletResponseImpl servletResponse = new HttpServletResponseImpl();
        filterChain.doFilter(servletRequest, servletResponse);

        ServletResponseToSocket.convert(servletResponse, output);
        
        LOGGER.log(Level.INFO, "Requested URL {0} - Status: {1}", new Object[]{servletRequest.getRequestURI(), servletResponse.getStatus()});
    }
}
