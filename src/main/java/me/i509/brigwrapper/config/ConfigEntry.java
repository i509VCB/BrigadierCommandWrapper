package me.i509.brigwrapper.config;

import java.util.Map;

import de.leonhard.storage.Yaml;

@FunctionalInterface
public interface ConfigEntry<T> {
    public T getValue(Yaml config);
    
    default void register(Map<String, ConfigEntry<?>> map, String id) {
        map.put(id, this);
    }
}
