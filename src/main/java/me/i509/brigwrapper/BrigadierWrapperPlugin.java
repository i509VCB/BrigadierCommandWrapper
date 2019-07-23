package me.i509.brigwrapper;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.leonhard.storage.Yaml;
import io.papermc.lib.PaperLib;
import me.i509.brigwrapper.CommandPermission.PermissionType;
import me.i509.brigwrapper.command.BrigadierWrappedCommand;
import me.i509.brigwrapper.command.Dimtest;
import me.i509.brigwrapper.command.DynamicErrorTest;
import me.i509.brigwrapper.command.PluginCommand;

public class BrigadierWrapperPlugin extends JavaPlugin {
    
    static BrigadierWrapperPlugin PACKAGE_INSTANCE;
    private Yaml yaml;
    
    
    @SuppressWarnings("deprecation")
    public void onEnable() {
        
        PACKAGE_INSTANCE = this;
        
        PaperLib.suggestPaper(this);
        
        if (!PaperLib.getEnvironment().isSpigot()) {
            throw new NoSpigotNoRunException();
        }
        
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
        BrigadierWrapper.reloadConfig();
    }

    Yaml yamlConfig() {
        if(yaml==null) {
            yaml = new Yaml("config", getDataFolder().getPath());
        }
        
        return yaml;
    }

    public static void checkIfMultiWorld() {
       
        boolean flag = false; 
        
        if(PACKAGE_INSTANCE.getServer().getPluginManager().isPluginEnabled("Multiworld") || PACKAGE_INSTANCE.getServer().getPluginManager().isPluginEnabled("Multiverse")) {
            flag = true;
        } 
        
        if(!BrigadierWrapper.useFallbackDimensionArgument() && flag) {
            // TODO warn in console about multiworld issue
        }
   }

   public static File getDataDir() {
       return PACKAGE_INSTANCE.getDataFolder();
   }
}
