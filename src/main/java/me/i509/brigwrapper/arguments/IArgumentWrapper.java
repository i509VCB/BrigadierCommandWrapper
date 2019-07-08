package me.i509.brigwrapper.arguments;

import com.mojang.brigadier.arguments.ArgumentType;

/**
 * Represents an argument wrapper for NMS based arguments
 * @author Vince
 *
 */
public interface IArgumentWrapper {
    /**
     * Gets the NMS argument of said argument
     * @return The NMS argument
     */
    public ArgumentType<?> getNMSType();  
}
