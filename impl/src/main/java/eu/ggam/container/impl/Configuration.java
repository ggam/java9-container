package eu.ggam.container.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

/**
 *
 * @author Guillermo González de Agüero
 */
public class Configuration {

    public static enum Option {
        PORT("port", Integer.class, () -> 8383),
        WAR_MODULE("war_module", String.class, () -> {
            throw new IllegalStateException("war_module property is required");
        });

        private final String key;
        private final Class<?> clazz;
        private final Supplier<?> defaultValue;

        private Option(String key, Class<?> clazz, Supplier<?> defaultValue) {
            this.key = key;
            this.clazz = clazz;
            this.defaultValue = defaultValue;
        }
    }

    private final Map<Option, Object> values = new HashMap<>();

    public Configuration(Properties properties) {
        for (Option option : Option.values()) {
            Object value = transformIfNeeded(properties.getProperty(option.key), option.clazz);

            if (value == null) {
                value = option.defaultValue.get(); // Only compute if the key was not found
            }
            values.put(option, value);
        }
    }

    public Integer getInteger(Option option) {
        return (Integer) values.get(option);
    }

    public String getString(Option option) {
        return (String) values.get(option);
    }

    private Object transformIfNeeded(String value, Class<?> targetClass) {
        if (value == null || targetClass.isAssignableFrom(value.getClass())) {
            return value; // No transformation needed
        }

        if (Integer.class.isAssignableFrom(targetClass)) {
            return Integer.parseInt(value);
        }

        throw new IllegalArgumentException("Only integer transformation is supported. Requested " + targetClass.getName());
    }
}
