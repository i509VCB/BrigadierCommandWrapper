package me.i509.brigwrapper.impl.v1_14_R1;

import org.bukkit.Location;
import org.bukkit.World;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.CommandSource;
import me.i509.brigwrapper.arguments.LocationArgumentWrapper;
import net.minecraft.server.v1_14_R1.ArgumentPosition;
import net.minecraft.server.v1_14_R1.ArgumentVec2;
import net.minecraft.server.v1_14_R1.ArgumentVec2I;
import net.minecraft.server.v1_14_R1.ArgumentVec3;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.BlockPosition2D;
import net.minecraft.server.v1_14_R1.Vec2F;
import net.minecraft.server.v1_14_R1.Vec3D;

public class LocationArgumentWrapper_1_14_R1 extends LocationArgumentWrapper {

    @Override
    public ArgumentType<?> _getNMSType(LocationType type) {
        switch(type) {
        case BLOCK:
            return ArgumentPosition.a();
        case BLOCK_2D:
            return ArgumentVec2I.a();
        case PRECISE:
            return ArgumentVec3.a();
        case PRECISE_2D:
            return ArgumentVec2.a();
        default:
            throw new IllegalArgumentException("Cannot create LocationArgumentWrapper with a null location type");
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected Location _getLocationVec3(CommandContext cmdCtx, String str) throws CommandSyntaxException {
        return LocationArgumentWrapper_1_14_R1.fromVec3(CommandSource.getSource(cmdCtx).getWorld(), ArgumentVec3.a(cmdCtx, str));
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected Location _getLocationVec2(CommandContext cmdCtx, String str) throws CommandSyntaxException {
        return LocationArgumentWrapper_1_14_R1.fromVec2F(CommandSource.getSource(cmdCtx).getWorld(), ArgumentVec2.a(cmdCtx, str));
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected Location _getLocationBlockPos(CommandContext cmdCtx, String str) throws CommandSyntaxException {
        return LocationArgumentWrapper_1_14_R1.fromBlockPos(CommandSource.getSource(cmdCtx).getWorld(), ArgumentPosition.a(cmdCtx, str));
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected Location _getLocationBlockPos2D(CommandContext cmdCtx, String str) throws CommandSyntaxException {
        return LocationArgumentWrapper_1_14_R1.fromBlockPos2D(CommandSource.getSource(cmdCtx).getWorld(), ArgumentVec2I.a(cmdCtx, str));
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected Location _getFromContext(CommandContext cmdCtx, String str, LocationType type) throws CommandSyntaxException {
        
        Object loc = cmdCtx.getArgument(str, getClazzFromType(type));
        
        if(loc instanceof BlockPosition) {
            return _getLocationBlockPos(cmdCtx, str);
        } else if (loc instanceof BlockPosition2D) {
            return _getLocationBlockPos2D(cmdCtx, str);
        } else if (loc instanceof Vec3D) {
            return _getLocationVec3(cmdCtx, str);
        } else if (loc instanceof Vec2F) {
            return _getLocationVec2(cmdCtx, str);
        }
        throw new IllegalArgumentException("Invalid LocationType");
    }
    
    private static Class getClazzFromType(LocationType loc) {
        switch(loc) {
        case BLOCK:
            return BlockPosition.class;
        case BLOCK_2D:
            return BlockPosition2D.class;
        case PRECISE:
            return Vec3D.class;
        case PRECISE_2D:
            return Vec2F.class;
        default:
            throw new IllegalArgumentException("");
        }
    }

    public static Location fromBlockPos(World bukkitworld, BlockPosition pos) {
        return new Location(bukkitworld, pos.getX(), pos.getY(), pos.getZ());
    }

    public static Location fromVec3(World bukkitworld, Vec3D vec3) {
        return new Location(bukkitworld, vec3.getX(), vec3.getY(), vec3.getZ());
    }

    public static Location fromBlockPos2D(World bukkitworld, BlockPosition2D pos2) {
        return new Location(bukkitworld, pos2.a, 0, pos2.b);
    }

    public static Location fromVec2F(World bukkitworld, Vec2F pos2) {
        return new Location(bukkitworld, pos2.i, 0, pos2.j);
    }
    
}
