package app.entity;

import app.enums.CommandType;
import app.exceptions.ParseException;

/**
 * The app.Main program implements ...
 * <p>
 * A - Advance: this command takes a positive integer parameter to define the
 * number of squares the bulldozer should move forwards (in whatever direction
 * it is currently facing);
 * <p>
 * L - Left: turn the bulldozer (on the spot) 90 degrees to the left of the
 * direction it is facing;
 * <p>
 * R - Right: turn the bulldozer 90 degrees to the right;
 * <p>
 * Q - Quit: end the simulation.
 *
 * @author Xiaoyu Sun
 */

public class Commands {

    // when construct command, sub-class commands has their only property.
    public boolean isQuitCommand;

    public Commands() {
        this.isQuitCommand = false;
    }

    public Commands(boolean isQuitCommand) {
        this.isQuitCommand = isQuitCommand;
    }


    /**
     * Validate the input Command is Valid or not.
     * If it is not a valid command, throw ParseException.
     * If it is valid, add it as either Direction/Advance Command object into a CommandList
     * @param input
     * @return Advance/Direction Command object
     * @throws ParseException
     */
    public static Commands validateCommandAndConstruct(String input) throws ParseException {

        if (input == null || input.isEmpty() || input.trim().isEmpty()) {
            throw new ParseException("invalid Command");
        }
        
        // iterate through the commandType Enum
        for (CommandType commandType : CommandType.values()) {
            // Compare only the first character of the command ignoring case
            String abbreviation = input.substring(0, 1).toLowerCase();
            if (abbreviation.equals(commandType.value)) {
                if (abbreviation.equals(CommandType.TURN_LEFT.value)
                    || abbreviation.equals(CommandType.TURN_RIGHT.value)) {
                    if (input.trim().length()==1){
                        return new DirectionCommand(abbreviation);
                    }else {
                        throw new ParseException("invalid Command");
                    }
                }
                if (abbreviation.equals(CommandType.ADVANCE.value)) {
                    // split into two parts, one is the
                    String[] splitAdvanceCommand = input.trim().split("\\s+");
                    if (splitAdvanceCommand.length == 2 && isPositiveInteger(splitAdvanceCommand[1]) ) {
                        int advanceStep =  Integer.parseInt(splitAdvanceCommand[1]);
                        return new AdvanceCommand(advanceStep);
                    }
                }

            }
        }
        throw new ParseException("invalid input Command");
    }


    public static boolean isPositiveInteger(String command) {
        try {
            int num = Integer.parseInt(command);
            if (num <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (isQuitCommand) {
            return "quit";
        }
        return "";
    }
}






