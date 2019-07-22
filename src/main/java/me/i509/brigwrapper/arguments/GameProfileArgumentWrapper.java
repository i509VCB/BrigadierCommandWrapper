package me.i509.brigwrapper.arguments;

import java.util.List;

import org.bukkit.OfflinePlayer;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.impl.v1_14_R1.GameProfileArgumentWrapper_1_14_R1;

public abstract class GameProfileArgumentWrapper {
    
    static {
        INSTANCE = new GameProfileArgumentWrapper_1_14_R1();
        
    }
    
    private static final GameProfileArgumentWrapper INSTANCE;
    
    /**
     * Creates a GameProfileArgument with no suggestions.
     * @param argName
     * @return
     */
    public static RequiredArgumentBuilder profile(String argName) {
        return INSTANCE._profile(argName);
    }
    
    /**
     * Creates a GameProfileArgument with suggestions. These recommendations dynamically change as the server is ran.
     * @param argName
     * @param recomendations
     * @return
     */
    public static RequiredArgumentBuilder reccomendedProfile(String argName, List<String> recomendations) {
        return INSTANCE._recprofile(argName, recomendations);
    }
    
    /**
     * Gets the Player's Profile via an instance of {@link OfflinePlayer}
     * @param ctx
     * @param argName
     * @return
     * @throws CommandSyntaxException
     */
    public static OfflinePlayer getProfile(CommandContext ctx, String argName) throws CommandSyntaxException {
        return INSTANCE._getProfile(ctx, argName);
    }

    protected abstract OfflinePlayer _getProfile(CommandContext ctx, String argName) throws CommandSyntaxException;

    protected abstract RequiredArgumentBuilder _recprofile(String argName, List<String> reccomendations);

    protected abstract RequiredArgumentBuilder _profile(String argName);
}
