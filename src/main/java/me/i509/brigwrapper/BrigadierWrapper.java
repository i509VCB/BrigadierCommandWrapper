package me.i509.brigwrapper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
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
import me.i509.util.Pair;

/**
 * TODO
 * Implement MultiWorld plugin checks
 * ChatComponentArgumentWrapper (ChatComponents)
 * LootTableArgumentWrapper (Loot tables, return the LootTable enum or NamespacedKey of loot table) TODO WIP
 * 
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

    private ConfirmationUtil confutil;

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

    /**
     * Verifies if this {@link CommandSender} is a player.
     * @param sender The command sender to test.
     * @return true if the sender is a player, otherwise false.
     */
    public static boolean isPlayer(@NotNull CommandSender sender) {
        return sender instanceof Player;
    }

    /**
     * Verifies if this {@link CommandSender} is not a {@link ConsoleCommandSender}.
     * @param sender The command sender to test.
     * @return false if the CommandSender is the console, otherwise true.
     */
    public static boolean notConsole(@NotNull CommandSender sender) {
        return !(sender instanceof ConsoleCommandSender);
    }

    /**
     * 
     * Verifies if this {@link CommandSender} is a Player, otherwise ends the command.
     * @param sender The command sender to test.
     * @param failureMessage The failure message.
     * @throws CommandSyntaxException To end the command if the sender is not a player.
     */
    public static void notPlayerThenFail(@NotNull CommandSender sender, @NotNull String failureMessage) throws CommandSyntaxException {
        if(sender instanceof Player) {
            return;
        }
        
        fail(failureMessage);
    }

    /**
     * 
     * Verifies if this {@link CommandSender} is not the Console, otherwise ends the command.
     * @param sender The command sender to test.
     * @param failureMessage The failure message.
     * @throws CommandSyntaxException To end the command if the sender is the console.
     */
    public static void ifConsoleFail(@NotNull CommandSender sender, @NotNull String failureMessage) throws CommandSyntaxException {
        if(sender instanceof ConsoleCommandSender) {
            fail(failureMessage);
        }
    }

    /**
     * Tests that this CommandSender has permission to.
     * @param source The command source.
     * @param perm The permission to test for.
     * @return true if the sender has permission, otherwise false.
     */
    public static boolean testSenderPerms(@NotNull CommandSource source, @NotNull CommandPermission perm) {
        if(perm.noPermissionNeeded()) {
            return true;
        }
        
        if(perm.isString()) {
            if(source.getSender().hasPermission(perm.asString())) return true; else return false;
        } else {
            switch(perm.type()) {
            case CONSOLE:
                if(source.getSender() instanceof ConsoleCommandSender) return true; else return false;
            case COMMAND_BLOCK:
                if(source.getSender() instanceof BlockCommandSender) return true; else return false;
            case OP:
                if(source.getSender().isOp()) return true; else return false;
            default:
                return false;
            }
        }
    }

    public static boolean testSenderPerms(@NotNull CommandSender sender, @NotNull CommandPermission perm) {
        if(perm.noPermissionNeeded()) {
            return true;
        }
        
        if(perm.isString()) {
            if(sender.hasPermission(perm.asString())) return true; else return false;
        } else {
            switch(perm.type()) {
            case CONSOLE:
                if(sender instanceof ConsoleCommandSender) return true; else return false;
            case COMMAND_BLOCK:
                if(sender instanceof BlockCommandSender) return true; else return false;
            case OP:
                if(sender.isOp()) return true; else return false;
            default:
                return false;
            }
        }
    }

    public static String joinWithSpace(String commandLabel, String... strings) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(commandLabel);
        sb.append(" ");
        
        Iterator<String> i = Arrays.stream(strings).iterator();
        
        while (i.hasNext()) {
            sb.append(i.next());
            if(i.hasNext()) {
                sb.append(" ");
            }
        }
        
        return sb.toString();
    }

    public static ConfirmationUtil getConfirm() {
        return INSTANCE.confutil;
    }
}
