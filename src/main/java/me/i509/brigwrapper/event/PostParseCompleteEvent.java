package me.i509.brigwrapper.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;


public class PostParseCompleteEvent extends Event {

    private static HandlerList handlers = new HandlerList();
    
    public PostParseCompleteEvent(@NotNull CommandSender sender, boolean exceptionView, @NotNull ParseResults<?> results, @NotNull StringReader reader) {
        this.sender = sender;
        this.isExceptionHandler = exceptionView;
        this.results = results;
        this.stringReader = reader;
    }
    
    private final CommandSender sender;

    private final boolean isExceptionHandler;

    private final ParseResults<?> results;

    private final StringReader stringReader;
    
    public boolean canSenderSeeExceptions() {
        return isExceptionHandler;
    }
    
    public CommandSender getFutureSender() {
        return this.sender;
    }
    
    public ParseResults<?> getResults() {
        return this.results;
    }
    
    public StringReader getStringReader() {
        return this.stringReader;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
