package org.example.output;

import org.example.treasuremap.MapObject;
import org.example.treasuremap.Treasure;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OutputWriter {

    public static void writeOutput(String outputFilePath, List<MapObject> mapObjects) throws IOException {
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
