package me.i509.brigwrapper.command;

import java.util.Optional;

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

public abstract class BrigadierCommand {
    
    @SuppressWarnings({ "rawtypes" })
    public abstract LiteralCommandNode buildCommand();
    
    public abstract Optional<String> description();
    
    public abstract Optional<String> shortDesc();
    
    public abstract Optional<String> usage();

    public IntegerArgumentType integer() {
        return IntegerArgumentType.integer();
    }
    
    public IntegerArgumentType integerLimited(int min) {
        return IntegerArgumentType.integer(min);
    }
    
    public IntegerArgumentType integerLimited(int min, int max) {
        return IntegerArgumentType.integer(min, max);
    }
    
    @SuppressWarnings("rawtypes")
    public RequiredArgumentBuilder required(String s, ArgumentType<?> argument) {
        return RequiredArgumentBuilder.argument(s, argument);
    }

    public BoolArgumentType bool() {
        return BoolArgumentType.bool();
    }
    
    public DoubleArgumentType doubleArg() {
        return DoubleArgumentType.doubleArg();
    }
    
    public DoubleArgumentType doublArg(int min) {
        return DoubleArgumentType.doubleArg(min);
    }
    
    public DoubleArgumentType doubleArg(int min, int max) {
        return DoubleArgumentType.doubleArg(min, max);
    }
    
    public FloatArgumentType floatArg() {
        return FloatArgumentType.floatArg();
    }
    
    public FloatArgumentType floatArg(int min) {
        return FloatArgumentType.floatArg(min);
    }
    
    public FloatArgumentType floatArg(int min, int max) {
        return FloatArgumentType.floatArg(min, max);
    }
    
    public LongArgumentType longArg() {
        return LongArgumentType.longArg();
    }
    
    public LongArgumentType longArg(int min) {
        return LongArgumentType.longArg(min);
    }
    
    public LongArgumentType longArg(int min, int max) {
        return LongArgumentType.longArg(min, max);
    }
    
    public StringArgumentType string() {
        return StringArgumentType.string();
    }
    
    public StringArgumentType greedy() {
        return StringArgumentType.greedyString();
    }
    
    public StringArgumentType word() {
        return StringArgumentType.word();
    }
    
    @SuppressWarnings({ "rawtypes" })
    public LiteralArgumentBuilder literal(String string) {
        return LiteralArgumentBuilder.literal(string);
    }
}
