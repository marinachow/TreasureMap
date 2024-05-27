package org.example.input;

import org.example.treasuremap.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputReader {

    public static InputData readInput(String inputFilePath) throws IOException {
        List<MapObject> mapObjects = new ArrayList<>();
        List<Mountain> mountains = new ArrayList<>();
        List<Treasure> treasures = new ArrayList<>();
        List<Adventurer> adventurers = new ArrayList<>();
        TreasureMap treasureMap = null;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) continue;

                String[] tokens = line.split(" - ");
                MapObject mapObject = MapObjectFactory.create(tokens, mountains, treasures, treasureMap);

                if (mapObject instanceof TreasureMap) {
                    treasureMap = (TreasureMap) mapObject;
                } else if (mapObject instanceof Mountain) {
                    mountains.add((Mountain) mapObject);
                } else if (mapObject instanceof Treasure) {
                    treasures.add((Treasure) mapObject);
                } else if (mapObject instanceof Adventurer) {
                    adventurers.add((Adventurer) mapObject);
                }

                if (mapObject != null) {
                    mapObjects.add(mapObject);
                }
            }
        }

        return new InputData(mapObjects,adventurers);
    }
}
