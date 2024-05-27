package org.example.treasuremap;

public class Mountain extends MapObject {
    public Mountain(int x, int y) {
        super(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("M - %d - %d", x, y);
    }
}

