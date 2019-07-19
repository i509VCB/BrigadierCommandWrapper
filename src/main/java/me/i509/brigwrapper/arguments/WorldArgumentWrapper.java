package me.i509.brigwrapper.arguments;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import me.i509.brigwrapper.BrigadierWrapper;
import me.i509.brigwrapper.impl.v1_14_R1.WorldArgumentWrapper_1_14_R1;

/**
 * Represents an argument that takes a world as the argument. 
 * <p>Due to how bukkit is implemented and the differences with normal game, there are two possible wrapped arguments. 
 * One is the normal Dimension Argument provided by default game which is converted into a {@link World}.
 * <p>Because Bukkit allows for multiple worlds alongside the default overworld, nether and the end, there is a second implementation that is based off a {@link DynamicStringArgument} and then error handling is done by the server and can throw an exception if it fails.
 * <p>Though you could use the normal Dimension Argument in a case where Bukkit has added multiple worlds on top of the normal implementation, the client would not know of this and would think you are specifying an invalid dimension during the parse process and throw a client side exception, but if sent to the server it would execute perfectly fine most likely confusing your users. 
 * <p>This can't be overridden without changing both the client and server so we provide the alternative which doesn't have the fancy command error popup but also works in the exact same way.
 * @author i509VCB
 */
public abstract class WorldArgumentWrapper {
    
    static {
        INSTANCE = new WorldArgumentWrapper_1_14_R1();
    }
    
    public static final SimpleCommandExceptionType NULL_WORLD_NAME = new SimpleCommandExceptionType(new LiteralMessage("Tried to find a world with null name, this is an issue with the implementation of this command and should be reported to the plugin author using this library"));
    
    public static final DynamicCommandExceptionType WORLD_NOT_FOUND = new DynamicCommandExceptionType(world -> {
        return new LiteralMessage("Could not find the world named: " + world);
    });
    
    public static final WorldArgumentWrapper INSTANCE;
    
    /**
     * Creates a {@link RequiredArgumentBuilder} of the WorldArgument
     * @param argName The name of the argument
     * @return A {@link RequiredArgumentBuilder} with the World Argument inside.
     */
    public static RequiredArgumentBuilder<?, ?> world(String argName) {
        if(BrigadierWrapper.useFallbackDimensionArgument()) {
            return DynamicStringArgument.dynamicString(() -> Bukkit.getWorlds().stream().map(world -> world.getName()).toArray(String[]::new)).build(argName);
        } else {
            return RequiredArgumentBuilder.argument(argName, INSTANCE.dimension());
        }
    }
    
    /**
     * Gets the World from an argument in the command
     * @param ctx The CommandContext
     * @param argName The name of the argument
     * @return The world specifyed in argument
     * @throws CommandSyntaxException If the world does not exist or is null.
     */
    public static World getWorld(CommandContext<?> ctx, String argName) throws CommandSyntaxException {
        if(BrigadierWrapper.useFallbackDimensionArgument()) {
            return INSTANCE.dynamicStringToWorld(ctx, argName);
        } else {
            return INSTANCE.getDimension(ctx, argName);
        }
    }

    private World getDimension(CommandContext<?> ctx, String argName) {
        
        return INSTANCE._dimension(ctx, argName);
    }
    
    
    private World dynamicStringToWorld(CommandContext<?> ctx, String argName) throws CommandSyntaxException {
        
        String worldName = ctx.getArgument(argName, String.class);
        
        if(worldName == null) {
            throw WorldArgumentWrapper.NULL_WORLD_NAME.create(); // Null safety
        }
        
        if(Bukkit.getWorld(worldName) == null) {
            throw WorldArgumentWrapper.WORLD_NOT_FOUND.create(worldName); // Doesn't exist
        }

        return Bukkit.getWorld(worldName);
    }

    /* 
     * ==========================
     *      Abstract methods
     * ==========================
     */
    
    protected abstract World _dimension(CommandContext<?> ctx, String argName);
    
    protected abstract ArgumentType<?> dimension();
    
    
}
