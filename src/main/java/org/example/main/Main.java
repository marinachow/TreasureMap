package org.example.main;

import org.example.simulation.AdventurerSimulator;
import org.example.input.InputData;
import org.example.input.InputReader;
import org.example.output.OutputWriter;
import org.example.treasuremap.*;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String inputFilePath;
        String outputFilePath;

        if (args.length >= 2) {
            inputFilePath = args[0];
            outputFilePath = args[1];
        } else {
            inputFilePath = "./input.txt";
            outputFilePath = "./output.txt";
        }

        try {
            InputData inputData = InputReader.readInput(inputFilePath);

            List<Adventurer> adventurers = inputData.getAdventurers();
            AdventurerSimulator adventurerSimulator = new AdventurerSimulator(adventurers);
            adventurerSimulator.simulate();

            OutputWriter.writeOutput(outputFilePath, inputData.getMapObjects());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
