package me.i509.brigwrapper.impl.v1_14_R1.arg;

import com.mojang.brigadier.arguments.ArgumentType;

import me.i509.brigwrapper.arguments.ChatColorWrapper;
import net.minecraft.server.v1_14_R1.ArgumentChatFormat;

public class ChatColorWrapper_1_14_R1 extends ChatColorWrapper {

    @Override
    public ArgumentType<?> getNMSType() {
        return ArgumentChatFormat.a();
    }

}
