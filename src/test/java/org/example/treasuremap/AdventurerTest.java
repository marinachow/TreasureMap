package org.example.treasuremap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AdventurerTest {

    @Mock
    ObstacleDetector obstacleDetector;
    @Mock
    TreasureManager treasureManager;
    @Mock
    Map map;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(map.getWidth()).thenReturn(5);
        when(map.getHeight()).thenReturn(5);
    }

    @Test
    public void testAdventurerCreation() {
        Adventurer adventurer = new Adventurer("John", 0, 0, "N", "AADADA",
                obstacleDetector, treasureManager, map);
        assertEquals("John", adventurer.getName());
        assertEquals(0, adventurer.getX());
        assertEquals(0, adventurer.getY());
        assertEquals("N", adventurer.getOrientation());
        assertEquals("AADADA", adventurer.getSequence());
    }

    @Test
    public void testMoveForward() {
        Adventurer adventurer = new Adventurer("John", 1, 2, "S", "A",
                obstacleDetector, treasureManager, map);
        adventurer.executeNextMovement();
        System.out.println(map);
        assertEquals(1, adventurer.getX());
        assertEquals(3, adventurer.getY());
    }

    @Test
    public void testTurnLeft() {
        Adventurer adventurer = new Adventurer("John", 0, 0, "N", "G",
                obstacleDetector, treasureManager, map);
        adventurer.executeNextMovement();
        assertEquals("W", adventurer.getOrientation());
    }

    @Test
    public void testTurnRight() {
        Adventurer adventurer = new Adventurer("John", 0, 0, "N", "D",
                obstacleDetector, treasureManager, map);
        adventurer.executeNextMovement();
        assertEquals("E", adventurer.getOrientation());
    }

    @Test
    public void testExecuteEntireSequence() {
        Adventurer adventurer = new Adventurer("Lara", 1, 1, "S", "AADADAGGA",
                obstacleDetector, treasureManager, map);
        while (!adventurer.hasFinishedSequence()) {
            adventurer.executeNextMovement();
        }
        assertEquals(0, adventurer.getX());
        assertEquals(3, adventurer.getY());
        assertEquals("S", adventurer.getOrientation());
    }

    @Test
    public void testExecuteNextMovement_NoObstacle() {
        when(obstacleDetector.hasObstacleAhead(anyInt(), anyInt(), anyString())).thenReturn(false);

        Adventurer adventurer = new Adventurer("John", 1, 2, "N", "A",
                obstacleDetector, treasureManager, map);
        adventurer.executeNextMovement();

        assertEquals(1, adventurer.getX());
        assertEquals(1, adventurer.getY());
        assertEquals(1, adventurer.getSequenceIndex());
    }

    @Test
    public void testExecuteNextMovement_WithObstacle() {
        when(obstacleDetector.hasObstacleAhead(anyInt(), anyInt(), anyString())).thenReturn(true);

        Adventurer adventurer = new Adventurer("John", 0, 0, "N", "A",
                obstacleDetector, treasureManager, map);
        adventurer.executeNextMovement();

        assertEquals(0, adventurer.getX());
        assertEquals(0, adventurer.getY());
        assertEquals(1, adventurer.getSequenceIndex());
    }

    @Test
    public void testExecuteNextMovement_NegativeCoordinates() {
        when(obstacleDetector.hasObstacleAhead(anyInt(), anyInt(), anyString())).thenReturn(false);

        Adventurer adventurer = new Adventurer("John", 0, 0, "N", "A",
                obstacleDetector, treasureManager, map);
        adventurer.executeNextMovement();

        assertEquals(0, adventurer.getX());
        assertEquals(0, adventurer.getY());
    }
}