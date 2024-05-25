package org.example.treasuremap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreasureMapTest {

    @Test
    public void testMapCreation() {
        TreasureMap treasureMap = new TreasureMap(5, 4);
        assertEquals(5, treasureMap.getWidth());
        assertEquals(4, treasureMap.getHeight());
    }
}

