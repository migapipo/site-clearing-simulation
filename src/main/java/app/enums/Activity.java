package app.enums;

public enum Activity {

    CLEARING_PLAIN_SQUARE_OR_VISITING_CLEARED_SQUARE(1),
    CLEARING_ROCKY_SQUARE(2), CLEARING_TREE_SQUARE(2), IDLE(0);

    private final int fuelUsage;

    Activity(int fuelUsage) {
        this.fuelUsage = fuelUsage;
    }

    public int getFuelUsage() {
        return fuelUsage;
    }
}
