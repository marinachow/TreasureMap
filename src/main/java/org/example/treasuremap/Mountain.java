package org.example.treasuremap;

public class Mountain {
    private int x;
    private int y;

    public Mountain(int x, int y) {
        this.x = x;
        this.y = y;
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

