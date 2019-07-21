package me.i509.brigwrapper.command;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;

import me.i509.brigwrapper.BrigadierWrapper;
import me.i509.brigwrapper.arguments.DynamicStringArgument;
import me.i509.brigwrapper.source.CommandSource;

public class PluginCommand extends BrigadierCommand {
    
    private static PluginCommand cmd;

    static {
        cmd = new PluginCommand();
    }
    
    public static PluginCommand getCmd() {
        return cmd;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public LiteralCommandNode register(CommandDispatcher dispatcher) {
        return dispatcher.register((LiteralArgumentBuilder) literal("bwrapper")
                .then(literal("version") 
                        .executes(ctx -> { 
                            return executeVersion(ctx); 
                        }))
                .then(literal("getRegistered")
                        .then(DynamicStringArgument.dynamicString(() -> {
                            return Arrays.stream(Bukkit.getPluginManager().getPlugins()).map(plugin -> plugin.getName()).distinct().toArray(String[]::new);
                        }).build("plugin")
                                .executes(ctx -> {
                                return executeListByPlugin(ctx);
                        }))).executes(ctx -> { 
                    return execute(ctx); 
                })
            );
    }

    @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
    private int executeListByPlugin(CommandContext ctx) throws CommandSyntaxException {
        // TODO Auto-generated method stub
        
        CommandSource source = CommandSource.getSource(ctx);
        
        String pluginName = (String) ctx.getArgument("plugin", String.class);
        
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        
        if(plugin==null) {
            BrigadierWrapper.fail("Plugin " + pluginName + " not found");
        }
        // TODO fix mess
        List<BrigadierCommand> commandsRegistered = BrigadierWrapper.getAllCommands().get(plugin.getName()).asList().stream().filter(predicate -> {
            if(predicate.getLeft().equals(plugin.getName())) {
                return true;
            }
            return false;
        }).map(pair -> pair.getRight()).collect(Collectors.toList());
                
        
        if(commandsRegistered.isEmpty()) {
            // TODO none registered
            return 1;
        }
        
        
        
        return 0;
    }

    @SuppressWarnings("rawtypes")
    private int executeVersion(CommandContext ctx) {
        CommandSource source = CommandSource.getSource(ctx);
        
        
        
        return 0;
    }

    @SuppressWarnings("rawtypes")
    private int execute(CommandContext ctx) {
        CommandSource source = CommandSource.getSource(ctx);
        
        
        return 0;
    }
    
    @Override
    public Optional<String> fullDescription() {
        return Optional.of("Gets a list of all commands brigadier wrapper has registered per plugin.");
    }

    @Override
    public Optional<String> shortDesc() {
        return Optional.of("Lists all Brigadier Wrapper registered commands.");
    }

    @Override
    public Optional<String> usage() {
        return Optional.of("/bwrapper getRegistered <plugin> [command]\n/bwrapper version\n/bwrapper");
    }

    @Override
    public boolean silentPerms() {
        return false;
    }
}
