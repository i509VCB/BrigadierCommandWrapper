package me.i509.brigwrapper.arguments;

import org.bukkit.Material;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.impl.v1_14_R1.arg.ItemArgumentWrapper_1_14_R1;
import me.i509.brigwrapper.impl.v1_14_R1.arg.decoder.ItemStackArgumentDecoder_1_14_R1;

public abstract class ItemArgumentWrapper implements IArgumentWrapper {
    
    public static ItemArgumentWrapper item() {
        // TODO
        return new ItemArgumentWrapper_1_14_R1();
    }
    
    @Override
    public abstract ArgumentType<?> getNMSType();
    
    public static abstract class ItemStackArgumentDecoder {
        
        private static ItemStackArgumentDecoder INSTANCE;
        
        private static ItemStackArgumentDecoder createInstance() {
            // TODO verion stuff
            return new ItemStackArgumentDecoder_1_14_R1();
        }
        
        public static Material getType(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
            if(INSTANCE==null) {
                INSTANCE = createInstance();
            }
            
            return INSTANCE._getType(cmdCtx, str);
        }
        
        /* 
         * ==========================
         *      Abstract methods
         * ==========================
         */
        
        protected abstract Material _getType(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException;
    }
}
