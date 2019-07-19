package me.i509.brigwrapper.arguments;

import org.bukkit.Material;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.impl.v1_14_R1.ItemArgumentWrapper_1_14_R1;

public abstract class ItemArgumentWrapper implements IArgumentWrapper {
    
    private static final ItemArgumentWrapper INSTANCE;

    static {
        INSTANCE = new ItemArgumentWrapper_1_14_R1();
    }
    
    /**
     * Creates an Item argument
     * @return a new Item argument.
     */
    public static ArgumentType<?> item() {
        return INSTANCE.getNMSType();
    }
    
    /**
     * Gets the type of item
     * @param ctx The {@link CommandContext}
     * @param str The name of the argument from {@link RequiredArgumentBuilder}
     * @return The Material of the item in the argument
     * @throws CommandSyntaxException
     */
    public static Material getType(CommandContext<?> ctx, String str) throws CommandSyntaxException {
        return INSTANCE._getType(ctx, str);
    }
    
    /* 
     * ==========================
     *      Abstract methods
     * ==========================
     */
    
    @Override
    public abstract ArgumentType<?> getNMSType();
    
    protected abstract Material _getType(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException;
}
