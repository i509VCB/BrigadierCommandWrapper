package me.i509.brigwrapper;

import java.nio.charset.Charset;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.mojang.brigadier.StringReader;

import me.i509.brigwrapper.CommandPermission.PermissionType;
import me.i509.brigwrapper.command.DynamicErrorTest;
import me.i509.brigwrapper.command.PluginCommand;
import me.i509.brigwrapper.help.HelpHelper;
import me.i509.util.SerializablePair;

public class BrigadierWrapperPlugin extends JavaPlugin {
    
    public static BrigadierWrapperPlugin TEMP_INSTANCE;
    
    public void onEnable() {
        
        TEMP_INSTANCE = this;
        
        registerChannels();

        BrigadierWrapper.registerCommand("bwrapper", this, CommandPermission.of("bwrapper.general"), PluginCommand.getCmd());
        
        BrigadierWrapper.registerCommand("packet_test", this, CommandPermission.of(PermissionType.OP), new DynamicErrorTest());

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

       getServer().getMessenger().registerIncomingPluginChannel(this, "bgw:c2s_cmdl", (channel, player, message) -> {
           
           System.out.println("Received packet");
           
           if(!channel.equals("bgw:c2s_cmdl")) {
               return;
           }
           
           String msg = new String(message, Charset.forName("UTF-8"));
           
           //String actual = msg.substring(channel.length()+1); // Cut out identifier
           
           System.out.println(msg);
           //System.out.println(actual);
           
           //Gson gson = new Gson();
           
           Gson gson = new Gson();
           
           @SuppressWarnings("unchecked")
           SerializablePair<String, Number> serialstringreader = gson.fromJson(msg, SerializablePair.class);
           
           StringReader stringreader = new StringReader(serialstringreader.getLeft());
           stringreader.setCursor((int) serialstringreader.getRight().intValue());
           
           
           System.out.println(stringreader.canRead());
           
           System.out.println(stringreader.getString());
           
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
