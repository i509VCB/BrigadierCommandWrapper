package me.i509.brigwrapper.config;

import de.leonhard.storage.Yaml;

public class ConfigWrapper {
    
    /**
     * Represents the config setting of weather to use fallback mode on {@link WorldArgumentWrapper} or not
     */
    public static final ConfigEntry<Boolean> useMultiWorldHandler = (yaml) -> {
        return yaml.getOrSetDefault("useMultiWorldHandler", false); // Handle missing cases where config does not exist yet.
    };
    
    /**
     * Weather to use debug logging or not.
     */
    public static final ConfigEntry<Boolean> debugLogging = (yaml) -> {
        return yaml.getOrSetDefault("debugLogging", false);
    };
    
    /**
     * Reads the current config entry from the file.
     * @param <T> Type of the value being read.
     * @param entry The entry to read
     * @param config The Yaml config file to check
     * @return The value of the entry when updated.
     */
    public static <T> T readValue(ConfigEntry<T> entry, Yaml config) {
        return entry.getValue(config);
    }
}
