package me.i509.brigwrapper.impl.v1_14_R1;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;

import me.i509.brigwrapper.arguments.WorldArgumentWrapper;
import net.minecraft.server.v1_14_R1.ArgumentDimension;
import net.minecraft.server.v1_14_R1.CommandListenerWrapper;
import net.minecraft.server.v1_14_R1.MinecraftServer;
import net.minecraft.server.v1_14_R1.WorldServer;

public class WorldArgumentWrapper_1_14_R1 extends WorldArgumentWrapper {

    @SuppressWarnings("unchecked")
    @Override
    protected World _dimension(CommandContext<?> ctx, String argName) {
        System.out.println(ArgumentDimension.a((CommandContext<CommandListenerWrapper>) ctx, argName).folder);
        System.out.println(ArgumentDimension.a((CommandContext<CommandListenerWrapper>) ctx, argName).toString());
        System.out.println(ArgumentDimension.a((CommandContext<CommandListenerWrapper>) ctx, argName).getType().getDimensionID());
        System.out.println(ArgumentDimension.a((CommandContext<CommandListenerWrapper>) ctx, argName));
        // TODO testing
        
        boolean used;
        
        for (WorldServer server : MinecraftServer.getServer().getWorlds()) {
            used = server.getWorldProvider().getDimensionManager().getDimensionID() == ArgumentDimension.a((CommandContext<CommandListenerWrapper>) ctx, argName).getType().getDimensionID();
            if (used) {
                return server.getWorld();
            }
        }
        // This will never happen as if the world is not within DimensionManager then it should never execute. If it does then something is seriously wrong.
        return null;
    }

    @Override
    protected ArgumentType<?> dimension() {
        return ArgumentDimension.a();
    }

}
