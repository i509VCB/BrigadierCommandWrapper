package me.i509.brigwrapper;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.leonhard.storage.Yaml;
import me.i509.brigwrapper.CommandPermission.PermissionType;
import me.i509.brigwrapper.command.BrigadierWrappedCommand;
import me.i509.brigwrapper.command.Dimtest;
import me.i509.brigwrapper.command.DynamicErrorTest;
import me.i509.brigwrapper.command.PluginCommand;
import me.i509.brigwrapper.config.ConfigWrapper;
import me.i509.brigwrapper.help.BrigadierHelpTopic;

public class BrigadierWrapperPlugin extends JavaPlugin {
    
    static BrigadierWrapperPlugin PACKAGE_INSTANCE;
    private static boolean isMultiworld;
    private Yaml yaml;
    
    @SuppressWarnings("deprecation")
    public void onEnable() {
        
        PACKAGE_INSTANCE = this;
        
        checkForSoftDependancies();
        
        config();
        
        registerChannels();
        
        Bukkit.getHelpMap().registerHelpTopicFactory(BrigadierWrappedCommand.class, command -> {
            return new BrigadierHelpTopic((BrigadierWrappedCommand) command);
        });

        BrigadierWrapper.registerCommand("bwrapper", this, CommandPermission.of("bwrapper.general"), PluginCommand.getCmd());
        
        BrigadierWrapper.registerCommand("packet_test", this, CommandPermission.of(PermissionType.OP), new DynamicErrorTest());
        
        BrigadierWrapper.registerCommand("dimens", this, CommandPermission.of(PermissionType.OP), new Dimtest());
        
        // TODO permissions logic
        
        getServer().getScheduler().scheduleSyncDelayedTask(this, () -> { // TODO This can error out, needs to be fixed due to NPEs
            
            try {
                DispatcherInstance.getInstance().syncCommands();
            } catch (ReflectiveOperationException e) {
                getLogger().severe("Failed to sync commands after server startup");
                e.printStackTrace();
            }
            
            BrigadierWrapper.setLoaded(); // Allow for on the fly registration now.
            
            /**
            BrigadierWrapper.INSTANCE.internalCommandMap.forEach((plugin, commandPair) -> 
               HelpHelper.overrideTopic(commandPair.getLeft(), BrigadierWrapper.INSTANCE.permissionMap.get(commandPair.getLeft()), commandPair.getRight()));
               */
            },0L);
            
   }
   
    public void onDisable() {
           getServer().getMessenger().unregisterIncomingPluginChannel(this);
           getServer().getMessenger().unregisterOutgoingPluginChannel(this);
       }

    /**
     * Register the plugin channels for future feature. Reccomended to just ignore these for now.
     */
    private void registerChannels() {
       getServer().getMessenger().registerOutgoingPluginChannel(this, "bgw:s2c_msg");
       getServer().getMessenger().registerOutgoingPluginChannel(this, "bgw:s2c_clearex");
       getServer().getMessenger().registerIncomingPluginChannel(this, "bgw:c2s_cmdl", DynamicErrorProvider.LISTENER);
    }

    private void checkForSoftDependancies() {
        // TODO MultiWorld stuff
        
    }

    private void config() {
        Yaml yaml = yamlConfig();
        isMultiworld = ConfigWrapper.readValue(ConfigWrapper.useMultiWorldHandler, yamlConfig()); // Immutable
        BrigadierWrapper.reloadConfig();
    }

    Yaml yamlConfig() {
        if(yaml==null) {
            yaml = new Yaml("config", getDataFolder().getPath());
        }
        
        return yaml;
    }

    public static boolean isMultiWorld() {
       // TODO common multiworld plugin checks
       return isMultiworld;
   }

   public static File getDataDir() {
       return PACKAGE_INSTANCE.getDataFolder();
   }
}
