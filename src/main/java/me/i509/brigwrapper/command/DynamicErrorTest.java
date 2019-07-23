package me.i509.brigwrapper.command;

import java.util.Optional;

import org.bukkit.command.ConsoleCommandSender;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;

import me.i509.brigwrapper.CommandSource;
import me.i509.brigwrapper.DynamicErrorProvider;
import me.i509.brigwrapper.arguments.EntitySelectorWrapper;
import me.i509.brigwrapper.arguments.EntitySelectorWrapper.EntitySelectorType;

public class DynamicErrorTest extends BrigadierCommand {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public LiteralCommandNode register(CommandDispatcher dispatcher) {
        return dispatcher.register((LiteralArgumentBuilder) literal("packet_test")
                .then(required("player", EntitySelectorWrapper.selector(EntitySelectorType.ONE_PLAYER))
                        .executes(ctx -> {
                            return execute(ctx);
                        })));
    }

    private int execute(CommandContext<?> ctx) throws CommandSyntaxException {
        CommandSource source = CommandSource.getSource(ctx);
        
        if(!(source.getSender() instanceof ConsoleCommandSender)) {
            throw new SimpleCommandExceptionType(new LiteralMessage("Must be console to use this command")).create(); // TODO replace with fail call or add #ifNotConsoleFail(CommandSender)
        }
        
        DynamicErrorProvider.sendDynamicMessage(EntitySelectorWrapper.getPlayer(ctx, "player"), "Test exception");
        
        return 1;
    }

    @Override
    public Optional<String> fullDescription() {

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

    @Override
    public boolean silentPerms() {
        return false;
    }

}
