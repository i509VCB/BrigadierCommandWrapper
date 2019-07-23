package me.i509.brigwrapper;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.mojang.brigadier.StringReader;

import me.i509.brigwrapper.event.CustomExceptionClearEvent;
import me.i509.brigwrapper.event.CustomExceptionSendEvent;
import me.i509.brigwrapper.event.StringReaderRecieveEvent;
import me.i509.util.SerializablePair;

/**
 * Future feature, not implemented yet.
 *
 */
public class DynamicErrorProvider {
    
    /**
     * The {@link PluginMessageListener} fired when the client sends a StringReader to the server to parse for custom errors.
     */
    public static final PluginMessageListener LISTENER = (channel, player, message) -> {
        
        if(!channel.equals("bgw:c2s_cmdl")) {
            return;
        }
        
        String msg = new String(message, Charset.forName("UTF-8"));
        
        Gson gson = new Gson();
        
        @SuppressWarnings("unchecked")
        SerializablePair<String, Number> serialstringreader = gson.fromJson(msg, SerializablePair.class);
        
        StringReader stringreader = new StringReader(serialstringreader.getLeft());
        stringreader.setCursor((int) serialstringreader.getRight().intValue());
        StringReaderRecieveEvent evt = new StringReaderRecieveEvent(stringreader, player);
        Bukkit.getPluginManager().callEvent(evt);

    };
    
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
    
    /**
     * Sends a dynamic command message to a player. This will send to any player but only do something if the player has the dynamic command messages mod installed.
     * This can be used for command errors or messages such as the outcome of a command or info of an entity you have selected.
     * @param player The player to send the dynamic message to.
     * @param supplier The supplier to get the message from.
     */
    public static void sendDynamicMessage(@NotNull Player player, Supplier<String> supplier) {
        String message = ChatColor.stripColor(supplier.get()); // No color is allowed in the packet due to the message always being displayed as white text
        serializeAndSend(player, message);
    }
    
    /**
     * Clear all the exceptions from dynamic command popup that the player sees.     This will do nothing if the Player does not have the ChatScreen or CommandBlock screen open.
     * @param player The player to clear the exception popups from.
     */
    public static void clearExceptions(@NotNull Player player) {
        CustomExceptionClearEvent evt = new CustomExceptionClearEvent(player);
        Bukkit.getPluginManager().callEvent(evt);
        player.sendPluginMessage(BrigadierWrapperPlugin.PACKAGE_INSTANCE, "bgw:s2c_clearex", new byte[0]); // Yes quite literally an empty message, if we need new exceptions just send another packet afterwards
    }

    
    private static void serializeAndSend(Player player, String... msg) {
        List<String> list = new ArrayList<String>();
        
        if(msg.length>0) {
            for(String ms : msg) {
                list.add(ms);
            }
        }
        
        CustomExceptionSendEvent evt = new CustomExceptionSendEvent(list, player);
        Bukkit.getPluginManager().callEvent(evt);
        
        Gson gson = new Gson();

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.write(gson.toJson(list).getBytes());
        
        player.sendPluginMessage(BrigadierWrapperPlugin.PACKAGE_INSTANCE, "bgw:s2c_msg", out.toByteArray());
    }
    
}
