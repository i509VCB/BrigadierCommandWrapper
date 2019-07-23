package me.i509.brigwrapper;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ConfirmationUtil {
    
    private static Map<String, Runnable> confirmList = new HashMap<String, Runnable>();
    
    public static final BaseComponent[] GENERIC_ACCEPT_DENY;
    
    
    static {
        TextComponent ACCEPT = new TextComponent("[ACCEPT]");
        ACCEPT.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bconfirm"));
        ACCEPT.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(new TextComponent("Confirm this request")).create()));
        
        TextComponent DENY = new TextComponent("[DENY]");
        DENY.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bdeny"));
        DENY.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(new TextComponent("Denies this request")).create()));
        
        
        BaseComponent[] comp = new ComponentBuilder("")
                .color(ChatColor.GREEN)
                .append(ACCEPT)
                .color(ChatColor.RED)
                .append(DENY)
                .create();
        
        GENERIC_ACCEPT_DENY = comp;
    }
    
    
    
    public static boolean canConfirm(CommandSource source) {
        if (BrigadierWrapper.getConfirm().isOnList(source.getSender().getName())) {
            return true;
        }
        return false;
    }

    public void deny(String sender_name) {
        if(isOnList(sender_name)) {
            confirmList.remove(sender_name);
        }
    }
    
    public void accept(String sender_name) {
        if(isOnList(sender_name)) {
            confirmList.get(sender_name).run();
        }
    }
    
    public void addToList(CommandSender sender, Runnable run) {
        confirmList.put(sender.getName(), run);
    }

    public boolean isOnList(String sender_name) {
        return confirmList.containsKey(sender_name);
    }
}
