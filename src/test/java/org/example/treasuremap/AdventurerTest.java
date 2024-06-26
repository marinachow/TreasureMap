package org.example.treasuremap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AdventurerTest {

    @Mock
    ObstacleDetector obstacleDetector;
    @Mock
    TreasureManager treasureManager;

    private TreasureMap treasureMap;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        treasureMap = new TreasureMap(5, 5);
    }

    @Test
    public void testAdventurerCreation() {
        Adventurer adventurer = new Adventurer("John", 0, 0, Orientation.NORTH, "AADADA",
                obstacleDetector, treasureManager, treasureMap);
        assertEquals("John", adventurer.getName());
        assertEquals(0, adventurer.getX());
        assertEquals(0, adventurer.getY());
        assertEquals(Orientation.NORTH, adventurer.getOrientation());
        assertEquals("AADADA", adventurer.getSequence());
    }

    @Test
    public void testMoveForward() {
        Adventurer adventurer = new Adventurer("John", 1, 2, Orientation.SOUTH, "A",
                obstacleDetector, treasureManager, treasureMap);
        adventurer.executeNextMovement();
        assertEquals(1, adventurer.getX());
        assertEquals(3, adventurer.getY());
    }

    @Test
    public void testTurnLeft() {
        Adventurer adventurer = new Adventurer("John", 0, 0, Orientation.NORTH, "G",
                obstacleDetector, treasureManager, treasureMap);
        adventurer.executeNextMovement();
        assertEquals(Orientation.WEST, adventurer.getOrientation());
    }

    @Test
    public void testTurnRight() {
        Adventurer adventurer = new Adventurer("John", 0, 0, Orientation.NORTH, "D",
                obstacleDetector, treasureManager, treasureMap);
        adventurer.executeNextMovement();
        assertEquals(Orientation.EAST, adventurer.getOrientation());
    }

    @Test
    public void testExecuteEntireSequence() {
        Adventurer adventurer = new Adventurer("Lara", 1, 1, Orientation.SOUTH, "AADADAGGA",
                obstacleDetector, treasureManager, treasureMap);
        while (!adventurer.hasFinishedSequence()) {
            adventurer.executeNextMovement();
        }
        assertEquals(0, adventurer.getX());
        assertEquals(3, adventurer.getY());
        assertEquals(Orientation.SOUTH, adventurer.getOrientation());
    }

    @Test
    public void testExecuteNextMovement_NoObstacle() {
        when(obstacleDetector.hasObstacleAhead(anyInt(), anyInt(), any(Orientation.class))).thenReturn(false);

        Adventurer adventurer = new Adventurer("John", 1, 2, Orientation.NORTH, "A",
                obstacleDetector, treasureManager, treasureMap);
        adventurer.executeNextMovement();

        assertEquals(1, adventurer.getX());
        assertEquals(1, adventurer.getY());
        assertEquals(1, adventurer.getSequenceIndex());
    }

    @Test
    public void testExecuteNextMovement_WithObstacle() {
        when(obstacleDetector.hasObstacleAhead(anyInt(), anyInt(), any(Orientation.class))).thenReturn(true);

        Adventurer adventurer = new Adventurer("John", 0, 0, Orientation.NORTH, "A",
                obstacleDetector, treasureManager, treasureMap);
        adventurer.executeNextMovement();

        assertEquals(0, adventurer.getX());
        assertEquals(0, adventurer.getY());
        assertEquals(1, adventurer.getSequenceIndex());
    }

    @Test
    public void testExecuteNextMovement_NegativeCoordinates() {
        when(obstacleDetector.hasObstacleAhead(anyInt(), anyInt(), any(Orientation.class))).thenReturn(false);

        Adventurer adventurer = new Adventurer("John", 0, 0, Orientation.NORTH, "A",
                obstacleDetector, treasureManager, treasureMap);
        adventurer.executeNextMovement();

        assertEquals(0, adventurer.getX());
        assertEquals(0, adventurer.getY());
    }

    @Test
    public void testMoveForward_AvoidCollision() {
        Adventurer adventurer1 = new Adventurer("John", 1, 1, Orientation.SOUTH, "A", obstacleDetector, treasureManager, treasureMap);
        Adventurer adventurer2 = new Adventurer("Alice", 1, 3, Orientation.NORTH, "A", obstacleDetector, treasureManager, treasureMap);
        adventurer1.executeNextMovement();
        adventurer2.executeNextMovement();

        assertFalse(adventurer1.getX() == adventurer2.getX() && adventurer1.getY() == adventurer2.getY());
    }

    @Test
    public void testTreasureCollectionAfterMovements() {
        Treasure treasure = new Treasure(1, 1, 2);
        List<Treasure> treasures = new ArrayList<>();
        treasures.add(treasure);
        TreasureManager treasureManager = new SimpleTreasureManager(treasures);

        Adventurer adventurer = new Adventurer("John", 1, 2, Orientation.NORTH, "A", obstacleDetector, treasureManager, treasureMap);

        adventurer.executeNextMovement();
        assertEquals(1, treasure.getNumberOfTreasures());
        assertEquals(1, adventurer.getTreasuresCollected());
    }

}
