package me.i509.brigwrapper.impl.v1_14_R1.arg;

import com.mojang.brigadier.arguments.ArgumentType;

import me.i509.brigwrapper.arguments.EntitySelectorWrapper;
import me.i509.brigwrapper.selectors.EntitySelectorType;
import net.minecraft.server.v1_14_R1.ArgumentEntity;

public class EntitySelectorWrapper_1_14_R1 extends EntitySelectorWrapper {
    private ArgumentEntity argNms;
    
    /*
     * a = true false -> only one ENTITY is allowed
     * b = false false -> multiple entities
     * c = true true -> only one PLAYER is allowed
     * d = false true -> multiple players
     */ 
    public EntitySelectorWrapper_1_14_R1(EntitySelectorType selectorType) {
        switch(selectorType) {
        case MANY_ENTITIES:
            argNms = ArgumentEntity.multipleEntities(); // b
            break;
        case MANY_PLAYERS:
            argNms = ArgumentEntity.d(); // d
            break;
        case ONE_ENTITY:
            argNms = ArgumentEntity.a(); // a
            break;
        case ONE_PLAYER:
            argNms = ArgumentEntity.c(); // c
            break;
        default:
            throw new IllegalArgumentException("Cannot create EntitySelectorWrapper with a null selector type");
        }
    }

    @Override
    public ArgumentType<?> getNMSType() {
        return argNms;
    }
}
