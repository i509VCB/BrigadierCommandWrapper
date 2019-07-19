package me.i509.brigwrapper.impl.v1_14_R1;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import me.i509.brigwrapper.arguments.NBTTagWrapper;
import net.minecraft.server.v1_14_R1.ArgumentNBTTag;

public class NBTTagWrapper_1_14_R1 extends NBTTagWrapper {

    @Override
    protected ArgumentType<?> _nbtTag() {
        return ArgumentNBTTag.a();
    }

    @Override
    protected NBTContainer _nbt(CommandContext<?> ctx, String str) {
        return new NBTContainer(ArgumentNBTTag.a(ctx, str));
    }

    @Override
    protected String _nbt_string(CommandContext<?> ctx, String str) {
        return ArgumentNBTTag.a(ctx, str).asString();
    }
}
