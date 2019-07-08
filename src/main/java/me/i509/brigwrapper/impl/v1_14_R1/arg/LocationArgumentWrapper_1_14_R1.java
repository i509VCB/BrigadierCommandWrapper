package me.i509.brigwrapper.impl.v1_14_R1.arg;

import org.bukkit.Location;
import org.bukkit.World;

import com.mojang.brigadier.arguments.ArgumentType;

import me.i509.brigwrapper.arguments.LocationArgumentWrapper;
import me.i509.brigwrapper.selectors.LocationType;
import net.minecraft.server.v1_14_R1.ArgumentPosition;
import net.minecraft.server.v1_14_R1.ArgumentVec2;
import net.minecraft.server.v1_14_R1.ArgumentVec2I;
import net.minecraft.server.v1_14_R1.ArgumentVec3;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.BlockPosition2D;
import net.minecraft.server.v1_14_R1.Vec2F;
import net.minecraft.server.v1_14_R1.Vec3D;

public class LocationArgumentWrapper_1_14_R1 extends LocationArgumentWrapper {

    public LocationArgumentWrapper_1_14_R1(LocationType argtype) {
        argType = argtype;
    }

    @Override
    public ArgumentType<?> getNMSType() {
        switch(argType) {
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
