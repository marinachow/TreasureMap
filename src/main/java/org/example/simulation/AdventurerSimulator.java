package org.example.simulation;

import org.example.treasuremap.Adventurer;

import java.util.List;

public class AdventurerSimulator {
    private final List<Adventurer> adventurers;

    public AdventurerSimulator(List<Adventurer> adventurers) {
        this.adventurers = adventurers;
    }

    public void simulate() {
        boolean anyAdventurerHasMoves;
        do {
            anyAdventurerHasMoves = false;
            for (Adventurer adventurer : adventurers) {
                if (!adventurer.hasFinishedSequence()) {
                    adventurer.executeNextMovement();
                    anyAdventurerHasMoves = true;
                }
            }
        } while (anyAdventurerHasMoves);
    }
}
