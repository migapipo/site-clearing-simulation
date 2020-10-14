package app;

import app.controller.GameController;
import app.entity.SiteMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static app.entity.SiteMap.parseSiteMapListToArray;

/**
 * The app.Main program implements a simulation application which act like a bulldozer operator to clear the site by
 * given instructions at he command line by a trainee supervisor.
 *
 * <p>
 * Inputs:
 * 1. A file containing a site map.
 * This will be specified on the command line when the application is started.
 * 2. Commands entered by the trainee on the console during the simulation run.
 * All input commands will be stored in a command list for later output and generating a itemised cost report.
 * <p>
 * There are 4 Command Types including advance, left, right and quit command to control the movement of the bulldozer.
 * Each command will generate different costs.
 * <p>
 * Clearing cost depends on the command type and the square type it stands on the site map.
 *
 * <p>
 * Users will see the site map they entered after entering the site map text file including the graph information.
 * Then users just need to input nec
 * instructions to complete each calculation and reach the output. After each function implementation, the application
 * will return to the main menu for users to continue or exit the application.
 */
public class Main {

    public static String[][] siteMap2DArray;
    public static List<String> commandList = new ArrayList<String>();
    private static ArrayList<String> siteMapList = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        /*
         * Ask user to enter filename with extension to open and read its content (The file should include the site
         * map information)
         */

        String mainMenu = ("To start the application, please enter the correct file path to open (with extension like" +
                " fileName.txt) : ");
        System.out.println(mainMenu);

        try {
            Scanner scanner = new Scanner(System.in);
            String inputString = scanner.nextLine().trim();

            System.out.println("Welcome to the Aconex site clearing simulator. This is a map of the site:\n");

            // Read a file containing site map
            siteMapList = SiteMap.readInputSiteMap(inputString);

            // Parse site map
            siteMap2DArray = parseSiteMapListToArray(siteMapList);


            GameController controller = new GameController(siteMap2DArray, SiteMap.ROW, SiteMap.COL);
            // Get command inputs and put them in a Command array list
            controller.readCommands();
            System.out.println("The simulation has ended at your request.\n"
                    + "These are the commands you issued:");
            // Print the command string list
            System.out.println(controller.getCommandListString());
            // Print the cost report
            System.out.println(controller.generateCostReport());

            System.out.println("Thank you for using the Aconex site clearing simulator.");

        } catch (IOException ex) {
            System.out.println("Error reading file");
            System.out.println("Please give a correct file name.");
        }

    }

}
