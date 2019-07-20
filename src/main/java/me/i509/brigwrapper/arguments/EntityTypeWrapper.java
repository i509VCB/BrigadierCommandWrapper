package me.i509.brigwrapper.arguments;

import org.bukkit.entity.EntityType;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.impl.v1_14_R1.EntityTypeWrapper_1_14_R1;

public abstract class EntityTypeWrapper {
    
    private static final EntityTypeWrapper INSTANCE;

    static {
        //TODO version
        INSTANCE = new EntityTypeWrapper_1_14_R1();
    }
    
    @SuppressWarnings("rawtypes")
    public static RequiredArgumentBuilder buildRequired(String s) {
        return INSTANCE._buildRequired(s);
    }
    
    public static EntityType getType(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
        return INSTANCE._getType(cmdCtx, str);
    }
    
    /* 
     * ==========================
     *      Abstract methods
     * ==========================
     */
    
    protected abstract EntityType _getType(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException;
    
    @SuppressWarnings("rawtypes")
    public abstract RequiredArgumentBuilder _buildRequired(String s);
    

}
