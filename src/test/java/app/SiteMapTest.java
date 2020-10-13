package app;

import app.entity.SiteMap;
import app.enums.Direction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;


public class SiteMapTest {
    @Test
    public void SiteMapTest() throws IOException {
        ArrayList<String> siteMapList;
        String [][] siteMap2DArray;
        siteMapList = SiteMap.readInputSiteMap("input/siteMap.txt");
        siteMap2DArray = SiteMap.parseSiteMapListToArray(siteMapList);

        ArrayList<String> testList =new ArrayList<String>(
                Arrays.asList( "o o t o o o o o o o",
                        "o o o o o o o T o o",
                        "r r r o o o o T o o",
                        "r r r r o o o o o o",
                        "r r r r r t o o o o"));
        assertEquals(testList,siteMapList );

        String[][] test2DArray = new String[][]{
                {"o", "o", "t", "o", "o", "o", "o", "o", "o", "o"},
                {"o", "o", "o", "o", "o", "o", "o", "T", "o", "o"},
                {"r", "r", "r", "o", "o", "o", "o", "T", "o", "o"},
                {"r", "r", "r", "r", "o", "o", "o", "o", "o", "o"},
                {"r", "r", "r", "r", "r", "t", "o", "o", "o", "o"}};

        assertTrue(Arrays.deepEquals(test2DArray, siteMap2DArray));


        assertEquals(1, SiteMap.encodeCoordinates(0, 1));
        assertEquals(11, SiteMap.encodeCoordinates(1, 1));
        assertArrayEquals(new int[]{0, 0}, SiteMap.decodeLocation(Direction.EAST, 0));
        assertArrayEquals(new int[]{0, 1}, SiteMap.decodeLocation(Direction.EAST, 1));
        assertArrayEquals(new int[]{1, 1}, SiteMap.decodeLocation(Direction.WEST, 11));
        assertArrayEquals(new int[]{4, 9}, SiteMap.decodeLocation(Direction.NORTH, 49));
    }
}
