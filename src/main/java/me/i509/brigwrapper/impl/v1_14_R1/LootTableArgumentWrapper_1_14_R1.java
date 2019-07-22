package me.i509.brigwrapper.impl.v1_14_R1;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_14_R1.util.CraftNamespacedKey;
import org.bukkit.loot.LootTable;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.arguments.LootTableArgumentWrapper;
import net.minecraft.server.v1_14_R1.ArgumentMinecraftKeyRegistered;
import net.minecraft.server.v1_14_R1.CommandDispatcher;
import net.minecraft.server.v1_14_R1.CommandLoot;
import net.minecraft.server.v1_14_R1.MinecraftKey;

public class LootTableArgumentWrapper_1_14_R1 extends LootTableArgumentWrapper {

    @Override
    protected LootTable _getTable(CommandContext ctx, String argName) throws CommandSyntaxException {
        MinecraftKey minecraftKey = ArgumentMinecraftKeyRegistered.c(ctx, argName);
        
        NamespacedKey bukkitKey = CraftNamespacedKey.fromMinecraft(minecraftKey);
        
        LootTable table = Bukkit.getLootTable(bukkitKey);
        
        if(table==null) {
            throw LootTableArgumentWrapper.TABLE_NOT_FOUND.create(bukkitKey.getKey());
        }
        
        return table;
    }

    @Override
    protected RequiredArgumentBuilder _nmsArg(String argName) {
        return CommandDispatcher.a(argName, ArgumentMinecraftKeyRegistered.a()).suggests(CommandLoot.a);
    }

}
