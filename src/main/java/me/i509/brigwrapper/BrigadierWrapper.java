package me.i509.brigwrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;

import me.i509.brigwrapper.command.BrigadierCommand;
import me.i509.brigwrapper.source.CommandSource;
import me.i509.brigwrapper.util.CommandUtils;
import me.i509.brigwrapper.util.Pair;

public final class BrigadierWrapper {
    
    public static final String DEFAULT_DESCRIPTION = "This command does not have a defined description";

    public static final String TRUNCATED_DEFAULT = "This command was registered using Brigadier";

    static {
        try {
            INSTANCE = new BrigadierWrapper(Bukkit.getServer());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        if(Bukkit.getWorlds().isEmpty()) {
            isLoaded = false;
        }
    }

    static BrigadierWrapper INSTANCE;
    
    public static BrigadierWrapper TEMP_INSTANCE = INSTANCE;
    
    @SuppressWarnings("rawtypes")
    private static CommandDispatcher dispatcher;

    private static boolean isLoaded;
    
    private SimpleCommandMap commandMap;

    Multimap<String, Pair<String, BrigadierCommand>> internalCommandMap;
    
    Map<String, CommandPermission> permissionMap;

    protected BrigadierWrapper(Server server) throws ClassNotFoundException {
        if(Package.getPackage("com.mojang.brigadier") == null) {
            throw new ClassNotFoundException("Could not find brigadier, this plugin requires 1.14 or newer");
        }
        
        internalCommandMap = HashMultimap.create();
        
        permissionMap = new HashMap<String, CommandPermission>();
        
        dispatcher = DispatcherInstance.getInstance().dispatcher(); //Create instance and store for local use
        
        commandMap = getSimpleCommandMap();
        
        Bukkit.getServer().getHelpMap();
    }
    
    /**
     * Registers a command
     * @param commandName the name of the command to register
     * @param plugin the plugin to use for plugin:command alias
     * @param permission the required permission for command to execute
     * @param command the command to execute
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void registerCommand(@NotNull String commandName, @NotNull Plugin plugin, @NotNull CommandPermission permission, @NotNull BrigadierCommand command) {
        
        Validate.notNull(commandName, "Cannot register null command name");
        Validate.notNull(permission, "Cannot register null command permission. Use CommandPermission#ofEmpty instead");
        Validate.notNull(command, "Cannot register null command");
        Validate.notNull(plugin, "Must register command with a plugin");
        
        INSTANCE.internalCommandMap.put(plugin.getName(), Pair.create(commandName, command));
        
        INSTANCE.permissionMap.put(commandName, permission);
        
        //DispatcherInstance.getInstance().knownCommands();
        
        LiteralCommandNode node = command.buildCommand();
        
        /*
        if(plugin !=null) {
            dispatcher.register((LiteralArgumentBuilder) LiteralArgumentBuilder.literal(plugin.getName() + ":" + commandName)
                    .requires(csource -> CommandUtils.testSenderPerms(CommandSource.getSource(csource), permission))
                        .redirect(node));
        }*/
         // TODO Aliases
        dispatcher.register((LiteralArgumentBuilder) LiteralArgumentBuilder.literal(commandName)
                .requires(csource -> CommandUtils.testSenderPerms(CommandSource.getSource(csource), permission))
                    .redirect(node));
    }


    /**
     * Ends a command because of an error and returns the following message as the reason. If done inside the execution of a command then the CommandSyntaxException will be caught by command executor
     * @param message The failure reason.
     */
    public static void fail(String message) throws CommandSyntaxException {
        throw new SimpleCommandExceptionType(new LiteralMessage(message)).create();
    }
    
    /**
     * Ends a command because of an error and returns the following message from the supplier as the reason. If done inside the execution of a command then the CommandSyntaxException will be caught by command executor
     * @param supplier the supplier ran to get the failure reason of the command. Such as in the case of a language system or undoing actions before ending a command. 
     * 
     */
    public static void fail(@NotNull Supplier<String> supplier) throws CommandSyntaxException {
        throw new SimpleCommandExceptionType(new LiteralMessage(supplier.get())).create();
    }
    
    /**
     * Gets an {@linkplain ImmutableMultimap} with all commands registered by plugins. Any commands registered with a null plugin will be underneath the a key with the string "null"
     * @return
     */
    public static ImmutableMultimap<String, Pair<String,BrigadierCommand>> getAllCommands() {
        return ImmutableMultimap.copyOf(INSTANCE.internalCommandMap);
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
        return null;
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
}
