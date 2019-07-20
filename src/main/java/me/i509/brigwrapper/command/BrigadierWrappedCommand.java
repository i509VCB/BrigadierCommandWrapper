package me.i509.brigwrapper.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Joiner;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.suggestion.Suggestions;

import me.i509.brigwrapper.CommandPermission;
import me.i509.brigwrapper.DispatcherInstance;

public class BrigadierWrappedCommand extends Command implements PluginIdentifiableCommand {

    private CommandPermission permission;
    private Plugin plugin;
    private BrigadierCommand wrapped;

    public BrigadierWrappedCommand(String name, String[] aliases, BrigadierCommand wrapped, CommandPermission permission, Plugin plugin) {
        super(name, "", "/" + name, Arrays.asList(aliases));
        this.permission = permission;
        this.plugin = plugin;
        this.wrapped = wrapped;
    }
    
    public BrigadierCommand getWrapped() {
        return this.wrapped;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!testPermission(sender)) return true;
        
        return DispatcherInstance.getInstance().execute(sender, commandLabel, getName(), args); // Will Always be true
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args, Location location) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        ParseResults parsed = DispatcherInstance.getInstance().parse(toDispatcher(args, getName()), sender);
        
        List<String> results = new ArrayList<>();
        DispatcherInstance.getInstance().dispatcher().getCompletionSuggestions(parsed).thenAccept((suggestions) -> {
            ((Suggestions) suggestions).getList().forEach((s) -> results.add(s.getText()));
        });

        return results;
    }
    
    public boolean testPermission(@NotNull CommandSender target) {
        if (permission.noPermissionNeeded()) {
            return true;
        }
        
        

        if (permission.isString()) {
            if(target.hasPermission(permission.asString())) {
                return true;
            } else {
                if (!wrapped.silentPerms()) {
                    target.sendMessage(wrapped.noPermsMessage());
                }
                return false;
            }
        }
        
        switch(permission.type()) {
        case COMMAND_BLOCK:
            if (target instanceof BlockCommandSender) return true;
        case CONSOLE:
            if (target instanceof ConsoleCommandSender) return true;
        case OP:
            if (target.isOp()) return true;
        default:
            break;
        }
        if (!wrapped.silentPerms()) {
            target.sendMessage(wrapped.noPermsMessage());
        }
        return false;
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }
    
    
    public static String toDispatcher(String[] args, String name) {
        return "/" + name + ((args.length > 0) ? " " + Joiner.on(' ').join(args) : "");
    }
}
