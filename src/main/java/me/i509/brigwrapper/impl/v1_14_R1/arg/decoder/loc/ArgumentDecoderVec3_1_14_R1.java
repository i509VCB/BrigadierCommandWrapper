package me.i509.brigwrapper.impl.v1_14_R1.arg.decoder.loc;

import org.bukkit.Location;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.arguments.LocationArgumentWrapper;
import me.i509.brigwrapper.impl.v1_14_R1.arg.LocationArgumentWrapper_1_14_R1;
import me.i509.brigwrapper.source.CommandSource;
import net.minecraft.server.v1_14_R1.ArgumentPosition;
import net.minecraft.server.v1_14_R1.BlockPosition;

public class ArgumentDecoderVec3_1_14_R1 extends LocationArgumentWrapper.ArgumentDecoderVec3 {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected Location _getLocation(CommandContext cmdCtx, String str) throws CommandSyntaxException {
        BlockPosition blockPos = ArgumentPosition.a(cmdCtx, str);
        return LocationArgumentWrapper_1_14_R1.fromBlockPos(CommandSource.getSource(cmdCtx).getWorld(), blockPos);
    }
    
}