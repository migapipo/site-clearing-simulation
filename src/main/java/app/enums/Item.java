package app.enums;

/**
 * Enum Item class lists all items of the cost table and corresponding cost units
 * @ClassName Item.java
 * @Description Item Enum Class
 * @createTime 2020 -  09 - 25 - 16 : 14
 */
public enum Item {

    COMMUNICATION_OVERHEAD("communication overhead", 1),
    FUEL_USAGE("fuel usage", 1),
    UNCLEARED_SQUARES("uncleared squares", 3),
    DESTRUCTION_OF_PROTECTED_TREE("destruction of protected tree",10),
    PAINT_DAMAGE_TO_BULLDOZER("paint damage to bulldozer", 2);

    private String itemName;

    private int costUnit;

    Item(String itemName, int costUnit) {
        this.itemName = itemName;
        this.costUnit = costUnit;
    }


    public String getItemName() {
        return itemName;
    }

    public int getCostUnit() {
        return costUnit;
    }
}
