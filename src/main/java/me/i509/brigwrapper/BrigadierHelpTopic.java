package me.i509.brigwrapper;

import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpTopic;

import me.i509.brigwrapper.command.BrigadierWrappedCommand;

public class BrigadierHelpTopic extends HelpTopic {
    
    private BrigadierWrappedCommand root;

    public BrigadierHelpTopic(BrigadierWrappedCommand command) {
        this.root = command;
        this.shortText = BrigadierHelpTopic.buildShortText(command);
        this.fullText = BrigadierHelpTopic.buildLongText(command);
    }

    private static String buildLongText(BrigadierWrappedCommand command) {
        // TODO Auto-generated method stub
        return null;
    }

    private static String buildShortText(BrigadierWrappedCommand command) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean canSee(CommandSender sender) {
        return root.testPermission(sender);
    }

}
