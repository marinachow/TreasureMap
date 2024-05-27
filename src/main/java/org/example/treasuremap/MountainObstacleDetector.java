package org.example.treasuremap;

import java.util.List;

public class MountainObstacleDetector implements ObstacleDetector {
    private final List<Mountain> mountains;

    public MountainObstacleDetector(List<Mountain> mountains) {
        this.mountains = mountains;
    }

    @Override
    public boolean hasObstacleAhead(int x, int y, Orientation orientation) {
        int nextX = x;
        int nextY = y;

        switch (orientation) {
            case NORTH:
                nextY -= 1;
                break;
            case SOUTH:
                nextY += 1;
                break;
            case EAST:
                nextX += 1;
                break;
            case WEST:
                nextX -= 1;
                break;
        }

        for (Mountain mountain : mountains) {
            if (mountain.getX() == nextX && mountain.getY() == nextY) {
                return true;
            }
        }
        return false;
    }
}
