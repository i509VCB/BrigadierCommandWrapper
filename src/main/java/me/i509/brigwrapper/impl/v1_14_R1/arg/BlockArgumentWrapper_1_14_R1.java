package me.i509.brigwrapper.impl.v1_14_R1.arg;

import com.mojang.brigadier.arguments.ArgumentType;

import me.i509.brigwrapper.arguments.BlockArgumentWrapper;
import net.minecraft.server.v1_14_R1.ArgumentTile;

public class BlockArgumentWrapper_1_14_R1 extends BlockArgumentWrapper {

    @Override
    public ArgumentType<?> getNMSType() {
        return ArgumentTile.a();
    }
    
    

}
