package me.i509.brigwrapper.impl.v1_14_R1.arg.decoder.loc;

import org.bukkit.Location;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.arguments.LocationArgumentWrapper;
import me.i509.brigwrapper.impl.v1_14_R1.arg.LocationArgumentWrapper_1_14_R1;
import me.i509.brigwrapper.source.CommandSource;
import net.minecraft.server.v1_14_R1.ArgumentVec3;

public class ArgumentDecoderBlockPosition_1_14_R1 extends LocationArgumentWrapper.ArgumentDecoderBlockPosition {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected Location _getLocation(CommandContext cmdCtx, String str) throws CommandSyntaxException {
        return LocationArgumentWrapper_1_14_R1.fromVec3(CommandSource.getSource(cmdCtx).getWorld(), ArgumentVec3.a(cmdCtx, str));
    }
    
}