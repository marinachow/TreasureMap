package org.example.treasuremap;

import org.example.main.Main;
import org.junit.jupiter.api.Test;

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

        Main.main(new String[]{inputFilePath.toString(), outputFilePath.toString()});

        List<String> outputLines = Files.readAllLines(outputFilePath);
        List<String> expectedLines = List.of(
                "C - 3 - 4",
                "M - 1 - 0",
                "M - 2 - 1",
                "T - 1 - 3 - 2",
                "A - Lara - 0 - 3 - S - 3"
        );

        assertEquals(expectedLines, outputLines);
    }

    @Test
    public void testComplexIntegration() throws IOException {
        Path inputFilePath = Files.createTempFile("inputComplex", ".txt");
        Path outputFilePath = Files.createTempFile("outputComplex", ".txt");

        String inputData = """
            C - 5 - 5
            M - 1 - 1
            M - 3 - 3
            T - 2 - 2 - 1
            T - 4 - 4 - 2
            A - Lara - 0 - 0 - E - AADAADAD
            A - John - 4 - 4 - W - AAGGAAGGA
            """;
        Files.writeString(inputFilePath, inputData);

        Main.main(new String[]{inputFilePath.toString(), outputFilePath.toString()});

        List<String> outputLines = Files.readAllLines(outputFilePath);
        List<String> expectedLines = List.of(
                "C - 5 - 5",
                "M - 1 - 1",
                "M - 3 - 3",
                "T - 4 - 4 - 1",
                "A - Lara - 1 - 2 - N - 1",
                "A - John - 3 - 4 - W - 1"
        );

        assertEquals(expectedLines, outputLines);

        Files.deleteIfExists(inputFilePath);
        Files.deleteIfExists(outputFilePath);
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
