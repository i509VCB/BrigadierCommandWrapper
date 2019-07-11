package me.i509.brigwrapper.impl.v1_14_R1.arg;

import org.bukkit.entity.EntityType;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.arguments.EntityTypeWrapper;
import net.minecraft.server.v1_14_R1.ArgumentEntitySummon;
import net.minecraft.server.v1_14_R1.CommandDispatcher;
import net.minecraft.server.v1_14_R1.CommandListenerWrapper;
import net.minecraft.server.v1_14_R1.CompletionProviders;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.IRegistry;

public class EntityTypeWrapper_1_14_R1 extends EntityTypeWrapper {

    @SuppressWarnings("rawtypes")
    @Override
    public RequiredArgumentBuilder _buildRequired(String s) {
        return CommandDispatcher.a(s, ArgumentEntitySummon.a()).suggests(CompletionProviders.d);
    }
    
    @SuppressWarnings({ "deprecation", "unchecked" })
    @Override
    protected EntityType _getType(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
        EntityTypes<?> type = IRegistry.ENTITY_TYPE.get(ArgumentEntitySummon.a((CommandContext<CommandListenerWrapper>) cmdCtx, str));
        
        String entity = type.e().substring(17); // Cause of entity.minecraft.
        
        return EntityType.fromName(entity);
    }
    
}
