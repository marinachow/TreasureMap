package org.example.treasuremap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapTest {

    @Test
    public void testMapCreation() {
        Map map = new Map(5, 4);
        assertEquals(5, map.getWidth());
        assertEquals(4, map.getHeight());
    }
}

