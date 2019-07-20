package me.i509.brigwrapper.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Supplier;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.BrigadierWrapper;
import me.i509.brigwrapper.CommandPermission;
import me.i509.brigwrapper.source.CommandSource;

public class CommandUtils {
    
    /**
     * Verifies if this {@link CommandSender} is a player.
     * @param sender The command sender to test.
     * @return true if the sender is a player, otherwise false.
     */
    public static boolean isPlayer(@NotNull CommandSender sender) {
        return sender instanceof Player;
    }
    
    /**
     * Verifies if this {@link CommandSender} is not a {@link ConsoleCommandSender}.
     * @param sender The command sender to test.
     * @return false if the CommandSender is the console, otherwise true.
     */
    public static boolean notConsole(@NotNull CommandSender sender) {
        return !(sender instanceof ConsoleCommandSender);
    }
    
    /**
     * 
     * Verifies if this {@link CommandSender} is a Player, otherwise ends the command.
     * @param sender The command sender to test.
     * @param failureMessage The failure message.
     * @throws CommandSyntaxException To end the command if the sender is not a player.
     */
    public static void notPlayerThenFail(@NotNull CommandSender sender, @NotNull String failureMessage) throws CommandSyntaxException {
        if(sender instanceof Player) {
            return;
        }
        
        BrigadierWrapper.fail(failureMessage);
    }
    
    /**
     * 
     * Verifies if this {@link CommandSender} is a Player, otherwise ends the command.
     * @param sender The command sender to test.
     * @param failureMessage The supplier for the failure message.
     * @throws CommandSyntaxException To end the command if the sender is not a player.
     */
    public static void notPlayerThenFail(@NotNull CommandSender sender, @NotNull Supplier<String> failureMessage) throws CommandSyntaxException {
        if(sender instanceof Player) {
            return;
        }
        BrigadierWrapper.fail(failureMessage);
    }
    
    /**
     * 
     * Verifies if this {@link CommandSender} is not the Console, otherwise ends the command.
     * @param sender The command sender to test.
     * @param failureMessage The failure message.
     * @throws CommandSyntaxException To end the command if the sender is the console.
     */
    public static void ifConsoleFail(@NotNull CommandSender sender, @NotNull String failureMessage) throws CommandSyntaxException {
        if(sender instanceof ConsoleCommandSender) {
            BrigadierWrapper.fail(failureMessage);
        }
    }
    
    /**
     * 
     * Verifies if this {@link CommandSender} is not the Console, otherwise ends the command.
     * @param sender The command sender to test.
     * @param failureMessage The supplier for the failure message.
     * @throws CommandSyntaxException To end the command if the sender is the console.
     */
    public static void ifConsoleFail(@NotNull CommandSender sender, @NotNull Supplier<String> failureMessage) throws CommandSyntaxException {
        if(sender instanceof ConsoleCommandSender) {
            BrigadierWrapper.fail(failureMessage);
        }
    }
    
    /**
     * Tests that this CommandSender has permission to.
     * @param source The command source.
     * @param perm The permission to test for.
     * @return true if the sender has permission, otherwise false.
     */
    public static boolean testSenderPerms(@NotNull CommandSource source, @NotNull CommandPermission perm) {
        if(perm.noPermissionNeeded()) {
            return true;
        }
        
        if(perm.isString()) {
            if(source.getSender().hasPermission(perm.asString())) return true; else return false;
        } else {
            switch(perm.type()) {
            case CONSOLE:
                if(source.getSender() instanceof ConsoleCommandSender) return true; else return false;
            case COMMAND_BLOCK:
                if(source.getSender() instanceof BlockCommandSender) return true; else return false;
            case OP:
                if(source.getSender().isOp()) return true; else return false;
            default:
                return false;
            }
        }
    }
    
    public static boolean testSenderPerms(@NotNull CommandSender sender, @NotNull CommandPermission perm) {
        if(perm.noPermissionNeeded()) {
            return true;
        }
        
        if(perm.isString()) {
            if(sender.hasPermission(perm.asString())) return true; else return false;
        } else {
            switch(perm.type()) {
            case CONSOLE:
                if(sender instanceof ConsoleCommandSender) return true; else return false;
            case COMMAND_BLOCK:
                if(sender instanceof BlockCommandSender) return true; else return false;
            case OP:
                if(sender.isOp()) return true; else return false;
            default:
                return false;
            }
        }
    }
    
    public static String joinWithSpace(String commandLabel, String... strings) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(commandLabel);
        sb.append(" ");
        
        Iterator<String> i = Arrays.stream(strings).iterator();
        
        while (i.hasNext()) {
            sb.append(i.next());
            if(i.hasNext()) {
                sb.append(" ");
            }
        }
        
        return sb.toString();
    }
}
