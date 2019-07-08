package me.i509.brigwrapper.help;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpTopic;

import me.i509.brigwrapper.BrigadierWrapper;
import me.i509.brigwrapper.CommandPermission;
import me.i509.brigwrapper.command.BrigadierCommand;
import me.i509.brigwrapper.util.CommandUtils;

/**
 * Represents a custom help topic for /help for commands registered using Brigadier.
 * Includes a short and long description and an optional usage.
 * @author i509VCB
 */
public class CustomCommandHelpTopic extends HelpTopic {
    
    private CommandPermission perm;
    
    public CustomCommandHelpTopic(String commandName, CommandPermission perm, BrigadierCommand command) {
        this.perm = perm;
        
        if(perm.noPermissionNeeded())
        
        this.shortText = command.shortDesc().orElse(BrigadierWrapper.TRUNCATED_DEFAULT);

        StringBuilder sb = new StringBuilder();

        sb.append(ChatColor.GOLD);
        sb.append("Description: ");
        sb.append(ChatColor.WHITE);
        sb.append(command.description().orElse(BrigadierWrapper.DEFAULT_DESCRIPTION));

        if (command.usage().isPresent()) {
            sb.append("\n");
            sb.append(ChatColor.GOLD);
            sb.append("Usage: ");
            sb.append(ChatColor.WHITE);
            sb.append(command.usage());
        }
        
        this.fullText = sb.toString();
    }

    @Override
    public boolean canSee(CommandSender player) {
        if(perm.noPermissionNeeded()) {
            return true;
        }
        
        return CommandUtils.testSenderPerms(player, perm);
    }

}
