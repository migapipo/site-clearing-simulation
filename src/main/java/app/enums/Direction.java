package app.enums;

public enum Direction {
    NORTH(0), EAST(90), SOUTH(180), WEST(270);

    private int degree;

    Direction(int degree) {
        this.degree = degree;
    }

    // get Direction from degree
    public static Direction getDirection(int degree) {

        for (Direction direction : Direction.values()) {
            if (direction.getDegree() == degree) {
                return direction;
            }
        }
        return null;
    }

    public int getDegree() {
        return this.degree;
    }

}
