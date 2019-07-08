package me.i509.brigwrapper;

import org.bukkit.plugin.java.JavaPlugin;

import me.i509.brigwrapper.CommandPermission.PermissionType;
import me.i509.brigwrapper.command.BlockProfileTest;
import me.i509.brigwrapper.command.ItemTest;
import me.i509.brigwrapper.command.LocationTestCommand;
import me.i509.brigwrapper.command.PluginCommand;
import me.i509.brigwrapper.command.SelectorTestCommand;
import me.i509.brigwrapper.help.HelpHelper;

public class BrigadierWrapperPlugin extends JavaPlugin {
    
    public static BrigadierWrapperPlugin TEMP_INSTANCE;
    
    public void onEnable() {
        
        TEMP_INSTANCE = this;
        
        BrigadierWrapper.registerCommand("bwrapper", this, CommandPermission.of("bwrapper.general"), PluginCommand.getCmd());
       
        BrigadierWrapper.registerCommand("selectortest", this, CommandPermission.of(PermissionType.OP), SelectorTestCommand.getCmd());
       
        BrigadierWrapper.registerCommand("loctest", this, CommandPermission.of(PermissionType.OP), LocationTestCommand.getCmd());
       
        BrigadierWrapper.registerCommand("ibt", this, CommandPermission.ofEmpty(), ItemTest.getCmd());
       
        BrigadierWrapper.registerCommand("blockp", this, CommandPermission.ofEmpty(), new BlockProfileTest());
        
       getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
           BrigadierWrapper.INSTANCE.internalCommandMap.forEach((plugin, commandPair) -> 
               HelpHelper.overrideTopic(commandPair.getLeft(), BrigadierWrapper.INSTANCE.permissionMap.get(commandPair.getLeft()), commandPair.getRight()));
       },1L);
       
       
   }
   
   public void onDisable() {
       
   }
}
