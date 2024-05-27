package org.example.treasuremap;

import java.util.List;

public class SimpleTreasureManager implements TreasureManager {
    private final List<Treasure> treasures;

    public SimpleTreasureManager(List<Treasure> treasures) {
        this.treasures = treasures;
    }

    @Override
    public boolean isTreasurePresent(int x, int y) {
        for (Treasure treasure : treasures) {
            if (treasure.getX() == x && treasure.getY() == y && treasure.getNumberOfTreasures() > 0) {
                treasure.collect();
                return true;
            }
        }
        return false;
    }
}
