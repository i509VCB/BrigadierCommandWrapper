package me.i509.brigwrapper.impl.v1_14_R1.arg.decoder;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.arguments.ItemArgumentWrapper.ItemStackArgumentDecoder;
import net.minecraft.server.v1_14_R1.ArgumentItemStack;

public class ItemStackArgumentDecoder_1_14_R1 extends ItemStackArgumentDecoder {

    @Override
    protected Material _getType(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
        
        return CraftItemStack.asNewCraftStack(ArgumentItemStack.a(cmdCtx, str).a()).getType();
    }

}
