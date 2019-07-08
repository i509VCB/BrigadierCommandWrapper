package me.i509.brigwrapper.util;

public class Pair<L, R> {
    
    private L left;
    private R right;

    public Pair(L l, R r) {
        this.left = l;
        this.right = r;
    }

    public static <L,R> Pair<L,R> create(L l, R r){
        return new Pair<L, R>(l,r);
    }

    public L getLeft() {
        return left;
    }
    
    public R getRight() {
        return right;
    }

}
