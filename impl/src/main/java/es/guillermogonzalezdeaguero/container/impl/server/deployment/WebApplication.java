package es.guillermogonzalezdeaguero.container.impl.server.deployment;

import es.guillermogonzalezdeaguero.container.impl.server.deployment.webxml.EffectiveWebXml;
import es.guillermogonzalezdeaguero.container.impl.server.deployment.webxml.WebXmlParser;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toSet;

/**
 *
 * @author guillermo
 */
public class WebApplication {

    private static final Logger LOGGER = Logger.getLogger(WebApplication.class.getName());

    private static final Path RELATIVE_CLASSES_PATH = Paths.get("WEB-INF", "classes");
    private static final Path RELATIVE_LIBS_PATH = Paths.get("WEB-INF", "lib");

    private String warModuleName;
    private ModuleLayer moduleLayer;
    private final ModuleLayer parentLayer;

    private final String contextPath;
    private final Path appPath;
    private DeploymentState state = DeploymentState.UNDEPLOYED;
    private EffectiveWebXml effectiveWebXml;
    private Module warModule;

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
        warModule = moduleLayer.findModule(warModuleName).get();

        effectiveWebXml = WebXmlParser.parse(appPath.resolve(Paths.get("WEB-INF", "web.xml")));

        changeState(DeploymentState.DEPLOYED);
    }

    public synchronized DeploymentState getState() {
        return state;
    }

    public String getContextPath() {
        return contextPath;
    }

    public EffectiveWebXml getEffectiveWebXml() {
        return effectiveWebXml;
    }

    public Module getWarModule() {
        return warModule;
    }

}
