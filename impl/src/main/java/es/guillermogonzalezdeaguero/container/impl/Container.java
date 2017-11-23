package es.guillermogonzalezdeaguero.container.impl;

import es.guillermogonzalezdeaguero.container.api.Plugin;
import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            Set<ModuleFinder> collect = Files.find(pluginPath, 1, (path, attributes) -> path.toString().endsWith(".jar")).
                    map(ModuleFinder::of).
                    collect(toSet());
            
            for(ModuleFinder moduleFinder:  collect) {
                System.out.println("ModuleFinder: " + moduleFinder);
                for(ModuleReference moduleReference : moduleFinder.findAll()) {
                    System.out.println("ModuleFinder: " + moduleReference);
                    Path get = Paths.get(moduleReference.location().get());
                    String name = moduleReference.descriptor().name();
                    
                    resolvedPlugins.put(name, getLayer(parentLayer, moduleFinder, name));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ModuleFinder pluginFinder = ModuleFinder.of(pluginPath);

    }

    private ModuleLayer getLayer(ModuleLayer parentLayer, ModuleFinder moduleFinder, String pluginName) {

        Configuration cf = parentLayer.configuration().
                resolve(moduleFinder, ModuleFinder.of(), Set.of(pluginName));

        System.out.println("Processed " + pluginName);

        return parentLayer.defineModulesWithOneLoader(cf, ClassLoader.getSystemClassLoader());
    }

    public void runAllPlugins() {
        System.out.println(resolvedPlugins);
        resolvedPlugins.values().
                forEach((layer) -> ServiceLoader.load(layer, Plugin.class).
                iterator().
                forEachRemaining(Plugin::run));
    }
}
