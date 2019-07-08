package me.i509.brigwrapper.arguments;

import org.bukkit.Material;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.impl.v1_14_R1.arg.BlockArgumentWrapper_1_14_R1;
import me.i509.brigwrapper.impl.v1_14_R1.arg.decoder.BlockArgumentDecoder_1_14_R1;

public abstract class BlockArgumentWrapper implements IArgumentWrapper {

    @Override
    public abstract ArgumentType<?> getNMSType();
    
    public static abstract class BlockArgumentDecoder {
        
        private static BlockArgumentDecoder INSTANCE;
        
        private static BlockArgumentDecoder createInstance() {
            // TODO verion stuff
            return new BlockArgumentDecoder_1_14_R1();
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

    public static BlockArgumentWrapper block() {
        // TODO Auto-generated method stub
        return new BlockArgumentWrapper_1_14_R1();
    }
}
