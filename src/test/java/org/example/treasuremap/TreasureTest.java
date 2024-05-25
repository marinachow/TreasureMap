package org.example.treasuremap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TreasureTest {

    @Test
    public void testTreasureCreation() {
        Treasure treasure = new Treasure(1, 2, 3);
        assertEquals(1, treasure.getX());
        assertEquals(2, treasure.getY());
        assertEquals(3, treasure.getNumberOfTreasures());
    }

    @Test
    public void testCollectTreasure() {
        Treasure treasure = new Treasure(1, 2, 3);
        treasure.collect();
        assertEquals(2, treasure.getNumberOfTreasures());
    }
}
