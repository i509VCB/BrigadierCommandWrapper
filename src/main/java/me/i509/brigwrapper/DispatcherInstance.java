package me.i509.brigwrapper;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;

import me.i509.brigwrapper.impl.v1_14_R1.Dispatcher_1_14_R1;

public abstract class DispatcherInstance {
    
    private static DispatcherInstance INSTANCE;

    static {
        INSTANCE = new Dispatcher_1_14_R1();
    }
    
    /**
     * Gets the current instance of the {@link DispatcherInstance}.
     * @return The current {@link DispatcherInstance}.
     */
    public static DispatcherInstance getInstance() {
        return INSTANCE;
    }
    
    
    /**
     * Gets the {@link CommandDispatcher} from this instance. 
     * This should not be used by plugins for registering commands. 
     * <p>Instead use {@link BrigadierWrapper#registerCommand(String, Plugin, String[], CommandPermission, BrigadierCommand)} to register commands.
     * @return the {@link CommandDispatcher}
     *
     **/
    @SuppressWarnings("rawtypes")
    public abstract CommandDispatcher dispatcher();
    
    /**
     * Future feature not implemented yet. May not survive final release
     * @param commandLine
     * @param p
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Deprecated
    public abstract ParseResults verifyDynamicOrError(String commandLine, Player p, String argName);

    @SuppressWarnings("rawtypes")
    public abstract ParseResults parse(String dispatcher, CommandSender sender);

    public abstract boolean execute(CommandSender sender, String commandLabel, String name, String[] args);
    
    public abstract void syncCommands() throws ReflectiveOperationException;
}
