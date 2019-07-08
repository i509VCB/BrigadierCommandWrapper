package me.i509.brigwrapper.command;

import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;

import me.i509.brigwrapper.DispatcherInstance;
import me.i509.brigwrapper.arguments.LocationArgumentWrapper;
import me.i509.brigwrapper.source.CommandSource;
import me.i509.brigwrapper.util.CommandUtils;

public class LocationTestCommand extends BrigadierCommand {

    public static LocationTestCommand getCmd() {
        return new LocationTestCommand();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public LiteralCommandNode buildCommand() {
        return DispatcherInstance.getInstance().dispatcher().register((LiteralArgumentBuilder) literal("loctest") //Get dispatcher to create command
            .then(requiredArg("loc", LocationArgumentWrapper.block().getNMSType()) // Specify arguments
                .executes(ctx -> { // Code to execute when arguments are satisfied
                    return execute(ctx); // Execute stuff here, much nicer to use a new method
                })));
    }

    @SuppressWarnings("rawtypes")
    private int execute(CommandContext ctx) throws CommandSyntaxException {
        
        CommandSender sender = CommandSource.getSource(ctx).getSender();
        
        CommandUtils.notPlayerThenFail(sender, "Must be a player to use this command");
        
        Location loc = LocationArgumentWrapper.ArgumentDecoderBlockPosition.getLocation(ctx, "loc");
        
        sender.sendMessage(loc.toString());
        
        return 1;
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
