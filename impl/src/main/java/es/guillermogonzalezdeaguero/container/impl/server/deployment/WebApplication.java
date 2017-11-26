package es.guillermogonzalezdeaguero.container.impl.server.deployment;

import es.guillermogonzalezdeaguero.container.impl.server.deployment.webxml.WebXmlParser;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toSet;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author guillermo
 */
public class WebApplication {

    private static final Logger LOGGER = Logger.getLogger(WebApplication.class.getName());

    private static final Path RELATIVE_CLASSES_PATH = Paths.get("WEB-INF", "classes");
    private static final Path RELATIVE_LIBS_PATH = Paths.get("WEB-INF", "lib");

    private String warModuleName;
    private Map<String, String> servletMappings;
    private ModuleLayer moduleLayer;
    private final ModuleLayer parentLayer;

    private final String contextPath;
    private final Path appPath;
    private DeploymentState state = DeploymentState.UNDEPLOYED;

    public WebApplication(ModuleLayer parentLayer, Path appPath) {
        this.parentLayer = parentLayer;
        this.appPath = appPath;
        contextPath = "/" + appPath.getFileName().toString();
    }

    private synchronized void changeState(DeploymentState newState) {
        LOGGER.log(Level.INFO, "\"{0}\" context state changed from {1} to {2}", new Object[]{contextPath, this.state, newState});
        this.state = newState;
    }

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

        servletMappings = WebXmlParser.getServletMappings(contextPath, appPath.resolve(Paths.get("WEB-INF", "web.xml")));

        StringBuilder sb = new StringBuilder();
        sb.append("Deployed {0}. Servlet mappings:\n");
        for (Map.Entry<String, String> entry : servletMappings.entrySet()) {
            String urlPattern = entry.getKey();
            String servletClass = entry.getValue();
            sb.append("- ").append(urlPattern).append(" : ").append(servletClass).append("\n");
        }

        LOGGER.log(Level.INFO, sb.toString(), new Object[]{contextPath});

        changeState(DeploymentState.DEPLOYED);
    }

    public synchronized DeploymentState getState() {
        return state;
    }

    public String getContextPath() {
        return contextPath;
    }

    public HttpServlet getServlet(String path) {
        if (state != DeploymentState.DEPLOYED) {
            throw new IllegalStateException(contextPath + " is not deployed");
        }

        String servletClass = servletMappings.get(path);

        if (servletClass == null) {
            return null;
        }

        try {
            HttpServlet servletInstance = (HttpServlet) Class.forName(moduleLayer.findModule(warModuleName).get(), servletClass).getDeclaredConstructor().newInstance();
            return servletInstance;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }
}
