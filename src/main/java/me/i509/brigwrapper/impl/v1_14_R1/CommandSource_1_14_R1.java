package me.i509.brigwrapper.impl.v1_14_R1;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import me.i509.brigwrapper.source.CommandSource;
import net.minecraft.server.v1_14_R1.CommandListenerWrapper;

public class CommandSource_1_14_R1 implements CommandSource {
    
    protected CommandSource_1_14_R1(CommandListenerWrapper source) {
        this.source = source;
    }

    private CommandListenerWrapper source;

    public static CommandSource fromObject(Object source) {
        if (source instanceof CommandListenerWrapper) {
            return new CommandSource_1_14_R1((CommandListenerWrapper) source);
        }
        
        throw new IllegalArgumentException("Tried to get the CommandSource from server but the source object is not an instance of CommandListenerWrapper");
    }

    @Override
    public CommandSender getSender() {
        return source.getBukkitSender();
    }
    
    @Override
    public World getWorld() {
        return source.getWorld().getWorld();
    }
    
    @Override
    public Location getSenderPos() {
        return new Location(source.getWorld().getWorld() ,source.getPosition().x, source.getPosition().y, source.getPosition().z);
    }
}
