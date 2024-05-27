package org.example.input;

import org.example.treasuremap.*;

import java.util.List;

public class InputData {
    private final List<MapObject> mapObjects;
    private final List<Adventurer> adventurers;

    public InputData(List<MapObject> mapObjects, List<Adventurer> adventurers) {
        this.mapObjects = mapObjects;
        this.adventurers = adventurers;
    }

    public List<MapObject> getMapObjects() {
        return mapObjects;
    }

    public List<Adventurer> getAdventurers() {
        return adventurers;
    }
}
