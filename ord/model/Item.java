package ord.model;

import java.util.ArrayList;

public class Item {
    String id;
    String description;
    String type;
    String unit;
    int pack;
    double cost;
    int availability;
    int limit;

    /**
     * A helper method that returns the list of labels for each of the columns in the catalogue's table.
     * @return an array of Strings containing the labels for a <code>JTable</code>
     */
    static public String[] catalogueColumnId(){
        return new String[] {"Item ID","Description","package Type","Unit","Units in a Pack","Package Cost, £","Availability, packs"};
    }

    public Item(String id, String description, String type, String unit, int pack, double cost, int availability, int limit) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.unit = unit;
        this.pack = pack;
        this.cost = cost;
        this.availability = availability;
        this.limit = limit;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getUnit() {
        return unit;
    }

    public int getPack() {
        return pack;
    }

    public double getCost() {
        return cost;
    }

    public int getAvailability() {
        return availability;
    }

    public int getLimit() {
        return limit;
    }

    /**
     * Returns the data for a row in the catalogue's table.
     * @return an array of Strings containing the data of this <code>Item</code> to be put in a <code>JTable</code>.
     */
    public String[] catalogueRowData(){
        return new String[] {id, description, type, unit, String.valueOf(pack), String.valueOf(cost), String.valueOf(availability)};
    }
}
