package es.guillermogonzalezdeaguero.container.impl;

import es.guillermogonzalezdeaguero.container.api.ServerPage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static java.util.stream.Collectors.toSet;

/**
 *
 * @author guillermo
 */
public class Container {

    private static final int PORT = 8282;
    private final Map<String, ModuleLayer> plugins;
    private final Map<String, ServerPage> pages;

    public Container(ModuleLayer parentLayer, Path pluginPath) {

        plugins = detectPlugins(parentLayer, pluginPath);
        System.out.println("Resolved plugins " + plugins + "\n");

        pages = resolvePages(plugins.values());
    }

    private Map<String, ModuleLayer> detectPlugins(ModuleLayer parentLayer, Path pluginPath) {
        Map<String, ModuleLayer> foundPlugins = new HashMap<>();

        try {
            Set<Path> detectedPlugins = Files.list(pluginPath).filter(Files::isDirectory).collect(toSet());

            for (Path detectedPlugin : detectedPlugins) {
                ModuleFinder pluginModuleFinder = ModuleFinder.of(detectedPlugin);
                Set<String> pluginNames = pluginModuleFinder.findAll().
                        stream().
                        map(ModuleReference::descriptor).
                        map(ModuleDescriptor::name).
                        collect(toSet()); // Modules of the plugin

                Configuration cf = parentLayer.configuration().
                        resolve(pluginModuleFinder, ModuleFinder.of(), pluginNames);

                foundPlugins.put(detectedPlugin.getName(detectedPlugin.getNameCount() - 1).toString(), parentLayer.defineModulesWithOneLoader(cf, ClassLoader.getSystemClassLoader()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return foundPlugins;
    }

    private Map<String, ServerPage> resolvePages(Collection<ModuleLayer> layers) {
        Map<String, ServerPage> resolvedPages = new HashMap<>();
        for (ModuleLayer layer : layers) {
            Iterator<ServerPage> it = ServiceLoader.load(layer, ServerPage.class).iterator();
            while (it.hasNext()) {
                ServerPage serverPage = it.next();

                String url = serverPage.getUrl();

                if (resolvedPages.containsKey(url)) {
                    throw new RuntimeException("Duplicated URL page: " + url);
                }
                resolvedPages.put(url, serverPage);
            }
        }
        
        resolvedPages.computeIfAbsent("/", (k) -> new DefaultRootServerPage(new HashSet<>(resolvedPages.keySet())));

        return resolvedPages;
    }

    public void startServer() throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Started server at port " + PORT);
        while (true) {
            Socket request = serverSocket.accept();
            executor.submit(() -> handleRequest(request));
        }
    }

    private void handleRequest(Socket request) {
        try (request;
                BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
                PrintWriter out = new PrintWriter(request.getOutputStream(), true)) {

            String url = in.readLine().split("GET ")[1].split(" HTTP")[0];
            System.out.println("Requested URL " + url);

            ServerPage serverPage = pages.get(url);

            String response = null;

            if (serverPage != null) {
                response = createResponse(200, serverPage.process());
            } else {
                response = createResponse(404, "Not found");
            }

            out.print(response);
        } catch (IOException e) {
            System.err.println("Error responding to request");
            e.printStackTrace();
        }
    }

    private String createResponse(int statusCode, String responseBody) {
        return "HTTP/1.1 " + statusCode + "\n"
                + "Content-type: text/html\n"
                + "Server-name: localhost\n"
                + "Content-length: " + responseBody.length() + "\n"
                + "\n"
                + responseBody;
    }
}
