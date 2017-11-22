package es.guillermogonzalezdeaguero.container.impl;

import es.guillermogonzalezdeaguero.container.api.Plugin;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Paths;
import java.util.ServiceLoader;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

/**
 *
 * @author guillermo
 */
public class Launcher {

    public static void main(String[] args) {
        ModuleLayer parent = ModuleLayer.boot();
        
        ModuleFinder pluginFinder = ModuleFinder.of(Paths.get("plugins"));
        Set<String> plugins = pluginFinder.findAll().
                stream().
                map(ModuleReference::descriptor).
                map(ModuleDescriptor::name).
                collect(toSet());
        
        Configuration cf = parent.configuration().
                resolve(pluginFinder, ModuleFinder.of(), plugins);

        ModuleLayer layer = parent.defineModulesWithOneLoader(cf, ClassLoader.getSystemClassLoader());

        ServiceLoader.load(layer, Plugin.class).
                iterator().
                forEachRemaining(Plugin::run);
    }
}
