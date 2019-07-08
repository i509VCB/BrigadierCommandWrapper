package me.i509.brigwrapper.arguments;

import org.bukkit.Location;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.impl.v1_14_R1.arg.LocationArgumentWrapper_1_14_R1;
import me.i509.brigwrapper.impl.v1_14_R1.arg.decoder.loc.ArgumentDecoderBlockPosition2D_1_14_R1;
import me.i509.brigwrapper.impl.v1_14_R1.arg.decoder.loc.ArgumentDecoderBlockPosition_1_14_R1;
import me.i509.brigwrapper.impl.v1_14_R1.arg.decoder.loc.ArgumentDecoderVec2D_1_14_R1;
import me.i509.brigwrapper.impl.v1_14_R1.arg.decoder.loc.ArgumentDecoderVec3_1_14_R1;
import me.i509.brigwrapper.selectors.LocationType;

public abstract class LocationArgumentWrapper implements IArgumentWrapper {
    
    protected LocationType argType;
    
    public static LocationArgumentWrapper precise() {
        // TODO Version dependant
        return new LocationArgumentWrapper_1_14_R1(LocationType.PRECISE);
    }
    
    public static LocationArgumentWrapper block() {
        // TODO Version dependant
        return new LocationArgumentWrapper_1_14_R1(LocationType.BLOCK);
    }
    
    public static LocationArgumentWrapper precise2D() {
        
        return new LocationArgumentWrapper_1_14_R1(LocationType.PRECISE_2D);
    }
    
    public static LocationArgumentWrapper block2D() {
        
        return new LocationArgumentWrapper_1_14_R1(LocationType.BLOCK_2D);
    }
     
    @Override
    public abstract ArgumentType<?> getNMSType();
    
    public static abstract class ArgumentDecoderVec3 {
        
        private static ArgumentDecoderVec3 INSTANCE;
        
        private static ArgumentDecoderVec3 createInstance() {
            // TODO verion stuff
            return new ArgumentDecoderVec3_1_14_R1();
        }
        
        public static Location getLocation(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
            if(INSTANCE==null) {
                INSTANCE = createInstance();
            }
            
            return INSTANCE._getLocation(cmdCtx, str);
        }
        
        /* 
         * ==========================
         *      Abstract methods
         * ==========================
         */
        
        protected abstract Location _getLocation(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException;
    }
    
    public static abstract class ArgumentDecoderBlockPosition {
        
        private static ArgumentDecoderBlockPosition INSTANCE;
        
        private static ArgumentDecoderBlockPosition createInstance() {
            // TODO verion stuff
            return new ArgumentDecoderBlockPosition_1_14_R1();
        }
        
        public static Location getLocation(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
            if(INSTANCE==null) {
                INSTANCE = createInstance();
            }
            
            return INSTANCE._getLocation(cmdCtx, str);
        }
        
        /* 
         * ==========================
         *      Abstract methods
         * ==========================
         */
        
        protected abstract Location _getLocation(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException;
    }
    
    public static abstract class ArgumentDecoderBlockPosition_2D {
        
        private static ArgumentDecoderBlockPosition_2D INSTANCE;
        
        private static ArgumentDecoderBlockPosition_2D createInstance() {
            // TODO verion stuff
            return new ArgumentDecoderBlockPosition2D_1_14_R1();
        }
        
        public static Location getLocation(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
            if(INSTANCE==null) {
                INSTANCE = createInstance();
            }
            
            return INSTANCE._getLocation(cmdCtx, str);
        }
        
        /* 
         * ==========================
         *      Abstract methods
         * ==========================
         */
        
        protected abstract Location _getLocation(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException;
    }
    
public static abstract class ArgumentDecoderVec2D {
        
        private static ArgumentDecoderVec2D INSTANCE;
        
        private static ArgumentDecoderVec2D createInstance() {
            // TODO verion stuff
            return new ArgumentDecoderVec2D_1_14_R1();
        }
        
        public static Location getLocation(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
            if(INSTANCE==null) {
                INSTANCE = createInstance();
            }
            
            return INSTANCE._getLocation(cmdCtx, str);
        }
        
        /* 
         * ==========================
         *      Abstract methods
         * ==========================
         */
        
        protected abstract Location _getLocation(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException;
    }
}
