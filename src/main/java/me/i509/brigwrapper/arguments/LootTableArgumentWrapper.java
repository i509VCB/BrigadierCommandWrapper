package me.i509.brigwrapper.arguments;

import org.bukkit.loot.LootTable;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;

import me.i509.brigwrapper.impl.v1_14_R1.LootTableArgumentWrapper_1_14_R1;

public abstract class LootTableArgumentWrapper {
    
    public static final DynamicCommandExceptionType TABLE_NOT_FOUND = new DynamicCommandExceptionType(table -> {
        return new LiteralMessage("LootTable: " + table + " not found");
    });
    
    static {
        INSTANCE = new LootTableArgumentWrapper_1_14_R1();
    }
    
    private static final LootTableArgumentWrapper INSTANCE;

    public static RequiredArgumentBuilder<?,?> loottable(String argName){
        return INSTANCE._nmsArg(argName);
    }
    
    public static LootTable getTable(CommandContext ctx, String argName) throws CommandSyntaxException {
        return INSTANCE._getTable(ctx, argName);
    }

    protected abstract LootTable _getTable(CommandContext ctx, String argName) throws CommandSyntaxException;

    protected abstract RequiredArgumentBuilder<?,?> _nmsArg(String argName);
}
