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

        getServer().getScheduler().scheduleSyncDelayedTask(this, () -> { // TODO This can error out, needs to be fixed due to NPEs
            BrigadierWrapper.INSTANCE.internalCommandMap.forEach((plugin, commandPair) -> 
               HelpHelper.overrideTopic(commandPair.getLeft(), BrigadierWrapper.INSTANCE.permissionMap.get(commandPair.getLeft()), commandPair.getRight()));
            },1L);
   }
   
    /**
     * Register the plugin channels for future feature. Reccomended to just ignore these for now.
     */
    private void registerChannels() {
       getServer().getMessenger().registerOutgoingPluginChannel(this, "bgw:s2c_msg");

       getServer().getMessenger().registerIncomingPluginChannel(this, "bgw:s2c_cmdl", (channel, player, message) -> {

           if(channel.equals("bgw:c2s_cmdl")) {
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
