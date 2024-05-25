package org.example;

import org.example.treasuremap.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String inputFilePath = "./input.txt";
        String outputFilePath = "./output.txt";
        List<Object> mapObjects = new ArrayList<>();
        List<Mountain> mountains = new ArrayList<>();
        List<Treasure> treasures = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }

                String[] tokens = line.split(" - ");

                switch (tokens[0].trim()) {
                    case "C":
                        mapObjects.add(parseMap(tokens));
                        break;
                    case "M":
                        Mountain mountain = parseMountain(tokens);
                        mountains.add(mountain);
                        mapObjects.add(mountain);
                        break;
                    case "T":
                        Treasure treasure = parseTreasure(tokens);
                        treasures.add(treasure);
                        mapObjects.add(treasure);
                        break;
                    case "A":
                        Object firstObject = mapObjects.get(0);
                        if (firstObject instanceof TreasureMap) {
                            mapObjects.add(parseAdventurer(tokens, mountains, treasures, (TreasureMap) firstObject));
                        } else {
                            System.err.println("Invalid input: " + firstObject + " must be a map");
                        }
                        break;
                    default:
                        System.err.println("Invalid input line: " + line);
                }
            }

            writeOutput(outputFilePath, mapObjects);

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
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

    private static void writeOutput(String outputFilePath, List<Object> mapObjects) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
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
    }
}
