package me.i509.brigwrapper.impl.v1_14_R1;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.craftbukkit.v1_14_R1.command.BukkitCommandWrapper;
import org.bukkit.craftbukkit.v1_14_R1.command.VanillaCommandWrapper;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;

import me.i509.brigwrapper.BrigadierWrapper;
import me.i509.brigwrapper.DispatcherInstance;
import me.i509.brigwrapper.command.BrigadierWrappedCommand;
import net.minecraft.server.v1_14_R1.CommandListenerWrapper;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.MinecraftServer;

public class Dispatcher_1_14_R1 extends DispatcherInstance {
    @SuppressWarnings({ "deprecation" })
    @Override
    public CommandDispatcher<?> dispatcher() {
        return MinecraftServer.getServer().commandDispatcher.a();
    }

    @Override
    public ParseResults verifyDynamicOrError(String commandLine, Player p, String argName) {
        ParseResults<CommandListenerWrapper> parsed = MinecraftServer.getServer().commandDispatcher.a().parse(commandLine, (CommandListenerWrapper) p);
        
        MinecraftServer.getServer().commandDispatcher.a().getCompletionSuggestions(parsed).thenAccept((suggestions) -> {
            
            parsed.getReader().getCursor();
            
            parsed.getContext().getNodes().forEach(node -> {
                node.getNode().getName();
            });
            
            
            Object o = parsed.getContext().getArguments().get(argName).getResult();
            
            if(!(o instanceof String)) {
                // TODO error, not a dynamic string
            }
            
            
            
            //suggestions.getList().stream().filter(sug -> sug.getText()).findFirst();
            
        });
        
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ParseResults parse(String dispatcher, CommandSender sender) {
        
        CommandListenerWrapper iwrapper = VanillaCommandWrapper.getListener(sender);
        
        ParseResults parse = DispatcherInstance.getInstance().dispatcher().parse(dispatcher, iwrapper);
        
        return parse;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String name, String[] args) {
        CommandListenerWrapper iwrapper = VanillaCommandWrapper.getListener(sender);
        MinecraftServer.getServer().commandDispatcher.a(iwrapper, BrigadierWrappedCommand.toDispatcher(args, name), BrigadierWrappedCommand.toDispatcher(args, commandLabel)); // Execute
        return true;
    }
    
    @SuppressWarnings({ "unchecked" })
    public void syncCommands() throws ReflectiveOperationException {
        // Clear existing commands, Basically a copy of syncCommands in CraftServer with our logic added
        net.minecraft.server.v1_14_R1.CommandDispatcher nmsdispatcher = MinecraftServer.getServer().commandDispatcher = new net.minecraft.server.v1_14_R1.CommandDispatcher();

        // Register all commands, vanilla ones will be using the old dispatcher references
        
        //Field f = BrigadierWrapper.getField(SimpleCommandMap.class, "knownCommands");
        
        Field f = SimpleCommandMap.class.getDeclaredField("knownCommands");
        f.setAccessible(true);
        
        
        for (Map.Entry<String, Command> entry : ((Map<String, org.bukkit.command.Command>) f.get(BrigadierWrapper.getBukkitCommandMap())).entrySet()) {
            String label = entry.getKey();
            Command command = entry.getValue();

            if (command instanceof VanillaCommandWrapper) {
                LiteralCommandNode<CommandListenerWrapper> node = (LiteralCommandNode<CommandListenerWrapper>) ((VanillaCommandWrapper) command).vanillaCommand;
                if (!node.getLiteral().equals(label)) {
                    LiteralCommandNode<CommandListenerWrapper> clone = new LiteralCommandNode(label, node.getCommand(), node.getRequirement(), node.getRedirect(), node.getRedirectModifier(), node.isFork());

                    for (CommandNode<CommandListenerWrapper> child : node.getChildren()) {
                        clone.addChild(child);
                    }
                    node = clone;
                }

                nmsdispatcher.a().getRoot().addChild(node);
            } else if (command instanceof BrigadierWrappedCommand) { // Our logic starts here
                ((BrigadierWrappedCommand) command).getWrapped().register(nmsdispatcher.a()); // Our logic is here only
            } else {
                new BukkitCommandWrapper((CraftServer) Bukkit.getServer(), entry.getValue()).register(nmsdispatcher.a(), label);
            }
        }

        // Refresh commands
        for (EntityPlayer player : Bukkit.getOnlinePlayers().stream().map(player -> ((CraftPlayer) player).getHandle()).collect(Collectors.toList())) {
            nmsdispatcher.a(player); // Declare commands packet basically
        }
    }
    
    
}
