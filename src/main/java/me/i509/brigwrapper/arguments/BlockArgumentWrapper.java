package me.i509.brigwrapper.arguments;

import org.bukkit.Material;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.impl.v1_14_R1.BlockArgumentWrapper_1_14_R1;

public abstract class BlockArgumentWrapper implements IArgumentWrapper {
    
    static {
        // TODO version stuff
        INSTANCE = new BlockArgumentWrapper_1_14_R1();
    }
    
    private static final BlockArgumentWrapper INSTANCE;

    @Override
    public abstract ArgumentType<?> getNMSType();

    public static ArgumentType<?> block() {
        return INSTANCE.getNMSType();
    }

    public static Material getType(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
        return INSTANCE._getType(cmdCtx, str);
    }
    
    /* 
     * ==========================
     *      Abstract methods
     * ==========================
     */

    protected abstract Material _getType(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException;
}
