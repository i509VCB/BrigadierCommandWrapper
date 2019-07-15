package me.i509.brigwrapper.command;

import java.util.Optional;

import org.bukkit.command.ConsoleCommandSender;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;

import me.i509.brigwrapper.DispatcherInstance;
import me.i509.brigwrapper.arguments.EntitySelectorWrapper;
import me.i509.brigwrapper.dynamic.DynamicErrorProvider;
import me.i509.brigwrapper.selectors.EntitySelectorType;
import me.i509.brigwrapper.source.CommandSource;
import me.i509.brigwrapper.util.CommandUtils;

public class DynamicErrorTest extends BrigadierCommand {

    @SuppressWarnings("unchecked")
    @Override
    public LiteralCommandNode buildCommand() {
        // TODO Auto-generated method stub
        return DispatcherInstance.getInstance().dispatcher().register((LiteralArgumentBuilder) literal("packet_test")
                .then(required("player", EntitySelectorWrapper.selector(EntitySelectorType.ONE_PLAYER))
                        .executes(ctx -> {
                            return execute(ctx);
                        })));
    }

    private int execute(CommandContext<?> ctx) throws CommandSyntaxException {
        CommandSource source = CommandSource.getSource(ctx);
        
        if(!(source.getSender() instanceof ConsoleCommandSender)) {
            throw new SimpleCommandExceptionType(new LiteralMessage("Must be console to use this command")).create();
        }
        
        DynamicErrorProvider.sendDynamicMessage(EntitySelectorWrapper.getPlayer(ctx, "player"), "Test exception");
        
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
