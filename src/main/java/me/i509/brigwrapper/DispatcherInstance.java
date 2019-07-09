package me.i509.brigwrapper;

import org.bukkit.entity.Player;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;

import me.i509.brigwrapper.impl.v1_14_R1.Dispatcher_1_14_R1;

public abstract class DispatcherInstance {
    
    private static Dispatcher_1_14_R1 INSTANCE;

    static {
        INSTANCE = new Dispatcher_1_14_R1();
    }
    
    /**
     * Gets the current instance of the Dispatcher instance
     * @return The current DispatcherInstance
     */
    public static DispatcherInstance getInstance() {
        return INSTANCE;
    }
    
    /**
     * Gets the CommandDispatcher from this instance
     * @return the CommandDispatcher
     */
    public abstract CommandDispatcher<?> dispatcher();

    
    /**
     * Future feature not implemented yet
     * @param commandLine
     * @param p
     * @return
     */
    @Deprecated
    public abstract ParseResults verifyDynamicOrError(String commandLine, Player p, String argName);
}
