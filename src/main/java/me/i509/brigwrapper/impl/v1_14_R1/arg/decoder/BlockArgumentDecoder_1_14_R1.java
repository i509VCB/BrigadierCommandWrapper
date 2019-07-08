package me.i509.brigwrapper.impl.v1_14_R1.arg.decoder;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.arguments.BlockArgumentWrapper.BlockArgumentDecoder;
import net.minecraft.server.v1_14_R1.ArgumentTile;
import net.minecraft.server.v1_14_R1.CommandListenerWrapper;

public class BlockArgumentDecoder_1_14_R1 extends BlockArgumentDecoder {

    @SuppressWarnings("unchecked")
    @Override
    protected Material _getType(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
        return CraftItemStack.asNewCraftStack(ArgumentTile.a((CommandContext<CommandListenerWrapper>) cmdCtx, str).a().getBlock().getItem()).getType();
    }

}
