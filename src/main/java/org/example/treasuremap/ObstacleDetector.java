package org.example.treasuremap;

public interface ObstacleDetector {
    boolean hasObstacleAhead(int x, int y, Orientation orientation);
}
