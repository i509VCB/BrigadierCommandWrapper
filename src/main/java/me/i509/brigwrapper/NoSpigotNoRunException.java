package me.i509.brigwrapper;

public class NoSpigotNoRunException extends RuntimeException {
    private static final long serialVersionUID = -3157830411509314810L;
    
    public NoSpigotNoRunException() {
        super("This plugin doesn't support CraftBukkit due to the confirmation system.\n"
                + "Also starting in 1.14 Spigot devs reccomend not using CraftBukkit and instead to use Spigot or my prefrence to use Paper instead");
    }
}
