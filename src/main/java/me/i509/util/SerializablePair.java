package me.i509.util;

import java.io.Serializable;

public class SerializablePair<L extends Serializable, R extends Serializable> implements Serializable {
    
    private static final long serialVersionUID = 3047432818854638985L;
    
    private L left;
    private R right;
    
    private SerializablePair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L extends Serializable,R extends Serializable> SerializablePair<L,R> create(L left, R right) {
        return new SerializablePair<L,R>(left, right);
    }
    
    public L getLeft() {
        return this.left;
    }
    
    public R getRight() {
        return this.right;
    }
    
    public SerializablePair<R,L> swap() {
        return SerializablePair.create(this.right, this.left);
    }
}
