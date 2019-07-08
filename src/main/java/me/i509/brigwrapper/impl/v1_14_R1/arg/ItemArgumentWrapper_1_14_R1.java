package me.i509.brigwrapper.impl.v1_14_R1.arg;

import com.mojang.brigadier.arguments.ArgumentType;

import me.i509.brigwrapper.arguments.ItemArgumentWrapper;
import net.minecraft.server.v1_14_R1.ArgumentItemStack;

public class ItemArgumentWrapper_1_14_R1 extends ItemArgumentWrapper {

    @Override
    public ArgumentType<?> getNMSType() {
        return ArgumentItemStack.a();
    }

}
