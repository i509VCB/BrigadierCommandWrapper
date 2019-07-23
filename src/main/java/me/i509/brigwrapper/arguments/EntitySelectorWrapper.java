package me.i509.brigwrapper.arguments;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.impl.v1_14_R1.EntitySelectorWrapper_1_14_R1;

// TODO version dependant selectors
public abstract class EntitySelectorWrapper {
    
    private static EntitySelectorWrapper INSTANCE;

    static {
        INSTANCE = new EntitySelectorWrapper_1_14_R1();
    }
    
    /**
     * Creates an entity selector argument of the following type.
     * @param type The EntitySelector type.
     * @return The EntitySelectorArgument.
     */
    public static ArgumentType<?> selector(@NotNull EntitySelectorType type) {
        return INSTANCE._getNMSType(type);
    }
    
    /**
     * Gets all players that were selected by this entity selector. This may return an empty Collection if the entity selector finds no applicable players.
     * <p>Note: you should only use this if you command explicitly asks for a MANY_PLAYERS entity selector otherwise this will throw a CommandSyntaxException.
     * @param cmdCtx The commandcontext from the executed command
     * @param str the name of required argument
     * @return A collection of all players that were selected.
     */
    public static List<Player> getPlayers(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
        return INSTANCE._getPlayers(cmdCtx, str);
    }

    /**
     * Gets the player that was selected by this entity selector. This may return a null value if the entity selector finds no applicable player.
     * <p>Note: you should only use this if you command explicitly asks for a ONE_PLAYER entity selector otherwise this will throw a CommandSyntaxException.
     * @param cmdCtx The commandcontext from the executed command
     * @param str the name of required argument
     * @return The selected player from this EntitySelector argument
     */
    public static Player getPlayer(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
        return INSTANCE._getPlayer(cmdCtx, str);
    }
    
    /**
     * Gets all entities that were selected by this entity selector. This may return an empty Collection if the entity selector finds no applicable entities.
     * <p>Note: you should only use this if you command explicitly asks for a MANY_ENTITIES entity selector otherwise this will throw a CommandSyntaxException.
     * @param cmdCtx The commandcontext from the executed command
     * @param str the name of required argument
     * @return A collection of all entities that were selected.
     */
    public static List<Entity> getEntities(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
        return INSTANCE._getEntities(cmdCtx, str);
    }
    
    /**
     * Gets the entity that was selected by this entity selector. This may return a null value if the entity selector finds no applicable entity.
     * <p>Note: you should only use this if you command explicitly asks for a ONE_ENTITY entity selector otherwise this will throw a CommandSyntaxException.
     * @param cmdCtx The commandcontext from the executed command
     * @param str the name of required argument
     * @return The selected entity from this EntitySelector argument
     */
    public static Entity getEntity(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
        return INSTANCE._getEntity(cmdCtx, str);
    }
    
    /* 
     * ==========================
     *      Abstract methods
     * ==========================
     */
    
    protected abstract ArgumentType<?> _getNMSType(EntitySelectorType type);
    
    protected abstract List<Entity> _getEntities(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException;

    protected abstract Entity _getEntity(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException;
    
    protected abstract List<Player> _getPlayers(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException;

    protected abstract Player _getPlayer(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException;
    
    public enum EntitySelectorType {
        /**Multiple entities, can include players*/MANY_ENTITIES, 
        /**Multiple players*/MANY_PLAYERS, 
        /**One entity only, can be a player*/ONE_ENTITY, 
        /**One player only*/ONE_PLAYER
    }
}
