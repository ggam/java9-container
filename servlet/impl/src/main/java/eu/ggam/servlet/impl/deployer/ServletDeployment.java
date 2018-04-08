package eu.ggam.servlet.impl.deployer;

import eu.ggam.container.api.http.HttpRequest;
import eu.ggam.container.api.http.HttpResponse;
import eu.ggam.servlet.impl.api.Deployment;
import eu.ggam.servlet.impl.api.DeploymentState;
import eu.ggam.servlet.impl.container.ContainerHttpResponseImpl;
import eu.ggam.servlet.impl.core.FilterChainFactory;
import eu.ggam.servlet.impl.descriptor.materialized.MaterializedWebApp;
import eu.ggam.servlet.impl.jsr154.FilterChainImpl;
import eu.ggam.servlet.impl.jsr154.HttpServletRequestImpl;
import eu.ggam.servlet.impl.jsr154.HttpServletResponseImpl;
import eu.ggam.servlet.impl.jsr154.ServletContextImpl;
import eu.ggam.servlet.impl.rootwebapp.FileServlet;
import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toSet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServletDeployment implements Deployment {

    private static final Logger LOGGER = Logger.getLogger(ServletDeployment.class.getName());

    private static final Path RELATIVE_CLASSES_PATH = Paths.get("WEB-INF", "classes");
    private static final Path RELATIVE_LIBS_PATH = Paths.get("WEB-INF", "lib");

    private String warModuleName;
    private ModuleLayer moduleLayer;
    private final ModuleLayer parentLayer;

    private final String contextPath;
    private final Path appPath;
    private volatile DeploymentState state = DeploymentState.UNDEPLOYED;
    private Module warModule;

    private MaterializedWebApp webApp;

    private ServletContext servletContext;

    private FilterChainFactory filterChainFactory;

    public ServletDeployment(ModuleLayer parentLayer, Path appPath) {
        this.parentLayer = parentLayer;
        this.appPath = appPath;

        String fileName = appPath.getFileName().toString();

        this.contextPath = ("ROOT".equals(fileName) ? "" : "/" + fileName);
    }

    private synchronized void changeState(DeploymentState newState) {
        LOGGER.log(Level.INFO, "\"{0}\" context state changed from {1} to {2}", new Object[]{contextPath, this.state, newState});
        this.state = newState;
    }

    @Override
    public void deploy() {
        changeState(DeploymentState.DEPLOYING);
        ModuleFinder warModuleFinder = ModuleFinder.of(appPath.resolve(RELATIVE_CLASSES_PATH));
        warModuleName = warModuleFinder.findAll().
                stream().
                map(ModuleReference::descriptor).
                map(ModuleDescriptor::name).
                findAny().get();

        ModuleFinder libsModuleFinder = ModuleFinder.of(appPath.resolve(RELATIVE_LIBS_PATH));
        Set<String> moduleNames = libsModuleFinder.findAll().
                stream().
                map(ModuleReference::descriptor).
                map(ModuleDescriptor::name).
                collect(toSet());

        moduleNames.add(warModuleName);

        Configuration cf = parentLayer.configuration().
                resolve(ModuleFinder.compose(warModuleFinder, libsModuleFinder), ModuleFinder.of(), moduleNames);

        moduleLayer = parentLayer.defineModulesWithOneLoader(cf, ClassLoader.getSystemClassLoader());
        warModule = moduleLayer.findModule(warModuleName).get();

        webApp = new MaterializedWebApp.Builder(appPath, warModule.getClassLoader()).
                contextParam(ServletContextImpl.InitParams.WEBAPP_PATH, appPath.toAbsolutePath().toString()).
                fileServlet(FileServlet.class.getName()).
                build();

        servletContext = new ServletContextImpl(webApp);

        filterChainFactory = new FilterChainFactory(servletContext, webApp);

        changeState(DeploymentState.DEPLOYED);
    }

    @Override
    public void undeploy() {
        failIfNotDeployed();
        changeState(DeploymentState.UNDEPLOYING);

        filterChainFactory.close();

        changeState(DeploymentState.UNDEPLOYED);
    }

    @Override
    public boolean matches(String url) {
        return url.startsWith(getContextPath());
    }

    @Override
    public HttpResponse process(HttpRequest containerRequest) throws IOException, ServletException {
        failIfNotDeployed();

        String uriPath = containerRequest.getUri().getPath();
        if (!matches(uriPath)) {
            throw new IllegalArgumentException("Url cannot be matched to this application");
        }

        String appUri = uriPath.substring(contextPath.length()); // URI without contextPath

        FilterChainImpl filterChain = filterChainFactory.create(appUri);

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

    @Override
    public DeploymentState getState() {
        return state;
    }

    @Override
    public String getModuleName() {
        return warModuleName;
    }

    @Override
    public String getContextPath() {
        return contextPath;
    }

    private void failIfNotDeployed() {
        DeploymentState currentState = state;
        if (currentState != DeploymentState.DEPLOYED) {
            throw new IllegalStateException(warModuleName + " is not deployed. Currently " + currentState);
        }
    }
}
