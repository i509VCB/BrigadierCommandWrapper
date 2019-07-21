package me.i509.brigwrapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import me.i509.brigwrapper.command.BrigadierCommand;
import me.i509.brigwrapper.command.BrigadierWrappedCommand;
import me.i509.brigwrapper.config.ConfigWrapper;
import me.i509.brigwrapper.util.Pair;

/**
 * TODO
 * Implement MultiWorld plugin checks
 * ChatComponentArgumentWrapper (ChatComponents)
 * LootTableArgumentWrapper (Loot tables, return the LootTable enum or NamespacedKey of loot table)
 * Wrapper for ASK_SERVER suggestion provider.
 * 
 * 
 * Finish BrigadierHelpTopic implementation.
 * 
 * 
 * GameProfileWrapper (Mojang GameProfiles, such as UUID or username)
 * 
 * Optionally add checking of sender into requires instead of at command runtime.
 * 
 * Create DynamicLiteralArgument which only works if within dynamic string array, along with error support.
 * 
 * Possible:
 * MathOperationWrapper (if possible)
 * 
 * 
 * @author i509VCB
 *
 */
public final class BrigadierWrapper {
    
    public static final String DEFAULT_DESCRIPTION = "This command does not have a defined description";

    public static final String TRUNCATED_DEFAULT = "This command was registered using Brigadier";

    static {
        try {
            INSTANCE = new BrigadierWrapper(Bukkit.getServer());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        if(Bukkit.getWorlds().isEmpty()) { // This works cause we load before the world anyways
            isLoaded = false;
        }
        
        fallbackDimension = ConfigWrapper.readValue(ConfigWrapper.useMultiWorldHandler, BrigadierWrapperPlugin.PACKAGE_INSTANCE.yamlConfig());
    }

    static BrigadierWrapper INSTANCE;
    
    static BrigadierWrapper TEMP_INSTANCE = INSTANCE;

    private static boolean isLoaded;
    
    private SimpleCommandMap commandMap;

    Multimap<String, Pair<String, BrigadierCommand>> internalCommandMap;
    
    Map<String, CommandPermission> permissionMap;

    private static final boolean fallbackDimension; // Immutable from startup
    
    private static boolean debugLogging; // Can change

    public static final String NO_PERMS_DEFAULT = ChatColor.RED + "You don't have permission to use this command.";

    protected BrigadierWrapper(Server server) throws ClassNotFoundException {
        if(Package.getPackage("com.mojang.brigadier") == null) {
            throw new ClassNotFoundException("Could not find brigadier, this plugin requires 1.14 or newer");
        }
        
        internalCommandMap = HashMultimap.create();
        
        DispatcherInstance.getInstance();
        
        commandMap = getSimpleCommandMap();
        
        reloadConfig();
        
        Bukkit.getServer().getHelpMap();
    }
    
    /**
     * Registers a command
     * @param commandName the name of the command to register
     * @param plugin the plugin to use for plugin:command alias
     * @param permission the required permission for command to execute
     * @param command the command to execute
     */
    public static void registerCommand(@NotNull String commandName, @NotNull Plugin plugin, @NotNull CommandPermission permission, @NotNull BrigadierCommand command) {
        registerCommand(commandName, plugin, new String[0], permission, command);
    }
    
    /**
     * Registers a command
     * @param commandName the name of the command to register
     * @param plugin the plugin to use for plugin:command alias
     * @param permission the required permission for command to execute
     * @param command the command to execute
     */
    public static void registerCommand(@NotNull String commandName, @NotNull Plugin plugin, @NotNull String[] aliases, @NotNull CommandPermission permission, @NotNull BrigadierCommand command) {
        
        Validate.notNull(commandName, "Cannot register null command name.");
        Validate.notNull(aliases, "Please create an empty array instead of setting this to null");
        Validate.notNull(permission, "Cannot register null command permission. Use CommandPermission#ofEmpty instead");
        Validate.notNull(command, "Cannot register null command");
        Validate.notNull(plugin, "Must register command with a plugin");
        
        INSTANCE.internalCommandMap.put(plugin.getName(), Pair.create(commandName, command));
        
        //LiteralCommandNode node = command.buildCommand();
        
        BrigadierWrappedCommand currentCmd = new BrigadierWrappedCommand(commandName, new String[0], command, permission, plugin);
        
        System.out.println("Registering command: " + commandName);
        getBukkitCommandMap().register(plugin.getName(), currentCmd);
        
        if(isServerLoaded()) {
            try {
                DispatcherInstance.getInstance().syncCommands();
            } catch (ReflectiveOperationException e) {
                BrigadierWrapperPlugin.PACKAGE_INSTANCE.getLogger().severe("Failed to register command: " + commandName);
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Uses reflection magic to unregister a command from bukkit's command map. This is not recommended for use unless needed and has been deprecated for such reason
     * @param commandName
     */
    @Deprecated
    public static void unregisterBukkitCommand(@NotNull String commandName) {
        try {
            Field f = INSTANCE.commandMap.getClass().getField("knownCommands");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, Command> knownCommands = (Map<String, Command>) f.get(INSTANCE.commandMap);
            knownCommands.remove(commandName);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }


    /**
     * Ends a command and returns the following message as the failure reason. If done inside the execution of a command then the CommandSyntaxException will be caught by command executor
     * @param message The failure reason.
     */
    public static void fail(String message) throws CommandSyntaxException {
        throw new SimpleCommandExceptionType(new LiteralMessage(message)).create();
    }
    
    /**
     * Gets an {@link ImmutableMultimap} with all commands registered by plugins.
     * @return
     */
    public static ImmutableMultimap<String, Pair<String,BrigadierCommand>> getAllCommands() {
        return ImmutableMultimap.copyOf(INSTANCE.internalCommandMap);
    }
    
    public static SimpleCommandMap getBukkitCommandMap() {
        return INSTANCE.getSimpleCommandMap();
    }
    
    public static boolean isServerLoaded() {
        return isLoaded;
    }
    
    /**
     * This should not be used by plugins. This operation cannot be undone without restarting the server.
     */
    @Deprecated
    public static void setLoaded() {
        isLoaded = true;
    }
    
    public static boolean useFallbackDimensionArgument() {
        return fallbackDimension;
    }
    
    public static boolean useDebugLogging() {
        return debugLogging;
    }

    /**
     * This only affects reloadable options like debug logging.
     */
    static void reloadConfig() {
        debugLogging = ConfigWrapper.readValue(ConfigWrapper.debugLogging, BrigadierWrapperPlugin.PACKAGE_INSTANCE.yamlConfig());
    }

    public SimpleCommandMap getSimpleCommandMap() {
        
        if(commandMap !=null) {
            return commandMap;
        }
        
        try {
            commandMap = (SimpleCommandMap) Bukkit.getServer().getClass().getMethod("getCommandMap").invoke(Bukkit.getServer());
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return commandMap;
    }
}
