package me.i509.brigwrapper;

import org.bukkit.plugin.java.JavaPlugin;

import me.i509.brigwrapper.command.PluginCommand;
import me.i509.brigwrapper.help.HelpHelper;

public class BrigadierWrapperPlugin extends JavaPlugin {
    
    public static BrigadierWrapperPlugin TEMP_INSTANCE;
    
    public void onEnable() {
        
        TEMP_INSTANCE = this;
        
        registerChannels();

        BrigadierWrapper.registerCommand("bwrapper", this, CommandPermission.of("bwrapper.general"), PluginCommand.getCmd());

        getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
            BrigadierWrapper.INSTANCE.internalCommandMap.forEach((plugin, commandPair) -> 
               HelpHelper.overrideTopic(commandPair.getLeft(), BrigadierWrapper.INSTANCE.permissionMap.get(commandPair.getLeft()), commandPair.getRight()));
            },1L);
   }
   
    /**
     * Register the plugin channels for future feature. Reccomended to just ignore these
     */
    private void registerChannels() {
       getServer().getMessenger().registerOutgoingPluginChannel(this, "BGW_S2C_MSG");

       getServer().getMessenger().registerIncomingPluginChannel(this, "BGW_C2S_CMDL", (channel, player, message) -> {

           if(channel.equals("BGW_C2S_CMDL")) {
               return;
           }
           /*
           String commandLine = new String(Base64.getDecoder().decode(message));
           
           DispatcherInstance.getInstance().verifyDynamicOrError(commandLine, player);
           */
       });
        
    }

   public void onDisable() {
       getServer().getMessenger().unregisterIncomingPluginChannel(this);
       getServer().getMessenger().unregisterOutgoingPluginChannel(this);
   }
}
