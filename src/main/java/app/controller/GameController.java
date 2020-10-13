package app.controller;

import app.entity.Bulldozer;
import app.entity.Commands;
import app.enums.Item;
import app.enums.SquareType;
import app.exceptions.CutTreeException;
import app.exceptions.OutOfBoundaryException;
import app.exceptions.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * GameController class includes consists of main methods and some other parse methods that operate on the main class
 * depending on the user input.
 */
public class GameController {

    private List<Commands> commandList;
    private String[][] siteMap;
    private boolean[][] visited;
    private Bulldozer currentLocation;
    private Map<Item, Integer> costMap;  // key ItemEnum, value: number of activties

    public GameController(String[][] siteMap, int row, int col ) {
        this.siteMap = siteMap;
        this.visited = new boolean[row][col];
        this.currentLocation = new Bulldozer();
        this.costMap = constructCostMap();
        this.commandList = new ArrayList<>();
    }

    private Map<Item, Integer> constructCostMap() {
        Map<Item, Integer> costMap = new HashMap<>();
        for (Item item: Item.values()) {
            costMap.put(item, 0);
        }
        return costMap;
    }

    /**
     * Read commands and and add to command List, execute one command per line.
     * @throws IllegalArgumentException
     */

    public void readCommands() throws IllegalArgumentException {

        System.out.println("\nThe bulldozer is currently located at the Northern edge of the site, " +
                "immediately to the West of the site, and facing East.\n");

        Scanner scanner = new Scanner(System.in);

        this.commandList = new ArrayList<>();
        System.out.print("(l)eft, (r)ight, (a)dvance <n>, (q)uit:");
        boolean isTerminate = false;
        while (! isTerminate && !scanner.hasNext("q|quit") ) {

            String command = scanner.nextLine().trim();
            Commands commandObj = null;
            // 1. Parse the command Str to command Object
            try {
                commandObj = Commands.validateCommandAndConstruct(command);
            }catch (ParseException exception) {
                System.out.println(exception.getMessage());
            }
            if (commandObj != null) {
                commandList.add(commandObj);
            }
            // 2. execute the command Obj , if there is an attempt to be out of boundary, break;
            try {
                this.currentLocation.execute(commandObj, this);
            }catch (OutOfBoundaryException e1 ) {
                System.out.println(e1.getMessage());
                isTerminate = true;
            }catch (CutTreeException e2) {
                System.out.println(e2.getMessage());
                isTerminate = true;
            }
            if (!isTerminate)  System.out.print("(l)eft, (r)ight, (a)dvance <n>, (q)uit:");
        }
        if (!isTerminate) {
            commandList.add(new Commands(true));
        }
        return;
    }

    /**
     * Getter for printing the input valid commands strings
     * @return
     */
    public String getCommandListString() {
        StringBuilder commandListStr = new StringBuilder();
        for (int index = 0 ; index < commandList.size(); index++){
            Commands command =  commandList.get(index);
            commandListStr.append(command.toString());
            if (index < commandList.size() - 1) commandListStr.append(", ");
        }
        return commandListStr.toString();
    }

    /**
     * Generate a table providing itemised costs of the clearing operation and a total cost.
     * @return itemised costs of the clearing operation and a total cost.
     */
    public String generateCostReport() {
        StringBuilder report = new StringBuilder();

        this.computeUnclearedSquareAndCommunication();
        report.append("The costs for this land clearing operation were:\n");
        report.append("Item\t\t\t\t\t\tQuantity\t\tCost\n");
        int totalCredit = 0;
        for (Item item: Item.values()) {
            String itemName = item.getItemName();
            int quantity = this.costMap.getOrDefault(item, 0);
            int credit = item.getCostUnit() *  quantity;
            totalCredit += credit;
            String row = String.format("%s\t\t\t\t%d\t\t%d", itemName, quantity, credit);
            report.append(row + "\n");
        }
        report.append("----\n");
        report.append(String.format("Total \t\t\t\t\t\t\t\t\t\t %d", totalCredit));
        return report.toString();
    }

    /**
     * count the number of uncleared square and communication overhead, not including the protected tree.
     */
    private void computeUnclearedSquareAndCommunication() {

        int count = 0;
        int row = siteMap.length, col = siteMap[0].length;

        for (int r = 0; r < row; r++){
            for (int c = 0; c < col; c++) {
                if (!this.visited[r][c] && !siteMap[r][c].equals(SquareType.TREE_PRESERVED.getSquareType())) {
                    count += 1;
                }
            }
        }
        costMap.put(Item.UNCLEARED_SQUARES, costMap.get(Item.UNCLEARED_SQUARES) + count);

        int communicationCount = this.commandList.size() - 1; // the last one is not succeed or quit command.
        this.costMap.put(Item.COMMUNICATION_OVERHEAD,
            costMap.get(Item.COMMUNICATION_OVERHEAD) + communicationCount * Item.COMMUNICATION_OVERHEAD.getCostUnit());

        return;
    }

    public List<Commands> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<Commands> commandList) {
        this.commandList = commandList;
    }

    public String[][] getSiteMap() {
        return siteMap;
    }

    public void setSiteMap(String[][] siteMap) {
        this.siteMap = siteMap;
    }

    public boolean[][] getVisited() {
        return visited;
    }

    public void setVisited(boolean[][] visited) {
        this.visited = visited;
    }

    public Bulldozer getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Bulldozer currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Map<Item, Integer> getCostMap() {
        return costMap;
    }

    public void setCostMap(Map<Item, Integer> costMap) {
        this.costMap = costMap;
    }
}
