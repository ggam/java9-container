package eu.ggam.container.impl.servletcontainer.deployer;

import eu.ggam.container.api.http.HttpRequest;
import eu.ggam.container.api.http.HttpResponse;
import eu.ggam.container.impl.servletcontainer.api.DeploymentState;
import eu.ggam.container.impl.servletcontainer.container.ContainerHttpResponseImpl;
import eu.ggam.container.impl.servletcontainer.core.FilterChainFactory;
import eu.ggam.container.impl.servletcontainer.descriptor.materialized.MaterializedWebApp;
import eu.ggam.container.impl.servletcontainer.jsr154.FilterChainImpl;
import eu.ggam.container.impl.servletcontainer.jsr154.HttpServletRequestImpl;
import eu.ggam.container.impl.servletcontainer.jsr154.HttpServletResponseImpl;
import eu.ggam.container.impl.servletcontainer.jsr154.ServletContextImpl;
import eu.ggam.container.impl.servletcontainer.rootwebapp.FileServlet;
import eu.ggam.jlink.launcher.spi.WebAppModule;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServletDeployment {

    public static class ServletDeploymentBuilder {

        private final WebAppModule module;

        public ServletDeploymentBuilder(WebAppModule module) {
            this.module = module;
        }

        public ServletDeployment deploy() {
            ServletDeployment servletDeployment = new ServletDeployment(module);
            servletDeployment.deploy();
            return servletDeployment;
        }
    }

    private static final Logger LOGGER = Logger.getLogger(ServletDeployment.class.getName());

    private String warModuleName;
    private final WebAppModule module;

    private final String contextPath = "/";
    private volatile DeploymentState state = DeploymentState.UNDEPLOYED;

    private MaterializedWebApp webApp;

    private ServletContext servletContext;

    private FilterChainFactory filterChainFactory;

    private ServletDeployment(WebAppModule module) {
        this.module = module;
    }

    private synchronized void changeState(DeploymentState newState) {
        LOGGER.log(Level.INFO, "\"{0}\" context state changed from {1} to {2}", new Object[]{contextPath, this.state, newState});
        this.state = newState;
    }

    private void deploy() {
        webApp = new MaterializedWebApp.Builder(module.getModule()).
                contextParam(ServletContextImpl.InitParams.WEBAPP_MODULE, module.getModule().getName()).
                defaultServlet(FileServlet.class.getName()).
                build();

        servletContext = new ServletContextImpl(webApp);

        filterChainFactory = new FilterChainFactory(servletContext, webApp);

        changeState(DeploymentState.DEPLOYED);
    }

    public void undeploy() {
        failIfNotDeployed();
        changeState(DeploymentState.UNDEPLOYING);

        filterChainFactory.close();

        changeState(DeploymentState.UNDEPLOYED);
    }

    public HttpResponse process(HttpRequest containerRequest) throws IOException, ServletException {
        failIfNotDeployed();

        FilterChainImpl filterChain = filterChainFactory.create(containerRequest.getUri().getPath());

        // TODO: process entity body
        HttpServletRequestImpl servletRequest = new HttpServletRequestImpl(servletContext, containerRequest.getMethod(), containerRequest.getUri(), filterChain.getUriMatch(), containerRequest.getHeaders());
        HttpServletResponseImpl servletResponse = new HttpServletResponseImpl();

        filterChain.doFilter(servletRequest, servletResponse);

        HttpResponse containerResponse = new ContainerHttpResponseImpl();
        sendResponse(servletResponse, containerResponse);

        return containerResponse;
    }

    private void sendResponse(HttpServletResponseImpl response, HttpResponse containerResponse) throws IOException {
        containerResponse.setStatus(response.getStatus());
        String contentType = response.getContentType() != null ? response.getContentType() : "text/html";
        containerResponse.getHeaders().put("Content-Type", List.of(contentType));

        byte[] responseBody = response.getResponseBody();
        containerResponse.getHeaders().put("Content-Length", List.of(String.valueOf(responseBody.length)));

        containerResponse.getOutputStream().write(responseBody);
    }

    public DeploymentState getState() {
        return state;
    }

    private void failIfNotDeployed() {
        DeploymentState currentState = state;
        if (currentState != DeploymentState.DEPLOYED) {
            throw new IllegalStateException(warModuleName + " is not deployed. Currently " + currentState);
        }
    }
}
