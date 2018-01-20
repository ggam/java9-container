package es.guillermogonzalezdeaguero.servlet.impl.deployment;

import es.guillermogonzalezdeaguero.container.api.Deployment;
import es.guillermogonzalezdeaguero.container.systemwebapplib.FileServlet;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.EffectiveWebXml;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.WebXmlParser;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.FilterDescriptor;
import es.guillermogonzalezdeaguero.servlet.impl.deployment.webxml.descriptor.ServletDescriptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toSet;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.Servlet;
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
    private EffectiveWebXml effectiveWebXml;
    private Module warModule;

    private ServletContext servletContext;

    public ServletDeployment(ModuleLayer parentLayer, Path appPath) {
        this.parentLayer = parentLayer;
        this.appPath = appPath;

        String fileName = appPath.getFileName().toString();

        // ROOT = /
        this.contextPath = "/" + ("ROOT".equals(fileName) ? "" : fileName);
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

        effectiveWebXml = WebXmlParser.parse(appPath.resolve(Paths.get("WEB-INF", "web.xml")));

        servletContext = new ServletContextImpl(contextPath);

        changeState(DeploymentState.DEPLOYED);
    }

    @Override
    public boolean matches(String url) {
        return url.startsWith(getContextPath());
    }

    @Override
    public void process(InputStream input, OutputStream output) throws IOException, ServletException {
        HttpServletRequestImpl servletRequest = createServletRequest(input);

        FilterChain filterChain = createFilterChain(servletRequest.getRequestURI());

        HttpServletResponseImpl servletResponse = new HttpServletResponseImpl();
        filterChain.doFilter(servletRequest, servletResponse);

        sendResponse(servletResponse, output);
    }

    private HttpServletRequestImpl createServletRequest(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        String[] requestLine = reader.readLine().split(" ");

        System.out.println(requestLine[0]);
        System.out.println(requestLine[1]);

        String method = requestLine[0];

        String[] uriQueryParams = requestLine[1].split("\\?");
        String uri = uriQueryParams[0];
        if (uriQueryParams.length == 2) {
            String queryParams = uriQueryParams[1]; // TODO: support query params
        }

        String httpVersion = requestLine[2];

        Map<String, List<String>> headers = new HashMap<>();

        String line;
        while ((line = reader.readLine()) != null) {
            if ("".equals(line)) {
                // Headers are done.
                break;
            }

            String[] header = line.split(":");
            List<String> headerValues = headers.computeIfAbsent(header[0], k -> new ArrayList<>());
            //System.out.println(line);
            headerValues.add(header[1]);
        }

        // TODO: process entity body
        return new HttpServletRequestImpl(servletContext, method, uri, headers);
    }

    private void sendResponse(HttpServletResponseImpl response, OutputStream output) throws IOException {
        output.write(("HTTP/1.1 " + response.getStatus() + " Unknown\r\n\r\n").getBytes());
        if (response.isErrorSent() && response.getStatusMessage() != null) {
            output.write(response.getStatusMessage().getBytes());
        } else {
            output.write(response.getStringWriter().toString().getBytes());
        }

    }

    public FilterChain createFilterChain(String url) {
        if (!matches(url)) {
            throw new IllegalArgumentException("Url cannot be matched to this application");
        }

        String pathInfo = url.substring(getContextPath().length(), url.length()); // TODO: pathInfo may be null?

        ServletDescriptor servletMatch = findServletMatch(effectiveWebXml.getServletDescriptors(), pathInfo);
        Queue<FilterDescriptor> matchedFilters = findFilterMatches(effectiveWebXml.getFilterDescriptors(), pathInfo);

        String servletClassName;
        if (servletMatch != null) {
            servletClassName = servletMatch.getClassName();
        } else {
            // Fallback to FileServlet when no match is found
            servletClassName = FileServlet.class.getName();
        }

        Servlet servletInstance = null;
        try {
            Class<?> servletClass = Class.forName(servletClassName, true, warModule.getClassLoader());

            servletInstance = (Servlet) Class.forName(servletClass.getModule(), servletClass.getName()).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        ArrayDeque<Filter> matchedFilterInstances = new ArrayDeque<>();
        for (FilterDescriptor matchedFilter : matchedFilters) {
            try {
                Class<?> filterClass = Class.forName(matchedFilter.getClassName(), true, warModule.getClassLoader());

                matchedFilterInstances.add((Filter) Class.forName(filterClass.getModule(), filterClass.getName()).getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

        return new FilterChainImpl(matchedFilterInstances, servletInstance);
    }

    public ServletDescriptor findServletMatch(Set<ServletDescriptor> servletDescriptors, String pathInfo) {
        for (ServletDescriptor servletDescriptor : servletDescriptors) {
            for (String exactPattern : servletDescriptor.getExactPatterns()) {
                if (exactPattern.equals(pathInfo) || (pathInfo == null && "/".equals(exactPattern))) {
                    return servletDescriptor;
                }
            }

            for (String prefixPattern : servletDescriptor.getPrefixPatterns()) {
                String prefix = prefixPattern.substring(0, prefixPattern.length() - 1);
                if (pathInfo != null && pathInfo.startsWith(prefix)) {
                    return servletDescriptor;
                }
            }

            if (pathInfo == null) {
                return null; // TODO: Default Servlet?
            }

            for (String extensionPattern : servletDescriptor.getExtensionPatterns()) {
                String extension = extensionPattern.split(".")[1];
                if (pathInfo.endsWith(extension)) {
                    return servletDescriptor;
                }
            }

        }
        return null;
    }

    public Queue<FilterDescriptor> findFilterMatches(Set<FilterDescriptor> filterDescriptors, String pathInfo) {
        Queue<FilterDescriptor> matchedFilters = new ArrayDeque<>();
        for (FilterDescriptor filterDescriptor : filterDescriptors) {
            for (String exactPattern : filterDescriptor.getExactPatterns()) {
                if (exactPattern.equals(pathInfo) || (pathInfo == null && "/".equals(exactPattern))) {
                    matchedFilters.add(filterDescriptor);
                }
            }

            for (String prefixPattern : filterDescriptor.getPrefixPatterns()) {
                String prefix = prefixPattern.substring(0, prefixPattern.length() - 1);
                if (pathInfo != null && pathInfo.startsWith(prefix)) {
                    matchedFilters.add(filterDescriptor);
                }
            }

            if (pathInfo == null) {
                // TODO: pending review
                // matchedFilters.add(filterDescriptor);
            }

            for (String extensionPattern : filterDescriptor.getExtensionPatterns()) {
                String extension = extensionPattern.split(".")[1];
                if (pathInfo != null && pathInfo.endsWith(extension)) {
                    matchedFilters.add(filterDescriptor);
                }
            }

        }
        return matchedFilters;
    }

    public synchronized DeploymentState getState() {
        return state;
    }

    @Override
    public String getContextPath() {
        return contextPath;
    }
}
