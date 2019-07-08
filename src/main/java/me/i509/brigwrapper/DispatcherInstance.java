package me.i509.brigwrapper;

import com.mojang.brigadier.CommandDispatcher;

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
}
