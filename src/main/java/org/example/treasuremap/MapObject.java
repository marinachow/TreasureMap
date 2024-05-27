package org.example.treasuremap;

public abstract class MapObject {
    protected int x;
    protected int y;

    public MapObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

