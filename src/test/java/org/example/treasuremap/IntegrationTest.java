package org.example.treasuremap;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {

    @Test
    public void testIntegration() throws IOException {
        Path inputFilePath = Files.createTempFile("input", ".txt");
        Path outputFilePath = Files.createTempFile("output", ".txt");

        String inputData = """
                C - 3 - 4
                M - 1 - 0
                M - 2 - 1
                T - 0 - 3 - 2
                T - 1 - 3 - 3
                A - Lara - 1 - 1 - S - AADADAGGA
                """;
        Files.writeString(inputFilePath, inputData);

        List<Object> mapObjects = new ArrayList<>();
        List<Mountain> mountains = new ArrayList<>();
        List<Treasure> treasures = new ArrayList<>();
        TreasureMap treasureMap = new TreasureMap(0, 0);

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }

                String[] tokens = line.split(" - ");

                switch (tokens[0].trim()) {
                    case "C":
                        int mapWidth = Integer.parseInt(tokens[1].trim());
                        int mapHeight = Integer.parseInt(tokens[2].trim());
                        treasureMap.setWidth(mapWidth);
                        treasureMap.setHeight(mapHeight);
                        mapObjects.add(treasureMap);
                        break;
                    case "M":
                        int mountainX = Integer.parseInt(tokens[1].trim());
                        int mountainY = Integer.parseInt(tokens[2].trim());
                        Mountain mountain = new Mountain(mountainX, mountainY);
                        mountains.add(mountain);
                        mapObjects.add(mountain);
                        break;
                    case "T":
                        int treasureX = Integer.parseInt(tokens[1].trim());
                        int treasureY = Integer.parseInt(tokens[2].trim());
                        int numTreasures = Integer.parseInt(tokens[3].trim());
                        Treasure treasure = new Treasure(treasureX, treasureY, numTreasures);
                        treasures.add(treasure);
                        mapObjects.add(treasure);
                        break;
                    case "A":
                        Adventurer adventurer = createAndMoveAdventurer(tokens, mountains, treasures, treasureMap);
                        mapObjects.add(adventurer);
                        break;
                    default:
                        System.err.println("Invalid input line: " + line);
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath.toFile()))) {
                for (Object obj : mapObjects) {
                    if (obj instanceof Treasure) {
                        Treasure treasure = (Treasure) obj;
                        if (treasure.getNumberOfTreasures() > 0) {
                            writer.write(treasure.toString());
                            writer.newLine();
                        }
                    } else {
                        writer.write(obj.toString());
                        writer.newLine();
                    }
                }
            }

            List<String> outputLines = Files.readAllLines(outputFilePath);
            List<String> expectedLines = List.of(
                    "C - 3 - 4",
                    "M - 1 - 0",
                    "M - 2 - 1",
                    "T - 1 - 3 - 2",
                    "A - Lara - 0 - 3 - S - 3"
            );

            assertEquals(expectedLines, outputLines);
        } finally {
            Files.deleteIfExists(inputFilePath);
            Files.deleteIfExists(outputFilePath);
        }
    }

    private Adventurer createAndMoveAdventurer(String[] tokens, List<Mountain> mountains, List<Treasure> treasures, TreasureMap treasureMap) {
        String adventurerName = tokens[1].trim();
        int adventurerX = Integer.parseInt(tokens[2].trim());
        int adventurerY = Integer.parseInt(tokens[3].trim());
        String orientation = tokens[4].trim();
        String movementSequence = tokens[5].trim();
        ObstacleDetector obstacleDetector = new MountainObstacleDetector(mountains);
        TreasureManager treasureManager = new SimpleTreasureManager(treasures);
        Adventurer adventurer = new Adventurer(adventurerName, adventurerX, adventurerY, orientation,
                movementSequence, obstacleDetector, treasureManager, treasureMap);
        while (!adventurer.hasFinishedSequence()) {
            adventurer.executeNextMovement();
        }
        return adventurer;
    }

    @Test
    public void testAdventurerBlockedByMountain() {
        TreasureMap treasureMap = new TreasureMap(3, 3);
        Mountain mountain = new Mountain(0, 1);
        List<Mountain> mountains = new ArrayList<>();
        mountains.add(mountain);

        Adventurer adventurer = new Adventurer("John", 0, 0, "S", "A", new MountainObstacleDetector(mountains), null, treasureMap);

        adventurer.executeNextMovement();

        assertEquals(0, adventurer.getX());
        assertEquals(0, adventurer.getY());
    }
}
