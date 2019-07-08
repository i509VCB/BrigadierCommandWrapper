package me.i509.brigwrapper.command;

import java.util.Arrays;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;

import me.i509.brigwrapper.BrigadierWrapper;
import me.i509.brigwrapper.DispatcherInstance;
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
    public LiteralCommandNode buildCommand() {
        return DispatcherInstance.getInstance().dispatcher().register((LiteralArgumentBuilder) literal("bwrapper")
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
        
        Plugin plugin = Bukkit.getPluginManager().getPlugin((String) ctx.getArgument("plugin", String.class)); // TODO add code to handle the Dynamic String arg
        
        
        
        // Bukkit.getHelpMap().getHelpTopics().stream().map(topic -> topic.getName()).forEach(name -> System.out.println(name)); TODO remove debug code
        
        if(plugin==null) {
            BrigadierWrapper.fail(() -> {
                return "Plugin " + plugin + " not found";
            });
        }
        // TODO fix mess
        Optional pluginList = Optional.empty();
                
        
        if(!pluginList.isPresent()) {
            
            return 1;
        }
        
        //Collection<String> values = pluginList.get().getValue();
        // TODO message success here
        
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
    public Optional<String> description() {
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
}
