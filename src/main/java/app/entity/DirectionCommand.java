package app.entity;

import app.enums.CommandType;
import app.enums.Direction;

public class DirectionCommand extends Commands {

    private String direction; // left or right;


    public DirectionCommand (String direction){
        super();
        this.direction = direction;
    }

    /**
     * Given a current direction [WEST/EAST/NORTH/], return the new direction base on the
     * current directionCommand
     * @param
     * @param currentDirection
     * @return
     */
    public Direction convertDegree( Direction currentDirection) {
        if (this.direction.equals(CommandType.TURN_LEFT.value)) {
            int updateDegree = (currentDirection.getDegree() - 90) % 360;
            updateDegree = updateDegree < 0 ? updateDegree + 360 : updateDegree;
            return Direction.getDirection(updateDegree);
        }
        return Direction.getDirection((currentDirection.getDegree() + 90) % 360);
    }

    @Override
    public String toString() {
        if(this.direction.equals(CommandType.TURN_LEFT.value)) {
            return "turn left";
        }
        if(this.direction.equals(CommandType.TURN_RIGHT.value)) {
            return "turn right";
        }
        return "";
    }
}
