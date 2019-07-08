package me.i509.brigwrapper.impl.v1_14_R1.arg.decoder.loc;

import org.bukkit.Location;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.arguments.LocationArgumentWrapper.ArgumentDecoderBlockPosition_2D;
import me.i509.brigwrapper.impl.v1_14_R1.arg.LocationArgumentWrapper_1_14_R1;
import me.i509.brigwrapper.source.CommandSource;
import net.minecraft.server.v1_14_R1.ArgumentVec2I;
import net.minecraft.server.v1_14_R1.BlockPosition2D;
import net.minecraft.server.v1_14_R1.CommandListenerWrapper;

public class ArgumentDecoderBlockPosition2D_1_14_R1 extends ArgumentDecoderBlockPosition_2D {

    @SuppressWarnings("unchecked")
    @Override
    protected Location _getLocation(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
        BlockPosition2D pos2 = ArgumentVec2I.a((CommandContext<CommandListenerWrapper>) cmdCtx.getSource(), str);
        return LocationArgumentWrapper_1_14_R1.fromBlockPos2D(CommandSource.getSource(cmdCtx).getWorld(), pos2);
    }
}
