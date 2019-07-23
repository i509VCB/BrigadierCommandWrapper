package me.i509.brigwrapper.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import com.mojang.brigadier.StringReader;

public class StringReaderRecieveEvent extends PlayerEvent {

    private static HandlerList handlers = new HandlerList();
    private StringReader reader;

    public StringReaderRecieveEvent(final StringReader reader, Player origin) {
        super(origin);
        this.reader = reader;
    }
    
    public final StringReader getReader() {
        return this.reader;
    }

    public static HandlerList getHandlerList() {
        return handlers ;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
