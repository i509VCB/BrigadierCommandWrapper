package me.i509.brigwrapper;

import org.bukkit.permissions.Permission;

public class CommandPermission {
    private boolean isString = false;
    private boolean noPerm = false;
    private PermissionType type;
    private String permissionString;

    protected CommandPermission(PermissionType type) {
        this.type = type;
    }

    protected CommandPermission(String string) {
        isString = true;
        permissionString = string;
    }
    
    protected CommandPermission() {
        noPerm = true;
    }

    public static CommandPermission ofEmpty() {
        return new CommandPermission();
    }

    public static CommandPermission of(PermissionType type) {
        return new CommandPermission(type);
    }
    
    public static CommandPermission of(String string) {
        return new CommandPermission(string);
    }
    
    public String asString() {
        return permissionString;
    }

    public PermissionType type() {
        return type;
    }
    
    public boolean noPermissionNeeded() {
        return noPerm;
    }

    public boolean isString() {
        return isString ;
    }

    public enum PermissionType {
        OP, CONSOLE, COMMAND_BLOCK
    }
}

    
