package org.example.treasuremap;

public class Adventurer extends MapObject {
    private final String name;
    private String orientation;
    private final String sequence;
    private int treasuresCollected;
    private int sequenceIndex;
    private final ObstacleDetector obstacleDetector;
    private final TreasureManager treasureManager;
    private final TreasureMap treasureMap;

    public Adventurer(String name, int x, int y, String orientation, String sequence,
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

    public String getOrientation() {
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
            case "N":
                newY -= 1;
                break;
            case "S":
                newY += 1;
                break;
            case "E":
                newX += 1;
                break;
            case "W":
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
            case "N":
                orientation = "W";
                break;
            case "S":
                orientation = "E";
                break;
            case "E":
                orientation = "N";
                break;
            case "W":
                orientation = "S";
                break;
        }
    }

    public void turnRight() {
        switch (orientation) {
            case "N":
                orientation = "E";
                break;
            case "S":
                orientation = "W";
                break;
            case "E":
                orientation = "S";
                break;
            case "W":
                orientation = "N";
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
