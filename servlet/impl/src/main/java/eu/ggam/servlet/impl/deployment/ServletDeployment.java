package eu.ggam.servlet.impl.deployment;

import eu.ggam.container.api.Deployment;
import eu.ggam.container.api.http.HttpRequest;
import eu.ggam.container.api.http.HttpResponse;
import eu.ggam.servlet.impl.FilterChainFactory;
import eu.ggam.servlet.impl.HttpServletRequestImpl;
import eu.ggam.servlet.impl.HttpServletResponseImpl;
import eu.ggam.servlet.impl.deployment.webxml.EffectiveWebXml;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toSet;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *
 * @author guillermo
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
    private DeploymentState state = DeploymentState.UNDEPLOYED;
    private Module warModule;

    private EffectiveWebXml webXml;

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

        try (InputStream is = Files.newInputStream(appPath.resolve(Paths.get("WEB-INF", "web.xml")))) {
            webXml = new EffectiveWebXml(contextPath, is, warModule.getClassLoader());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        servletContext = webXml.getServletContext();

        filterChainFactory = new FilterChainFactory(webXml.getServletDescriptors(), webXml.getFilterDescriptors());

        changeState(DeploymentState.DEPLOYED);
    }

    @Override
    public boolean matches(String url) {
        return url.startsWith(getContextPath());
    }

    @Override
    public void process(HttpRequest containerRequest, HttpResponse containerResponse) throws IOException, ServletException {
        // TODO: process entity body
        HttpServletRequestImpl servletRequest = new HttpServletRequestImpl(servletContext, containerRequest.getMethod(), containerRequest.getUri(), containerRequest.getHeaders());
        HttpServletResponseImpl servletResponse = new HttpServletResponseImpl();

        if (!matches(servletRequest.getRequestURI())) {
            throw new IllegalArgumentException("Url cannot be matched to this application");
        }

        FilterChain filterChain = filterChainFactory.create(servletRequest.getPathInfo());

        filterChain.doFilter(servletRequest, servletResponse);

        sendResponse(servletResponse, containerResponse);
    }

    private void sendResponse(HttpServletResponseImpl response, HttpResponse containerResponse) throws IOException {
        containerResponse.setStatus(response.getStatus());
        String contentType = response.getContentType() != null ? response.getContentType() : "text/html";
        containerResponse.getHeaders().put("Content-Type", List.of(contentType));

        byte[] responseBody = response.getResponseBody();
        containerResponse.getHeaders().put("Content-Length", List.of(String.valueOf(responseBody.length)));
        
        containerResponse.getOutputStream().write(responseBody);
    }

    public synchronized DeploymentState getState() {
        return state;
    }

    @Override
    public String getContextPath() {
        return contextPath;
    }
}
