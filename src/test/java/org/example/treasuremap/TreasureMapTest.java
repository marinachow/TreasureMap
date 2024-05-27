package org.example.treasuremap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TreasureMapTest {

    @Test
    public void testMapCreation() {
        TreasureMap treasureMap = new TreasureMap(5, 4);
        assertEquals(5, treasureMap.getWidth());
        assertEquals(4, treasureMap.getHeight());
    }

    @Test
    public void testMapBoundaryConditions() {
        TreasureMap treasureMap = new TreasureMap(1, 1);
        assertTrue(treasureMap.isValidMove(0, 0));
        assertFalse(treasureMap.isValidMove(1, 0));
        assertFalse(treasureMap.isValidMove(0, 1));

        TreasureMap largeMap = new TreasureMap(1000, 1000);
        assertTrue(largeMap.isValidMove(999, 999));
        assertFalse(largeMap.isValidMove(1000, 1000));
    }

}

