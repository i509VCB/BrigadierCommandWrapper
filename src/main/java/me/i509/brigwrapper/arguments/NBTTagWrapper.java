package me.i509.brigwrapper.arguments;

import org.bukkit.Bukkit;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import me.i509.brigwrapper.MissingDependancyException;
import me.i509.brigwrapper.impl.v1_14_R1.NBTTagWrapper_1_14_R1;

public abstract class NBTTagWrapper {
    
    static {
        INSTANCE = new NBTTagWrapper_1_14_R1();
    }
    
    private static NBTTagWrapper INSTANCE;

    public static ArgumentType<?> nbtTag() {
        return INSTANCE._nbtTag();
    }
    
    public static NBTContainer getTag(CommandContext<?> ctx, String str) {
        if(Bukkit.getServer().getPluginManager().getPlugin("NBTAPI")==null) { // NBT API is required
            throw new MissingDependancyException("NBTAPI plugin is missing for NBTTagWrapper. You can still use NBTTagWrapper#getTagAsString but you will have to handle the nbt tag logic yourself.");
        }
        return INSTANCE._nbt(ctx, str);
    }
    
    public static String getTagAsString(CommandContext<?> ctx, String str) {
        return INSTANCE._nbt_string(ctx, str);
    }
    
    /* 
     * ==========================
     *      Abstract methods
     * ==========================
     */

    protected abstract ArgumentType<?> _nbtTag();
    
    protected abstract NBTContainer _nbt(CommandContext<?> ctx, String str);
    
    protected abstract String _nbt_string(CommandContext<?> ctx, String str);
}
