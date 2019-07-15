package me.i509.brigwrapper.arguments;

import org.bukkit.Location;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.impl.v1_14_R1.LocationArgumentWrapper_1_14_R1;
import me.i509.brigwrapper.selectors.LocationType;

public abstract class LocationArgumentWrapper {
    
    private static LocationArgumentWrapper INSTANCE;

    static {
        INSTANCE = new LocationArgumentWrapper_1_14_R1();
    }
    
    protected LocationType argType;
    
    public static ArgumentType<?> location(LocationType type) {
        return INSTANCE.getNMSType(type);
    }
    
    protected abstract ArgumentType<?> getNMSType(LocationType type);

    public static Location getLocation(CommandContext<?> cmdCtx, String str, LocationType type) throws CommandSyntaxException {
        return INSTANCE.getFromContext(cmdCtx, str, type);
    }

    /* 
     * ==========================
     *      Abstract methods
     * ==========================
     */
    
    protected abstract Location getFromContext(CommandContext cmdCtx, String str, LocationType type) throws CommandSyntaxException;

    protected abstract Location _getLocationVec3(CommandContext cmdCtx, String str) throws CommandSyntaxException;
    
    protected abstract Location _getLocationVec2(CommandContext cmdCtx, String str) throws CommandSyntaxException;
    
    protected abstract Location _getLocationBlockPos(CommandContext cmdCtx, String str) throws CommandSyntaxException;
    
    protected abstract Location _getLocationBlockPos2D(CommandContext cmdCtx, String str) throws CommandSyntaxException;
}
