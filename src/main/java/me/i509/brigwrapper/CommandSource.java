package me.i509.brigwrapper;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.impl.v1_14_R1.CommandSource_1_14_R1;

public interface CommandSource {
    
    /**
     * Gets the sender of this command
     * @return
     */
    @NotNull
    public CommandSender getSender();
    
    /**
     * Gets the world of the command sender. 
     * This may not be applicable to all senders such as {@link ConsoleCommandSender}.
     * @return The world of this command sender.
     */
    @Nullable
    public World getWorld();
    
    /**
     * Gets the position of the command sender, including the world. 
     * This may not be applicable to all senders such as {@link ConsoleCommandSender}.
     * @return The location of this command sender.
     */
    @Nullable
    public Location getSenderPos();
    
    /**
     * Gets the player who sent this command. Otherwise ends the command.
     * @return The player who send this command.
     * @throws CommandSyntaxException if the sender is not a player and ends the command.
     */
    public Player getPlayerSender() throws CommandSyntaxException;
    
    /**
     * Gets the entity that sent this command. Otherwise ends the command.
     * @return The entity that send this command.
     * @throws CommandSyntaxException if the sender is not an entity and ends the command.
     */
    public Entity getEntitySender() throws CommandSyntaxException;

    /**
     * Gets the wrapped CommandSource from the command context
     * @param context The command context
     * @return The command source.
     */
    public static CommandSource getSource(CommandContext<?> context) {
        // TODO get server version
        return CommandSource_1_14_R1.fromObject(context.getSource());
    }
    
    /**
     * This should only be used for cases like requires(source) where the output argument is the actual source of the command.
     * @param csource The object representing the source of the command
     * @return The command source.
     */
    public static CommandSource getSource(Object csource) {
        // TODO Auto-generated method stub
        return CommandSource_1_14_R1.fromObject(csource);
    }
    
    public static Object getListener(CommandSender sender) {
        
        return CommandSource_1_14_R1.getListener(sender);
    }
    
}
