package me.i509.brigwrapper.command;

import java.util.Optional;

import org.bukkit.entity.Player;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;

import me.i509.brigwrapper.DispatcherInstance;
import me.i509.brigwrapper.arguments.EntitySelectorWrapper;
import me.i509.brigwrapper.source.CommandSource;

public class SelectorTestCommand extends BrigadierCommand {
    
    private static SelectorTestCommand cmd;

    static {
        cmd = new SelectorTestCommand();
    }
    
    public static SelectorTestCommand getCmd() {
        return cmd;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public LiteralCommandNode buildCommand() {
        return DispatcherInstance.getInstance().dispatcher().register((LiteralArgumentBuilder) literal("selectortest") //Get dispatcher to create command
                .then(requiredArg("player", EntitySelectorWrapper.onePlayer().getNMSType()) // Specify arguments
                        .executes(ctx -> { // Code to execute when arguments are satisfied
                            return execute(ctx); // Execute stuff here, much nicer to use a new method
                        })));
    }

    private int execute(CommandContext<?> ctx) throws CommandSyntaxException {
        CommandSource source = CommandSource.getSource(ctx);
        
        Player player = EntitySelectorWrapper.ArgumentDecoder.getPlayer(ctx, "player");
        
        source.getSender().sendMessage("Selected player:" + player.getName() + " - " +player.getUniqueId().toString());
        
        player.sendMessage("A command has selected you");
        
        return 0;
    }
    
    @Override
    public Optional<String> description() {
        return Optional.empty();
    }

    @Override
    public Optional<String> shortDesc() {
        return Optional.empty();
    }

    @Override
    public Optional<String> usage() {
        return Optional.empty();
    }
}
