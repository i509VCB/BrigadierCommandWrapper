package me.i509.brigwrapper.impl.v1_14_R1;

import com.mojang.brigadier.CommandDispatcher;

import me.i509.brigwrapper.DispatcherInstance;
import net.minecraft.server.v1_14_R1.MinecraftServer;

public class Dispatcher_1_14_R1 extends DispatcherInstance {
    @SuppressWarnings({ "deprecation" })
    @Override
    public CommandDispatcher<?> dispatcher() {
        return MinecraftServer.getServer().commandDispatcher.a();
    }
}
