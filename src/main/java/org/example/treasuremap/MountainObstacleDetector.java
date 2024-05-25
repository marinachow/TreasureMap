package org.example.treasuremap;

import java.util.List;

public class MountainObstacleDetector implements ObstacleDetector {
    private List<Mountain> mountains;

    public MountainObstacleDetector(List<Mountain> mountains) {
        this.mountains = mountains;
    }

    @Override
    public boolean hasObstacleAhead(int x, int y, String orientation) {
        int nextX = x;
        int nextY = y;

        switch (orientation) {
            case "N":
                nextY -= 1;
                break;
            case "S":
                nextY += 1;
                break;
            case "E":
                nextX += 1;
                break;
            case "W":
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
