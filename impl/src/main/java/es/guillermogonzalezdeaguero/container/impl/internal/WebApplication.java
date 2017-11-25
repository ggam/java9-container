package es.guillermogonzalezdeaguero.container.impl.internal;

import es.guillermogonzalezdeaguero.container.impl.internal.webxml.WebXmlParser;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author guillermo
 */
public class WebApplication {

    private static final Path RELATIVE_CLASSES_PATH = Paths.get("WEB-INF", "classes");
    private static final Path RELATIVE_LIBS_PATH = Paths.get("WEB-INF", "lib");

    private final String contextPath;
    private final String warModuleName;
    private final Map<String, String> servletMappings;
    private final ModuleLayer moduleLayer;

    public WebApplication(ModuleLayer parentLayer, Path appPath) {

        ModuleFinder warModuleFinder = ModuleFinder.of(appPath.resolve(RELATIVE_CLASSES_PATH));
        warModuleName = warModuleFinder.findAll().
                stream().
                map(ModuleReference::descriptor).
                map(ModuleDescriptor::name).
                findAny().get();
        System.out.println("War module: " + warModuleName);

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
        contextPath = "/" + appPath.getFileName().toString();

        servletMappings = WebXmlParser.getServletMappings(appPath.resolve(Paths.get("WEB-INF", "web.xml")));
        System.out.println(contextPath + " Servlet mappings: " + servletMappings);
    }

    public String getContextPath() {
        return contextPath;
    }

    public HttpServlet getServlet(String path) {
        path = "/example-servlet"; // TODO:
        System.out.println("Requested path " + path);

        String servletClass = servletMappings.get(path);

        if (servletClass == null) {
            return null;
        }

        try {
            System.out.println("00000000000000000");
            System.out.println(servletMappings);
            System.out.println(Class.forName(moduleLayer.findModule(warModuleName).get(), servletClass));
            System.out.println("1111111111111111");

            HttpServlet servletInstance = (HttpServlet) Class.forName(moduleLayer.findModule(warModuleName).get(), servletClass).getDeclaredConstructor().newInstance();

            System.out.println(servletClass);
            System.out.println(servletInstance);
            return servletInstance;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
