package org.example.input;

import org.example.treasuremap.*;

import java.util.List;

public class MapObjectFactory {

    public static MapObject create(String[] tokens, List<Mountain> mountains, List<Treasure> treasures, TreasureMap treasureMap) {
        return switch (tokens[0].trim()) {
            case "C" -> parseMap(tokens);
            case "M" -> parseMountain(tokens);
            case "T" -> parseTreasure(tokens);
            case "A" -> parseAdventurer(tokens, mountains, treasures, treasureMap);
            default -> throw new IllegalArgumentException("Invalid input: " + String.join(" - ", tokens));
        };
    }

    private static TreasureMap parseMap(String[] tokens) {
        int mapWidth = Integer.parseInt(tokens[1].trim());
        int mapHeight = Integer.parseInt(tokens[2].trim());
        return new TreasureMap(mapWidth, mapHeight);
    }

    private static Mountain parseMountain(String[] tokens) {
        int mountainX = Integer.parseInt(tokens[1].trim());
        int mountainY = Integer.parseInt(tokens[2].trim());
        return new Mountain(mountainX, mountainY);
    }

    private static Treasure parseTreasure(String[] tokens) {
        int treasureX = Integer.parseInt(tokens[1].trim());
        int treasureY = Integer.parseInt(tokens[2].trim());
        int numTreasures = Integer.parseInt(tokens[3].trim());
        return new Treasure(treasureX, treasureY, numTreasures);
    }

    private static Adventurer parseAdventurer(String[] tokens, List<Mountain> mountains, List<Treasure> treasures, TreasureMap treasureMap) {
        String adventurerName = tokens[1].trim();
        int adventurerX = Integer.parseInt(tokens[2].trim());
        int adventurerY = Integer.parseInt(tokens[3].trim());
        Orientation orientation = Orientation.fromCode(tokens[4]);
        String movementSequence = tokens[5].trim();

        if (!treasureMap.isValidMove(adventurerX, adventurerY) ||
                mountains.stream().anyMatch(m -> m.getX() == adventurerX && m.getY() == adventurerY)) {
            System.err.printf("Invalid starting position for adventurer %s at (%d, %d)%n", adventurerName, adventurerX, adventurerY);
            return null;
        }

        ObstacleDetector obstacleDetector = new MountainObstacleDetector(mountains);
        TreasureManager treasureManager = new SimpleTreasureManager(treasures);

        return new Adventurer(adventurerName, adventurerX, adventurerY, orientation,
                movementSequence, obstacleDetector, treasureManager, treasureMap);
    }
}
