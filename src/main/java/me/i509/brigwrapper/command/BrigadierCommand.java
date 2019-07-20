package me.i509.brigwrapper.command;

import java.util.Optional;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;

import me.i509.brigwrapper.BrigadierWrapper;

public abstract class BrigadierCommand {
    
    /**
     * Used to create the command
     * @param commandDispatcher The dispatcher the command will be registered inside of. Provided to allow creation of commmand using {@link CommandDispatcher#register(LiteralArgumentBuilder)}.
     * @return A literal command node which is the command structure.
     */
    @SuppressWarnings({ "rawtypes" })
    public abstract LiteralCommandNode register(CommandDispatcher commandDispatcher);
    
    public String noPermsMessage() {
        return BrigadierWrapper.NO_PERMS_DEFAULT;
    }
    
    public Optional<String> fullDescription() {
        return Optional.empty();
    }
    
    public Optional<String> shortDesc() {
        return Optional.empty();
    }
    
    public Optional<String> usage() {
        return Optional.empty();
    }
    
    /**
     * An integer argument
     * @return An {@link IntegerArgumentType}
     */
    public IntegerArgumentType integer() {
        return IntegerArgumentType.integer();
    }
    
    /**
     * An integer argument, with a limited range
     * @param min The minimum acceptable value.
     * @return An {@link IntegerArgumentType}
     */
    public IntegerArgumentType integerLimited(int min) {
        return IntegerArgumentType.integer(min);
    }
    
    /**
     * An integer argument, with a limited range
     * @param min The minimum acceptable value.
     * @param max The maximum acceptable value.
     * @return An {@link IntegerArgumentType}
     */
    public IntegerArgumentType integerLimited(int min, int max) {
        return IntegerArgumentType.integer(min, max);
    }
    
    /**
     * Creates a required argument
     * @param s The name of the argument
     * @param argument The {@link ArgumentType} of the argument
     * @return An {@link RequiredArgumentBuilder} from the ArgumentType
     */
    @SuppressWarnings("rawtypes")
    public RequiredArgumentBuilder required(String s, ArgumentType<?> argument) {
        return RequiredArgumentBuilder.argument(s, argument);
    }
    
    /**
     * A boolean argument.
     * @return An {@link BoolArgumentType}
     */
    public BoolArgumentType bool() {
        return BoolArgumentType.bool();
    }
    
    /**
     * A double argument/
     * @return A {@link DoubleArgumentType}
     */
    public DoubleArgumentType doubleArg() {
        return DoubleArgumentType.doubleArg();
    }
    
    /**
     * A double argument, with a limited range
     * @param min The minimum acceptable value.
     * @return A {@link DoubleArgumentType}
     */
    public DoubleArgumentType doubleArg(int min) {
        return DoubleArgumentType.doubleArg(min);
    }
    
    /**
     * A double argument, with a limited range
     * @param min The minimum acceptable value.
     * @param max The maximum acceptable value.
     * @return A {@link DoubleArgumentType}
     */
    public DoubleArgumentType doubleArg(double min, double max) {
        return DoubleArgumentType.doubleArg(min, max);
    }
    
    /**
     * A float argument.
     * @return A {@link FloatArgumentType}
     */
    public FloatArgumentType floatArg() {
        return FloatArgumentType.floatArg();
    }
    
    /**
     * A float argument, with a limited range
     * @param min The minimum acceptable value.
     * @return A {@link FloatArgumentType}
     */
    public FloatArgumentType floatArg(float min) {
        return FloatArgumentType.floatArg(min);
    }
    
    /**
     * A float argument, with a limited range
     * @param min The minimum acceptable value.
     * @param max The maximum acceptable value.
     * @return A {@link FloatArgumentType}
     */
    public FloatArgumentType floatArg(float min, float max) {
        return FloatArgumentType.floatArg(min, max);
    }
    
    /**
     * A long argument.
     * @return A {@link LongArgumentType}
     */
    public LongArgumentType longArg() {
        return LongArgumentType.longArg();
    }
    
    /**
     * A long argument, with a limited range
     * @param min The minimum acceptable value.
     * @return A {@link LongArgumentType}
     */
    public LongArgumentType longArg(long min) {
        return LongArgumentType.longArg(min);
    }
    
    /**
     * A long argument, with a limited range
     * @param min The minimum acceptable value.
     * @param max The maximum acceptable value.
     * @return A {@link LongArgumentType}
     */
    public LongArgumentType longArg(long min, long max) {
        return LongArgumentType.longArg(min, max);
    }
    
    /**
     * A String argument, this can be "quoted" or just a single word.
     * @return A {@link StringArgumentType} that is for a string
     */
    public StringArgumentType string() {
        return StringArgumentType.string();
    }
    
    /**
     * A version of the string argument that has no terminator, everything else behind it is part of the endless string.
     * @return @return A {@link StringArgumentType} that is for a greedy string;
     */
    public StringArgumentType greedy() {
        return StringArgumentType.greedyString();
    }
    
    /**
     * A String argument, this is just single word.
     * @return A {@link StringArgumentType} that is for a word
     */
    public StringArgumentType word() {
        return StringArgumentType.word();
    }
    
    /**
     * Represents a literal (not really an argument) but this an input must match this statement when parsed within the command or the command will fail.
     * @param string The literal statement
     * @return A configured {@link LiteralArgumentBuilder}
     */
    @SuppressWarnings({ "rawtypes" })
    public LiteralArgumentBuilder literal(String string) {
        return LiteralArgumentBuilder.literal(string);
    }

    public abstract boolean silentPerms();
}
