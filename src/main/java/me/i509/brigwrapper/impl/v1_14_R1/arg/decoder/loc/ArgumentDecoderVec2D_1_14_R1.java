package me.i509.brigwrapper.impl.v1_14_R1.arg.decoder.loc;

import org.bukkit.Location;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.arguments.LocationArgumentWrapper.ArgumentDecoderVec2D;
import me.i509.brigwrapper.impl.v1_14_R1.arg.LocationArgumentWrapper_1_14_R1;
import me.i509.brigwrapper.source.CommandSource;
import net.minecraft.server.v1_14_R1.ArgumentVec2;
import net.minecraft.server.v1_14_R1.CommandListenerWrapper;

public class ArgumentDecoderVec2D_1_14_R1 extends ArgumentDecoderVec2D {

    @SuppressWarnings("unchecked")
    @Override
    protected Location _getLocation(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
        return LocationArgumentWrapper_1_14_R1.fromVec2F(CommandSource.getSource(cmdCtx).getWorld(), ArgumentVec2.a((CommandContext<CommandListenerWrapper>) cmdCtx.getSource(), str));
    }
}
