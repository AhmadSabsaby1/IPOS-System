package database;

import java.sql.*;

public class DBLocalStock extends DBParent{

    public DBLocalStock() throws ClassNotFoundException, SQLException{
        super();
    }

    // Returns all records of LocalStock
    public ResultSet getStock() throws SQLException {
        String sql = "SELECT * FROM LocalStock";
        PreparedStatement query = con.prepareStatement(sql);
        return query.executeQuery();
    }

    // Updates the total (availability) of one product via itemID
    public void updateStock(String itemID, int availability) throws SQLException {
        String sql = "UPDATE LocalStock SET availability = ? WHERE ItemID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setInt(1, availability);
        query.setString(2, itemID);
        query.executeUpdate();
    }

    // Creates new record for a new product
    public String newProduct(String description, String packageType, String unit,
                           int unitsInAPack, double packageCost, int availability,
                           int stockLimit, int retailMarkUpRate) throws SQLException {
        String sql = "INSERT INTO LocalStock VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement query = con.prepareStatement(sql);
        String id = getUniqueID();
        query.setString(1, id);
        query.setString(2, description);
        query.setString(3, packageType);
        query.setString(4, unit);
        query.setInt(5, unitsInAPack);
        query.setDouble(6, packageCost);
        query.setInt(7, availability);
        query.setInt(8, stockLimit);
        query.setInt(9, retailMarkUpRate);
        query.executeUpdate();
        return id;
    }

    // Deletes the record of a specified product
    public void deleteProduct(String itemID) throws SQLException {
        String sql = "DELETE FROM LocalStock WHERE ItemID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, itemID);
        query.executeUpdate();
    }

    // Takes any number of itemIDs and returns records of all products with
    // the specified IDs
    public ResultSet getItemInfo(String ...itemIDs) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM LocalStock");
        if (itemIDs.length > 0) {
            sql.append(" WHERE itemID = ").append(itemIDs[0]);
            for (int i = 1; i < itemIDs.length; ++i) {
                sql.append(" OR itemID = ").append(itemIDs[i]);
            }
        }
        Statement query = con.createStatement();
        return query.executeQuery(sql.toString());
    }

    // Returns the records that have the specified item name (description).
    // Not case-sensitive and will return any name that contains the string,
    // including partial matches (E.G: getItemInfoByName("ara") will return
    // the record for Paracetamol
    public ResultSet getItemByName(String itemName) throws SQLException {
        String sql = "SELECT * FROM LocalStock WHERE UPPER(description) LIKE UPPER(?)";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, "%"+itemName +"%");
        return query.executeQuery();
    }

    // Used to generate a unique ID when creating a new record.
    private String getUniqueID() throws SQLException {
        String sql = "SELECT itemID FROM LocalStock ORDER BY itemID";
        PreparedStatement query = con.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        int currentNum = 1;
        while (rs.next()) {
            String id = rs.getString("itemID");
            int num = Integer.parseInt(id);
            if (num > currentNum) {
                break;
            }
            currentNum++;
        }
        return String.format("%07d", currentNum);
    }

}
