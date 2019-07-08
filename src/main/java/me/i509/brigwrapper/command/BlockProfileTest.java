package me.i509.brigwrapper.command;

import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;

import me.i509.brigwrapper.DispatcherInstance;
import me.i509.brigwrapper.arguments.ChatColorWrapper;
import me.i509.brigwrapper.arguments.ChatColorWrapper.ChatColorArgumentDecoder;
import me.i509.brigwrapper.arguments.BlockArgumentWrapper;
import me.i509.brigwrapper.arguments.LocationArgumentWrapper;
import me.i509.brigwrapper.source.CommandSource;
import me.i509.brigwrapper.util.CommandUtils;

public class BlockProfileTest extends BrigadierCommand {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public LiteralCommandNode buildCommand() {
        return DispatcherInstance.getInstance().dispatcher().register((LiteralArgumentBuilder) literal("blockp")
                .then(requiredArg("loc", LocationArgumentWrapper.block().getNMSType())
                        .then(requiredArg("blocktype", BlockArgumentWrapper.block().getNMSType())
                                .then(requiredArg("color", ChatColorWrapper.color().getNMSType()).executes(ctx -> {
                                    return execute(ctx);
                                })))));
    }

    private int execute(CommandContext<?> ctx) throws CommandSyntaxException {
        CommandSource source = CommandSource.getSource(ctx);
        
        CommandSender sender = source.getSender();
        
        CommandUtils.notPlayerThenFail(source.getSender(), "Must be a player to use this command");
        
        Location loc = LocationArgumentWrapper.ArgumentDecoderBlockPosition.getLocation(ctx, "loc");
        
        Material m = BlockArgumentWrapper.BlockArgumentDecoder.getType(ctx, "blocktype");
        
        ChatColor color = ChatColorArgumentDecoder.getType(ctx, "color");
        
        if(source.getWorld().getBlockAt(loc).getType().equals(m)) {
            sender.sendMessage(color + "Is same: true");
            return 1;
        }
        
        sender.sendMessage(color + "Is same: false");
        
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
