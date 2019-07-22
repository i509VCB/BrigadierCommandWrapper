package me.i509.brigwrapper.impl.v1_14_R1;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.arguments.GameProfileArgumentWrapper;
import net.minecraft.server.v1_14_R1.ArgumentProfile;
import net.minecraft.server.v1_14_R1.ICompletionProvider;

public class GameProfileArgumentWrapper_1_14_R1 extends GameProfileArgumentWrapper {

    @Override
    protected RequiredArgumentBuilder _recprofile(String argName, List<String> reccomendations) {
        return RequiredArgumentBuilder.argument(argName, ArgumentProfile.a()).suggests((ctx, suggestionsbuilder) -> {
            return ICompletionProvider.a(reccomendations.stream().toArray(String[]::new), suggestionsbuilder);
        });
    }

    @Override
    protected RequiredArgumentBuilder _profile(String argName) {
        return RequiredArgumentBuilder.argument(argName, ArgumentProfile.a());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OfflinePlayer _getProfile(CommandContext ctx, String argName) throws CommandSyntaxException {
        ArgumentProfile.a(ctx, argName);
        
        int i = 0;
        Iterator<GameProfile> iterator = ArgumentProfile.a(ctx, argName).iterator();

        if(iterator.hasNext()) {
            
            GameProfile profile = iterator.next();
            if(Bukkit.getOfflinePlayer(profile.getId())==null) {
                throw ArgumentProfile.a.create();    
            }
            
            return Bukkit.getOfflinePlayer(profile.getId());
        }
        
        throw ArgumentProfile.a.create();   
    }
    
    
    
}
