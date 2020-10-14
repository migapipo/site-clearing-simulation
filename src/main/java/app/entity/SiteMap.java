package app.entity;

import app.enums.Direction;
import app.exceptions.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * SiteMap is a util class with static methods, which are for read and parse input site map file and encode/decode
 * coordinates
 */
public class SiteMap {

    public static ArrayList<String> siteMapList = new ArrayList<String>();
    public static int ROW;
    public static int COL;


    public static ArrayList<String> readInputSiteMap(String siteMapFileName) throws IOException {
        // FileReader reads text files in the default encoding
        FileReader fileReader = new FileReader(siteMapFileName);
        // Wrap the FileReader in BufferedReader
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            siteMapList.add(line);
        }
        return siteMapList;
    }

	/**
	 * Parse sitemap list to siteMap 2D array
	 * @param siteMapList
	 * @return siteMap
	 */
    public static String[][] parseSiteMapListToArray(ArrayList<String> siteMapList) {
        // Get siteMap column and row
        COL = siteMapList.get(0).trim().split("\\s+").length;
        ROW = siteMapList.size();

        String[][] siteMap = new String[ROW][COL];

        for (int i = 0; i < siteMapList.size(); i++) {
            String[] rowList = siteMapList.get(i).trim().split("\\s+");
            for (int j = 0; j < rowList.length; j++) {
                siteMap[i][j] = rowList[j];
            }
        }
        return siteMap;
    }

	/**
	 * encode coordinates (x, y) to an integer to represent each location on the siteMap
	 * location = x * ColumnNumber + y
	 * @param x
	 * @param y
	 * @return location
	 */
    public static int encodeCoordinates(int x, int y) {
        int location = x * COL + y;
        return location;

    }

	/**
	 * Decode an integer to coordinates (x, y) on the siteMap
	 * @param direction
	 * @param location
	 * @return coordinates
	 * @throws ParseException
	 */
    public static int[] decodeLocation(Direction direction, int location) throws ParseException {
        int[] coordinates = new int[2];
        if (location == -1) {
            if (direction == Direction.EAST) {
                return new int[]{0, -1};

            }
            return new int[]{-1, -1};
        }
        int x = location / COL;
        int y = location % COL;
        coordinates[0] = x;
        coordinates[1] = y;
        return coordinates;
    }

}
