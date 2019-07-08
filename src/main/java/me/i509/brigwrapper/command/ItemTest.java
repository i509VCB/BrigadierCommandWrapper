package me.i509.brigwrapper.command;

import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;

import me.i509.brigwrapper.DispatcherInstance;
import me.i509.brigwrapper.arguments.ItemArgumentWrapper;
import me.i509.brigwrapper.arguments.ItemArgumentWrapper.ItemStackArgumentDecoder;
import me.i509.brigwrapper.source.CommandSource;
import me.i509.brigwrapper.util.CommandUtils;

public class ItemTest extends BrigadierCommand {
    
    public static ItemTest getCmd() {
        return new ItemTest();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public LiteralCommandNode buildCommand() {
        return DispatcherInstance.getInstance().dispatcher().register((LiteralArgumentBuilder) literal("ibt")
                .then(requiredArg("item", ItemArgumentWrapper.item().getNMSType())
                        .executes(ctx -> {
                            return execute(ctx);
                        })));
    }
    
    @SuppressWarnings({ "rawtypes" })
    private int execute(CommandContext ctx) throws CommandSyntaxException {
        
        Material m = ItemStackArgumentDecoder.getType(ctx, "item");
        
        CommandSource source = CommandSource.getSource(ctx);
        
        CommandUtils.notPlayerThenFail(source.getSender(), "Must be a player to use this command");
        
        Player p  = (Player) source.getSender();
        
        p.getInventory().addItem(new ItemStack(m));
        
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
