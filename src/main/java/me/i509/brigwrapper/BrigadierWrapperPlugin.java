package me.i509.brigwrapper;

import org.bukkit.plugin.java.JavaPlugin;

import me.i509.brigwrapper.CommandPermission.PermissionType;
import me.i509.brigwrapper.command.Dimtest;
import me.i509.brigwrapper.command.DynamicErrorTest;
import me.i509.brigwrapper.command.PluginCommand;
import me.i509.brigwrapper.dynamic.DynamicErrorProvider;
import me.i509.brigwrapper.help.HelpHelper;

public class BrigadierWrapperPlugin extends JavaPlugin {
    
    public static BrigadierWrapperPlugin TEMP_INSTANCE;
    
    public void onEnable() {
        
        TEMP_INSTANCE = this;
        
        registerChannels();

        BrigadierWrapper.registerCommand("bwrapper", this, CommandPermission.of("bwrapper.general"), PluginCommand.getCmd());
        
        BrigadierWrapper.registerCommand("packet_test", this, CommandPermission.of(PermissionType.OP), new DynamicErrorTest());
        
        BrigadierWrapper.registerCommand("dimens", this, CommandPermission.of(PermissionType.OP), new Dimtest());
        
        // TODO permissions logic
        
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
       getServer().getMessenger().registerIncomingPluginChannel(this, "bgw:c2s_cmdl", DynamicErrorProvider.LISTENER);
    }

   public void onDisable() {
       getServer().getMessenger().unregisterIncomingPluginChannel(this);
       getServer().getMessenger().unregisterOutgoingPluginChannel(this);
   }

   public static boolean isMultiWorld() {
       // TODO common multiworld plugin checks
       return false;
   }
}
