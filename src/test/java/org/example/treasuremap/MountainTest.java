package org.example.treasuremap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MountainTest {

    @Test
    public void testMountainCreation() {
        Mountain mountain = new Mountain(2, 3);
        assertEquals(2, mountain.getX());
        assertEquals(3, mountain.getY());
    }
}

