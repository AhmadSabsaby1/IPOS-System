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
    public void newProduct(String itemID, String description, String packageType, String unit,
                           int unitsInAPack, int packageCost, int availability,
                           int stockLimit, int retailMarkUpRate) throws SQLException {
        String sql = "INSERT INTO LocalStock VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, itemID);
        query.setString(2, description);
        query.setString(3, packageType);
        query.setString(4, unit);
        query.setInt(5, unitsInAPack);
        query.setInt(6, packageCost);
        query.setInt(7, availability);
        query.setInt(8, stockLimit);
        query.setInt(9, retailMarkUpRate);
        query.executeUpdate();
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

}
