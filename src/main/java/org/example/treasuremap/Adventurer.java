package org.example.treasuremap;

public class Adventurer extends MapObject {
    private final String name;
    private Orientation orientation;
    private final String sequence;
    private int treasuresCollected;
    private int sequenceIndex;
    private final ObstacleDetector obstacleDetector;
    private final TreasureManager treasureManager;
    private final TreasureMap treasureMap;

    public Adventurer(String name, int x, int y, Orientation orientation, String sequence,
                      ObstacleDetector obstacleDetector, TreasureManager treasureManager, TreasureMap treasureMap) {
        super(x, y);
        this.name = name;
        this.orientation = orientation;
        this.sequence = sequence;
        this.treasuresCollected = 0;
        this.sequenceIndex = 0;
        this.obstacleDetector = obstacleDetector;
        this.treasureManager = treasureManager;
        this.treasureMap = treasureMap;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public String getSequence() {
        return sequence;
    }

    public int getSequenceIndex() {
        return sequenceIndex;
    }

    public int getTreasuresCollected() {
        return treasuresCollected;
    }

    public void moveForward() {
        int newX = x;
        int newY = y;

        switch (orientation) {
            case NORTH:
                newY -= 1;
                break;
            case SOUTH:
                newY += 1;
                break;
            case EAST:
                newX += 1;
                break;
            case WEST:
                newX -= 1;
                break;
        }


        if (treasureMap.isValidMove(newX, newY) && !treasureMap.isSquareOccupied(newX, newY)) {
            treasureMap.removeAdventurerPosition(this);

            x = newX;
            y = newY;

            treasureMap.addAdventurerPosition(this);
        }
    }


    public void turnLeft() {
        switch (orientation) {
            case NORTH:
                orientation = Orientation.WEST;
                break;
            case SOUTH:
                orientation = Orientation.EAST;
                break;
            case EAST:
                orientation = Orientation.NORTH;
                break;
            case WEST:
                orientation = Orientation.SOUTH;
                break;
        }
    }

    public void turnRight() {
        switch (orientation) {
            case NORTH:
                orientation = Orientation.EAST;
                break;
            case SOUTH:
                orientation = Orientation.WEST;
                break;
            case EAST:
                orientation = Orientation.SOUTH;
                break;
            case WEST:
                orientation = Orientation.NORTH;
                break;
        }
    }

    public void executeNextMovement() {
        if (sequenceIndex < sequence.length() && !obstacleDetector.hasObstacleAhead(x, y, orientation)) {
            char nextMove = sequence.charAt(sequenceIndex);
            switch (nextMove) {
                case 'A':
                    moveForward();
                    if (treasureManager.isTreasurePresent(x, y)) {
                        treasuresCollected++;
                    }
                    break;
                case 'G':
                    turnLeft();
                    break;
                case 'D':
                    turnRight();
                    break;
            }
        }
        sequenceIndex++;
    }

    public boolean hasFinishedSequence() {
        return sequenceIndex >= sequence.length();
    }

    @Override
    public String toString() {
        return String.format("A - %s - %d - %d - %s - %d", name, x, y, orientation, treasuresCollected);
    }
}
