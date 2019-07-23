package me.i509.brigwrapper.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class CustomExceptionClearEvent extends PlayerEvent {

    private static HandlerList handlers = new HandlerList();

    public CustomExceptionClearEvent(Player who) {
        super(who);
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
