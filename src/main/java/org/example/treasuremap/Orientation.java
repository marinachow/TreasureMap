package org.example.treasuremap;

public enum Orientation {
    NORTH("N"), SOUTH("S"), EAST("E"), WEST("O");

    private final String code;

    Orientation(String code) {
        this.code = code;
    }

    public static Orientation fromCode(String code) {
        for (Orientation orientation : values()) {
            if (orientation.code.equals(code)) {
                return orientation;
            }
        }
        throw new IllegalArgumentException("Invalid orientation code: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}
