package me.i509.brigwrapper.help;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import me.i509.brigwrapper.BrigadierWrapper;
import me.i509.brigwrapper.CommandPermission;
import me.i509.brigwrapper.command.BrigadierCommand;
import me.i509.brigwrapper.util.Pair;

public class HelpHelper {
    
    /**
     * Attempts to override the help topic
     * @param commandName The command name to override, without any /
     * @param command The instance of the command
     * @return true if successful, otherwise false.
     */
    public static void overrideTopic(String commandName, CommandPermission perm, BrigadierCommand right) {
        //if(helpTopics !=null) {
        //    helpTopics.put("/"+commandName, new CustomCommandHelpTopic(commandName, perm, right));
        //    return true;
        //}
        
        BrigadierWrapper.getAllCommands();
        
        Pair<String,String> builtTopic = buildTopicAmended(commandName, perm, right);
        
        if(perm.isString()) {
            Bukkit.getHelpMap().getHelpTopic("/" + commandName).amendCanSee(perm.asString());
        }
        
        Bukkit.getHelpMap().getHelpTopic("/" + commandName).amendTopic(builtTopic.getLeft(), builtTopic.getRight());
    }
    
    private static Pair<String,String> buildTopicAmended(String commandName, CommandPermission perm, BrigadierCommand command) {
        String shortText;
        String fullText;

        shortText = command.shortDesc().orElse(BrigadierWrapper.TRUNCATED_DEFAULT);

        StringBuilder sb = new StringBuilder();

        sb.append(ChatColor.GOLD);
        sb.append("Description: ");
        sb.append(ChatColor.WHITE);
        sb.append(command.description().orElse(BrigadierWrapper.DEFAULT_DESCRIPTION));

        if (command.usage().isPresent()) {
            sb.append("\n");
            sb.append(ChatColor.GOLD);
            sb.append("Usage: ");
            sb.append("\n");
            sb.append(ChatColor.WHITE);
            sb.append(command.usage().get());
        }
        
        fullText = sb.toString();
        
        return Pair.create(shortText, fullText);
    }
}
