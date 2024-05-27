package org.example.treasuremap;

public class Treasure extends MapObject {
    private int numberOfTreasures;

    public Treasure(int x, int y, int numberOfTreasures) {
        super(x, y);
        this.numberOfTreasures = numberOfTreasures;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNumberOfTreasures() {
        return numberOfTreasures;
    }

    public void collect() {
        if (numberOfTreasures > 0) {
            numberOfTreasures--;
        }
    }

    @Override
    public String toString() {
        return String.format("T - %d - %d - %d", x, y, numberOfTreasures);
    }
}
