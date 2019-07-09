package me.i509.brigwrapper.dynamic;

import java.util.function.Supplier;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import me.i509.brigwrapper.BrigadierWrapperPlugin;

/**
 * Future feature, not implemented yet.
 *
 */
public class DynamicErrorProvider {
    
    /**
     * Sends a dynamic command message to a player. This will send to any player but only do something if the player has the dynamic command messages mod installed.
     * This can be used for command errors or messages such as the outcome of a command or info of an entity you have selected.
     * @param player The player to send the dynamic message to
     * @param s The message to send
     */
    public static void sendDynamicMessage(@NotNull Player player, String s) {
        String message = ChatColor.stripColor(s); // No color is allowed in the packet due to the message always being displayed as white text
        serializeAndSend(player, message);
    }
    
    public static void sendDynamicMessage(@NotNull Player player, Supplier<String> supplier) {
        String message = ChatColor.stripColor(supplier.get()); // No color is allowed in the packet due to the message always being displayed as white text
        serializeAndSend(player, message);
    }
    
    private static void serializeAndSend(Player player, String msg) {
        // TODO serializer logic?
        
        player.sendPluginMessage(BrigadierWrapperPlugin.TEMP_INSTANCE, "BGW_S2C_MSG", msg.getBytes());
    }
    
}
