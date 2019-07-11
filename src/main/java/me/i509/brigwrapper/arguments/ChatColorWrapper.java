package me.i509.brigwrapper.arguments;

import org.bukkit.ChatColor;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.impl.v1_14_R1.arg.ChatColorWrapper_1_14_R1;

public abstract class ChatColorWrapper implements IArgumentWrapper {
    
    protected static final ChatColorWrapper INSTANCE;

    static {
        // TODO verison
        INSTANCE = new ChatColorWrapper_1_14_R1();
    }
    
    @Override
    public abstract ArgumentType<?> getNMSType();

    public static ArgumentType<?> color() {
        return INSTANCE.getNMSType();
    }
    
    public static ChatColor getType(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
        return INSTANCE._getColor(cmdCtx, str);
    }
    
    /* 
     * ==========================
     *      Abstract methods
     * ==========================
     */
    
    protected abstract ChatColor _getColor(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException;
}
