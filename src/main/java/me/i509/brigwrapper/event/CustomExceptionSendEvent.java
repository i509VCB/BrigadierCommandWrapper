package me.i509.brigwrapper.event;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class CustomExceptionSendEvent extends PlayerEvent {

    private static HandlerList handlers = new HandlerList();
    private List<String> exceptions;

    public CustomExceptionSendEvent(List<String> customExceptions, Player who) {
        super(who);
        this.exceptions = customExceptions;
        // TODO Auto-generated constructor stub
    }
    
    public List<String> getCustomExceptions() {
        return exceptions;
    }

    public static HandlerList getHandlerList() {
        return handlers ;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
