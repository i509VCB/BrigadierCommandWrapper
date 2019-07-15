package me.i509.brigwrapper.impl.v1_14_R1;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.arguments.EntitySelectorWrapper;
import me.i509.brigwrapper.selectors.EntitySelectorType;
import net.minecraft.server.v1_14_R1.ArgumentEntity;
import net.minecraft.server.v1_14_R1.Entity;

public class EntitySelectorWrapper_1_14_R1 extends EntitySelectorWrapper {
    /*
     * a = true false -> only one ENTITY is allowed
     * b = false false -> multiple entities
     * c = true true -> only one PLAYER is allowed
     * d = false true -> multiple players
     */ 
    protected ArgumentType<?> _getNMSType(EntitySelectorType selectorType) {
        switch(selectorType) {
        case MANY_ENTITIES:
            return ArgumentEntity.multipleEntities(); // b
        case MANY_PLAYERS:
            return ArgumentEntity.d(); // d
        case ONE_ENTITY:
            return ArgumentEntity.a(); // a
        case ONE_PLAYER:
            return ArgumentEntity.c(); // c
        default:
            throw new IllegalArgumentException("Cannot create EntitySelectorWrapper with a null selector type");
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected List<org.bukkit.entity.Entity> _getEntities(CommandContext cmdCtx, String str) throws CommandSyntaxException {
        return (List<org.bukkit.entity.Entity>) ArgumentEntity.c(cmdCtx, str).stream().map(entity -> (org.bukkit.entity.Entity) ((Entity) entity).getBukkitEntity()).collect(Collectors.toList()); // parseEntities
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected org.bukkit.entity.Entity _getEntity(CommandContext cmdCtx, String str) throws CommandSyntaxException {
        return (org.bukkit.entity.Entity) ArgumentEntity.a(cmdCtx, str).getBukkitEntity(); // parseEntity
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected List<Player> _getPlayers(CommandContext cmdCtx, String str) throws CommandSyntaxException {
        return (List<Player>) ArgumentEntity.d(cmdCtx, str).stream().map(player -> (Player) ((Entity) player).getBukkitEntity()).collect(Collectors.toList()); // parsePlayers
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected Player _getPlayer(CommandContext cmdCtx, String str) throws CommandSyntaxException {
        return (Player) ArgumentEntity.e(cmdCtx, str).getBukkitEntity(); // parsePlayer
    }
}
