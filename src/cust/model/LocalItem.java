package cust.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocalItem {
    private String id;
    private String description;
    private String type;
    private String unit;
    private int pack;
    private double cost;
    private int availability;
    private int limit;
    private int retailRate;

    static public String ITEM_ID = "Item ID";
    static public String DESCRIPTION = "Description";

    static public String[] catalogueColumnId(){
        return new String[] {"Item ID","Description","package Type","Unit","Units in a Pack","Package Cost, £","Availability, packs"};
    }

    public LocalItem(ResultSet rs) throws SQLException {
        if (rs.isBeforeFirst())
            rs.next();

        String id = rs.getString("itemID");
        String description = rs.getString("description");
        String type = rs.getString("packageType");
        String unit = rs.getString("unit");
        int pack = rs.getInt("unitsInAPack");
        double packageCost = rs.getDouble("packageCost");
        int availability = rs.getInt("availability");
        int stockLimit = rs.getInt("stockLimit");
        int retailMarkUpRate = rs.getInt("retailMarkUpRate");

        this.id = id;
        this.description = description;
        this.type = type;
        this.pack = pack;
        this.unit = unit;
        this.cost = packageCost;
        this.availability = availability;
        this.limit = stockLimit;
        this.retailRate = retailMarkUpRate;
    }

    public LocalItem(String id, String description, String type, String unit, int pack, double cost, int availability, int limit) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.unit = unit;
        this.pack = pack;
        this.cost = cost;
        this.availability = availability;
        this.limit = limit;
    }

    /// ////////// GETTERS ////////////
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

    /// ////////////////////////////////
    public String[] catalogueRowData(){
        return new String[] {id, description, type, unit, Integer.toString(pack), Double.toString(cost), Integer.toString(availability)};
    }
}
