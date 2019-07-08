package me.i509.brigwrapper.dynamic;

import java.util.function.Supplier;

import org.bukkit.entity.Player;

public class DynamicErrorProvider {
    
    /**
     * Sends a dynamic command message to a player. This will send to any player but only do something if the player has the dynamic command messages mod installed.
     * This can be used for command errors or messages such as the outcome of a command or info of an entity you have selected.
     * @param player The player to send the dynamic message to
     * @param s The message to send

     */
    public static boolean sendDynamicMessage(Player player, String s) {
        return false;
    }
    
    public static boolean sendDynamicMessage(Player player, Supplier<String> supplier) {
        return false;
    }
    
    
    
}
