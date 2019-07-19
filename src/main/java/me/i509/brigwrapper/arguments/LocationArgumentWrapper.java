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
    
    /**
     * Creates a location argument from the following LocationType.
     * @param type The location type to use
     * @return a new location argument.
     */
    public static ArgumentType<?> location(LocationType type) {
        return INSTANCE._getNMSType(type);
    }
    
    /**
     * Gets the location from the LocationArgument
     * @param ctx The CommandContext of the command
     * @param str The name of the argument from {@link RequiredArgumentBuilder}
     * @param type The type of location
     * @return A location within the world this command was executed in. If this is a 2D based location than the Y height will be 0
     * @throws CommandSyntaxException If the argument is not a location.
     */
    public static Location getLocation(CommandContext<?> ctx, String str, LocationType type) throws CommandSyntaxException {
        return INSTANCE._getFromContext(ctx, str, type);
    }

    protected abstract ArgumentType<?> _getNMSType(LocationType type);

    /* 
     * ==========================
     *      Abstract methods
     * ==========================
     */
    
    protected abstract Location _getFromContext(CommandContext cmdCtx, String str, LocationType type) throws CommandSyntaxException;

    protected abstract Location _getLocationVec3(CommandContext cmdCtx, String str) throws CommandSyntaxException;
    
    protected abstract Location _getLocationVec2(CommandContext cmdCtx, String str) throws CommandSyntaxException;
    
    protected abstract Location _getLocationBlockPos(CommandContext cmdCtx, String str) throws CommandSyntaxException;
    
    protected abstract Location _getLocationBlockPos2D(CommandContext cmdCtx, String str) throws CommandSyntaxException;
}
