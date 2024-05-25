package org.example.treasuremap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreasureMap {
    private int width;
    private int height;
    private final Map<String, List<Adventurer>> adventurerPositions;

    public TreasureMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.adventurerPositions = new HashMap<>();
    }

    public boolean isValidMove(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean isSquareOccupied(int x, int y) {
        return adventurerPositions.containsKey(getCoordinateKey(x, y));
    }

    public void addAdventurerPosition(Adventurer adventurer) {
        String key = getCoordinateKey(adventurer.getX(), adventurer.getY());
        adventurerPositions.computeIfAbsent(key, k -> new ArrayList<>()).add(adventurer);
    }

    public void removeAdventurerPosition(Adventurer adventurer) {
        String key = getCoordinateKey(adventurer.getX(), adventurer.getY());
        List<Adventurer> adventurers = adventurerPositions.get(key);
        if (adventurers != null) {
            adventurers.remove(adventurer);
            if (adventurers.isEmpty()) {
                adventurerPositions.remove(key);
            }
        }
    }

    private String getCoordinateKey(int x, int y) {
        return x + "-" + y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return String.format("C - %d - %d", width, height);
    }
}