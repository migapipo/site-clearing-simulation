package app.entity;


import app.controller.GameController;
import app.enums.Activity;
import app.enums.Direction;
import app.enums.Item;
import app.enums.SquareType;
import app.exceptions.CutTreeException;
import app.exceptions.OutOfBoundaryException;

import java.util.Map;

/**
 * Bulldozer class contains information about bulldozer location, direction and methods used to update location and
 * direction and calculate the cost as it moves.
 */
public class Bulldozer {

    private int location;
    private Direction direction;

    public Bulldozer() {
        this.location = -1;
        this.direction = Direction.EAST;
    }

    public Bulldozer(int location, Direction direction) {
        this.location = location;
        this.direction = direction;
    }

    /**
     * Update location and direction after one movement on the siteMap
     * @param siteMap
     */
    private void nextLocation(String[][] siteMap) throws OutOfBoundaryException {
        int rows = siteMap.length;
        int cols = siteMap[0].length;
        int[] coordinate = SiteMap.decodeLocation(this.direction, this.location);
        int r = coordinate[0], c = coordinate[1];
        // according to direction, r - row and c - column change correspondingly
        if (this.direction == Direction.NORTH) {
            r -= 1;
        } else if (this.direction == Direction.EAST) {
            c += 1;
        } else if (this.direction == Direction.SOUTH) {
            r += 1;
        } else if (this.direction == Direction.WEST) {
            c -= 1;
        }

        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            throw new OutOfBoundaryException(String.format("Bulldozer is out of boundary at (%d, %d)", r, c));
        }
        this.location = SiteMap.encodeCoordinates(r, c);
        return;
    }

    private void updateDirection(DirectionCommand directionCommand) {
        this.direction = directionCommand.convertDegree(this.direction);
        return;
    }

    /**
     * Compute cost each step the bulldozer moves and record it in the costMap.4 Square Types are considered: plain
     * land, land that has already been cleared, rocky land, land containing a removable tree. Cost of each step
     * depends on the square type and whether this square has been visited or not.
     *
     * @param costMap
     * @param siteMap
     * @param visited
     * @param remainStep
     * @throws CutTreeException
     */
    private void computeCostPerStep(Map<Item, Integer> costMap, String[][] siteMap,
                                    boolean[][] visited, int remainStep) throws CutTreeException {
        int[] coordinate = SiteMap.decodeLocation(this.direction, this.location);
        int r = coordinate[0], c = coordinate[1];
        // case 1 : "O" or visited
        if (siteMap[r][c].equals(SquareType.PLAIN.getSquareType()) || visited[r][c]) {
            visited[r][c] = true;
            int fuelCount = Activity.CLEARING_PLAIN_SQUARE_OR_VISITING_CLEARED_SQUARE.getFuelUsage();
            costMap.put(Item.FUEL_USAGE,
                    costMap.get(Item.FUEL_USAGE) + Item.FUEL_USAGE.getCostUnit() * fuelCount);
            return;
        }
        // case 2 : "r"
        if (siteMap[r][c].equals(SquareType.ROCKY.getSquareType())) {
            visited[r][c] = true;
            int fuelCount = Activity.CLEARING_ROCKY_SQUARE.getFuelUsage();
            costMap.put(Item.FUEL_USAGE, costMap.get(Item.FUEL_USAGE) + Item.FUEL_USAGE.getCostUnit() * fuelCount);
            return;
        }
        // case 3 : "t"
        if (siteMap[r][c].equals(SquareType.TREE_REMOVABLE.getSquareType())) {
            visited[r][c] = true;
            int fuelCount = Activity.CLEARING_TREE_SQUARE.getFuelUsage();
            if (remainStep == 0) {
                costMap.put(Item.PAINT_DAMAGE_TO_BULLDOZER, costMap.get(Item.PAINT_DAMAGE_TO_BULLDOZER) + 1);
            }
            costMap.put(Item.FUEL_USAGE, costMap.get(Item.FUEL_USAGE) + Item.FUEL_USAGE.getCostUnit() * fuelCount);
            return;
        }
        // case 4: "T"
        if (siteMap[r][c].equals(SquareType.TREE_PRESERVED.getSquareType())) {
            throw new CutTreeException(String.format("the current location(%d, %d) is a protected tree", r, c));
        }
        return;
    }

    /**
     * Execute the command with the game app.controller as the context.
     * 1. if the command type is a direction command. update the direction.
     * 2. if the command type is a advance command.
     * for step in steps:
     * 1. update next location.
     * 2. check the Square type of next location.
     * 3. compute the cost of fuel based on the Square type and update the costMap based on the ItemMap
     *
     * @param command
     * @param controller
     */
    public void execute(Commands command, GameController controller) throws OutOfBoundaryException, CutTreeException {
        String[][] siteMap = controller.getSiteMap();
        Map<Item, Integer> costMap = controller.getCostMap();
        boolean[][] visited = controller.getVisited();

        if (command instanceof DirectionCommand) {
            this.updateDirection((DirectionCommand) command);
            return;
        }
        if (command instanceof AdvanceCommand) {
            AdvanceCommand moveCommand = (AdvanceCommand) command;
            int steps = moveCommand.getAdvanceSteps();

            for (int step = 0; step < steps; step++) {
                this.nextLocation(siteMap);
                this.computeCostPerStep(costMap, siteMap, visited, steps - step - 1);
            }
        }
        return;
    }

    public int getLocation() {
        return location;
    }

    public Direction getDirection() {
        return direction;
    }
}
