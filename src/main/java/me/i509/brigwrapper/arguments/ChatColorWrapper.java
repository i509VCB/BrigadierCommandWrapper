package me.i509.brigwrapper.arguments;

import org.bukkit.ChatColor;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.impl.v1_14_R1.arg.ChatColorWrapper_1_14_R1;
import me.i509.brigwrapper.impl.v1_14_R1.arg.decoder.ChatColorDecoder_1_14_R1;

public abstract class ChatColorWrapper implements IArgumentWrapper {

    @Override
    public abstract ArgumentType<?> getNMSType();
    
    public static abstract class ChatColorArgumentDecoder {
        private static ChatColorArgumentDecoder INSTANCE;
        
        private static ChatColorArgumentDecoder createInstance() {
            // TODO verion stuff
            return new ChatColorDecoder_1_14_R1();
        }
        
        public static ChatColor getType(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
            if(INSTANCE==null) {
                INSTANCE = createInstance();
            }
            
            return INSTANCE._getColor(cmdCtx, str);
        }
        
        /* 
         * ==========================
         *      Abstract methods
         * ==========================
         */
        
        protected abstract ChatColor _getColor(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException;
    }

    public static ChatColorWrapper color() {
        return new ChatColorWrapper_1_14_R1();
    }
}
