package me.i509.brigwrapper.impl.v1_14_R1;

import org.bukkit.entity.Player;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;

import me.i509.brigwrapper.DispatcherInstance;
import net.minecraft.server.v1_14_R1.CommandListenerWrapper;
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
}
