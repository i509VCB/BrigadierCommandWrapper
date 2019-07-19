package me.i509.brigwrapper.util;

public class MissingDependancyException extends RuntimeException {
    public MissingDependancyException(String string) {
        super(string);
    }

    private static final long serialVersionUID = 5488241828439158705L;
}
