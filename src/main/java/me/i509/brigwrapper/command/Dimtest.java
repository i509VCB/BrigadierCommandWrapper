package me.i509.brigwrapper.command;

import java.util.Optional;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;

import me.i509.brigwrapper.arguments.WorldArgumentWrapper;

public class Dimtest extends BrigadierCommand {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public LiteralCommandNode register(CommandDispatcher dispatcher) {
        return dispatcher.register((LiteralArgumentBuilder) literal("dimens")
                .then(WorldArgumentWrapper.world("dim")
                        .executes(ctx -> {
                            return execute(ctx);
                        })));
    }

    private int execute(CommandContext<?> ctx) throws CommandSyntaxException {
        System.out.println(WorldArgumentWrapper.getWorld(ctx, "dim"));
        return 1;
    }

    @Override
    public Optional<String> fullDescription() {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<String> shortDesc() {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<String> usage() {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public boolean silentPerms() {
        return false;
    }

}
