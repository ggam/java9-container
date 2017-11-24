package es.guillermogonzalezdeaguero.container.impl;

import es.guillermogonzalezdeaguero.container.api.Plugin;
import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

/**
 *
 * @author guillermo
 */
public class Container {

    private final Map<String, ModuleLayer> resolvedPlugins;

    public Container(ModuleLayer parentLayer, Path pluginPath) {

        resolvedPlugins = new HashMap<>();

        try {
            Set<Path> detectedPlugins = Files.list(pluginPath).filter(Files::isDirectory).collect(toSet());

            for (Path detectedPlugin : detectedPlugins) {
                ModuleFinder pluginModuleFinder = ModuleFinder.of(detectedPlugin);
                Set<String> pluginNames = pluginModuleFinder.findAll().
                        stream().
                        map(ModuleReference::descriptor).
                        map(ModuleDescriptor::name).
                        collect(toSet()); // Modules of the plugin

                System.out.println(pluginNames);

                Configuration cf = parentLayer.configuration().
                        resolve(pluginModuleFinder, ModuleFinder.of(), pluginNames);

                System.out.println("layering: " + detectedPlugin.getName(detectedPlugin.getNameCount() - 1).toString());
                resolvedPlugins.put(detectedPlugin.getName(detectedPlugin.getNameCount() - 1).toString(), parentLayer.defineModulesWithOneLoader(cf, ClassLoader.getSystemClassLoader()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("plugins: " + resolvedPlugins);
    }

    public void runAllPlugins() {
        System.out.println(resolvedPlugins);
        resolvedPlugins.values().
                forEach((layer) -> ServiceLoader.load(layer, Plugin.class).
                iterator().
                forEachRemaining(Plugin::run));
    }
}
