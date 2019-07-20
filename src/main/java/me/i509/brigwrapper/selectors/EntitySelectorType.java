package me.i509.brigwrapper.selectors;

public enum EntitySelectorType {
    /**Multiple entities, can include players*/MANY_ENTITIES, 
    /**Multiple players*/MANY_PLAYERS, 
    /**One entity only, can be a player*/ONE_ENTITY, 
    /**One player only*/ONE_PLAYER
}
