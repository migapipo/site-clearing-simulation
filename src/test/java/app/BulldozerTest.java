package app;

import app.controller.GameController;
import app.entity.Bulldozer;
import app.entity.Commands;
import app.entity.SiteMap;
import app.enums.Direction;
import app.exceptions.CutTreeException;
import app.exceptions.OutOfBoundaryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class BulldozerTest {

    private ArrayList<String> siteMapList = null;
    private String[][] siteMap2DArray = null;

    private GameController gameController = null;

    private String[] testCommandList = new String[]{"a 2", "r", "a 4"};

    @BeforeEach
    void setup() throws IOException {

        SiteMap.siteMapList = new ArrayList<>();
        this.gameController = null;
        this.siteMapList = SiteMap.readInputSiteMap("input/siteMap.txt");
        this.siteMap2DArray = SiteMap.parseSiteMapListToArray(this.siteMapList);
        this.gameController = new GameController(this.siteMap2DArray, SiteMap.ROW, SiteMap.COL);

    }

    /**
     * Assert the bulldozer match the expected geo-location and direction
     *
     * @param expectedDirection
     * @param expectedLocation
     * @param bulldozer
     */
    void assertPositionObj(Direction expectedDirection, int[] expectedLocation, Bulldozer bulldozer) {
        assertEquals(expectedDirection, bulldozer.getDirection());
        assertArrayEquals(expectedLocation, SiteMap.decodeLocation(bulldozer.getDirection(), bulldozer.getLocation()));
    }


    @Test
    /**
     * This is a test for the Sample Test Data provided in the PDF File.
     * Input commands: a 4 -> r ->  a 4 -> l -> a 2 -> a 4 -> l -> q
     * This method will execute the command one by one defined in the testCommandList, and check the Bulldozer Object
     * is updated correctly, by checking each attribute of a position.
     * Finally, this method checks the itemised expense report is generated correctly.
     *
     * @throws IOException
     */
    void executeAndValidateSampleTest() throws IOException {
        String expectedReport = "The costs for this land clearing operation were:\n"
                + "Item\t\t\t\t\t\tQuantity\t\tCost\n"
                + "communication overhead\t\t\t\t7\t\t7\n"
                + "fuel usage\t\t\t\t19\t\t19\n"
                + "uncleared squares\t\t\t\t34\t\t102\n"
                + "destruction of protected tree\t\t\t\t0\t\t0\n"
                + "paint damage to bulldozer\t\t\t\t1\t\t2\n"
                + "----\n"
                + "Total \t\t\t\t\t\t\t\t\t\t 130"; //

        // Create a bulldozer object
        Bulldozer bulldozer = new Bulldozer();
        Commands commands1 = Commands.validateCommandAndConstruct("a 4");
        // Add command to gameController for overhead calculation
        this.gameController.getCommandList().add(commands1);
        bulldozer.execute(commands1, this.gameController);
        // Call assertPositionObj method to assert the attributes of the current bulldozer after execute method
        assertPositionObj(Direction.EAST, new int[]{0, 3}, bulldozer);

        Commands commands2 = Commands.validateCommandAndConstruct("r");
        this.gameController.getCommandList().add(commands2);
        bulldozer.execute(commands2, this.gameController);
        assertPositionObj(Direction.SOUTH, new int[]{0, 3}, bulldozer);

        Commands commands3 = Commands.validateCommandAndConstruct("a 4 ");
        this.gameController.getCommandList().add(commands3);
        bulldozer.execute(commands3, this.gameController);
        assertPositionObj(Direction.SOUTH, new int[]{4, 3}, bulldozer);

        Commands commands4 = Commands.validateCommandAndConstruct("l ");
        this.gameController.getCommandList().add(commands4);
        bulldozer.execute(commands4, this.gameController);
        assertPositionObj(Direction.EAST, new int[]{4, 3}, bulldozer);

        Commands commands5 = Commands.validateCommandAndConstruct("a 2");
        this.gameController.getCommandList().add(commands5);
        bulldozer.execute(commands5, this.gameController);
        assertPositionObj(Direction.EAST, new int[]{4, 5}, bulldozer);

        Commands commands6 = Commands.validateCommandAndConstruct("a 4");
        this.gameController.getCommandList().add(commands6);
        bulldozer.execute(commands6, this.gameController);
        assertPositionObj(Direction.EAST, new int[]{4, 9}, bulldozer);

        Commands commands7 = Commands.validateCommandAndConstruct("l");
        this.gameController.getCommandList().add(commands7);
        bulldozer.execute(commands7, this.gameController);
        assertPositionObj(Direction.NORTH, new int[]{4, 9}, bulldozer);

        // Add quit command at the end
        this.gameController.getCommandList().add(new Commands(true));

        // Check if the report is the same as what we expected
        assertEquals(expectedReport, this.gameController.generateCostReport());

    }

    /**
     * Execute the command one by one defined in the testCommandList, and check the Bulldozer Object
     * is updated correctly.
     *
     * @throws IOException
     */
    @Test
    void executeAndValidateCustomisedTest() throws IOException {


        String expectedReport = "The costs for this land clearing operation were:\n"
                + "Item\t\t\t\t\t\tQuantity\t\tCost\n"
                + "communication overhead\t\t\t\t3\t\t3\n"
                + "fuel usage\t\t\t\t9\t\t9\n"
                + "uncleared squares\t\t\t\t42\t\t126\n"
                + "destruction of protected tree\t\t\t\t0\t\t0\n"
                + "paint damage to bulldozer\t\t\t\t0\t\t0\n"
                + "----\n"
                + "Total \t\t\t\t\t\t\t\t\t\t 138";
        Bulldozer bulldozer = new Bulldozer();
        // 1. execute a 2
        Commands commands1 = Commands.validateCommandAndConstruct(this.testCommandList[0]);
        this.gameController.getCommandList().add(commands1);
        bulldozer.execute(commands1, this.gameController);
        // Check the bulldozer position and direction facing per command.
        assertPositionObj(Direction.EAST, new int[]{0, 1}, bulldozer);

        // 2. execute r
        Commands commands2 = Commands.validateCommandAndConstruct(this.testCommandList[1]);
        this.gameController.getCommandList().add(commands2);
        bulldozer.execute(commands2, this.gameController);

        assertPositionObj(Direction.SOUTH, new int[]{0, 1}, bulldozer);

        // 3. execute a 4
        Commands commands3 = Commands.validateCommandAndConstruct(this.testCommandList[2]);
        this.gameController.getCommandList().add(commands3);
        bulldozer.execute(commands3, this.gameController);
        assertPositionObj(Direction.SOUTH, new int[]{4, 1}, bulldozer);

        // finally add quit command.
        this.gameController.getCommandList().add(new Commands(true));

        // check if the printed report is the same as what you expected
        assertEquals(expectedReport, this.gameController.generateCostReport());
    }

    /**
     * Throw a Cut Tree Exception when there is an attempt to remove a protected tree
     * Command list: a 8 -> r -> a 1
     *
     * @throws IOException
     */
    @Test
    void CutTreeExceptionTest() throws IOException {
        Bulldozer bulldozer = new Bulldozer();
        // 1. execute a 2
        Commands commands1 = Commands.validateCommandAndConstruct("a 8 ");
        this.gameController.getCommandList().add(commands1);
        bulldozer.execute(commands1, this.gameController);
        // We can check the bulldozer we are per command.
        assertPositionObj(Direction.EAST, new int[]{0, 7}, bulldozer);

        // 2. execute r
        Commands commands2 = Commands.validateCommandAndConstruct("r ");
        this.gameController.getCommandList().add(commands2);
        bulldozer.execute(commands2, this.gameController);

        assertPositionObj(Direction.SOUTH, new int[]{0, 7}, bulldozer);

        // 3. execute a 1, should throw the CutThreeException.
        Commands commands3 = Commands.validateCommandAndConstruct("a 1");
        this.gameController.getCommandList().add(commands3);
        assertThrows(CutTreeException.class, () -> bulldozer.execute(commands3, this.gameController));
    }

    /**
     * Throw an Out of Boundary exception when there is an attempt to navigate beyond the boundaries of the site
     *
     * @throws IOException
     */
    @Test
    void OutOfColBoundaryExceptionTest() throws IOException {
        Bulldozer bulldozer = new Bulldozer();
        // 1. execute a 2
        Commands commands1 = Commands.validateCommandAndConstruct("a 11     ");
        this.gameController.getCommandList().add(commands1);
        assertThrows(OutOfBoundaryException.class, () -> bulldozer.execute(commands1, this.gameController));
    }

    /**
     * Throw an Out of Boundary exception when there is an attempt to navigate beyond the row boundaries of the site
     *
     * @throws IOException
     */
    @Test
    void OutOfRowBoundaryExceptionTest() throws IOException {
        Bulldozer bulldozer = new Bulldozer();

        // 1. execute a 1
        Commands commands1 = Commands.validateCommandAndConstruct("a 1");
        this.gameController.getCommandList().add(commands1);
        bulldozer.execute(commands1, this.gameController);
        // We can check the bulldozer we are per command.
        assertPositionObj(Direction.EAST, new int[]{0, 0}, bulldozer);

        // 2. execute r
        Commands commands2 = Commands.validateCommandAndConstruct("r    ");
        this.gameController.getCommandList().add(commands2);
        bulldozer.execute(commands2, this.gameController);

        assertPositionObj(Direction.SOUTH, new int[]{0, 0}, bulldozer);

        // 3. execute a 7, should throw the Out of Boundary exception
        Commands commands3 = Commands.validateCommandAndConstruct("a 6");
        this.gameController.getCommandList().add(commands3);
        assertThrows(OutOfBoundaryException.class, () -> bulldozer.execute(commands3, this.gameController));
    }

}
