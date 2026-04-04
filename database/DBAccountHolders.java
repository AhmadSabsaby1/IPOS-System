package database;

import java.sql.*;

public class DBAccountHolders extends DBParent {

    public DBAccountHolders() throws ClassNotFoundException, SQLException {
        super();
    }

    // Returns all records of AccountHolders
    public ResultSet getAccounts() throws SQLException {
        String sql = "SELECT * FROM AccountHolders";
        PreparedStatement query = con.prepareStatement(sql);
        return query.executeQuery();
    }

    // Updates the name of a specified account holder
    public void setName(String accountID, String name) throws SQLException {
        String sql = "UPDATE AccountHolders SET name = ? WHERE accountID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, name);
        query.setString(2, accountID);
        query.executeUpdate();
    }

    // Updates the address of a specified account holder
    public void setAddress(String accountID, String address) throws SQLException {
        String sql = "UPDATE AccountHolders SET address = ? WHERE accountID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, address);
        query.setString(2, accountID);
        query.executeUpdate();
    }

    // Updates the current balance of a specified account holder
    public void setBalance(String accountID, int balance) throws SQLException {
        String sql = "UPDATE AccountHolders SET balance = ? WHERE accountID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setInt(1, balance);
        query.setString(2, accountID);
        query.executeUpdate();
    }

    // Updates the total balance limit of a specified account holder
    public void setBalanceLimit(String accountID, int balanceLimit) throws SQLException {
        String sql = "UPDATE AccountHolders SET balanceLimit = ? WHERE accountID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setInt(1, balanceLimit);
        query.setString(2, accountID);
        query.executeUpdate();
    }

    // Updates the discount type of a specified account holder
    public void setDiscountType(String accountID, String discountType) throws SQLException {
        String sql = "UPDATE AccountHolders SET discountType = ? WHERE accountID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, discountType);
        query.setString(2, accountID);
        query.executeUpdate();
    }

    // Updates the fixed discount of a specified account holder
    public void setFixedDiscount(String accountID, double discount) throws SQLException {
        String sql = "UPDATE AccountHolders SET discount = ? WHERE accountID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setDouble(1, discount);
        query.setString(2, accountID);
        query.executeUpdate();
    }

    // Updates the first tier discount of a specified account holder
    public void setTier1Discount(String accountID, double tier1Discount) throws SQLException {
        String sql = "UPDATE AccountHolders SET tier1Discount = ? WHERE accountID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setDouble(1, tier1Discount);
        query.setString(2, accountID);
        query.executeUpdate();
    }

    // Updates the first discount threshold of a specified account holder
    public void setTier1Threshold(String accountID, int tier1Threshold) throws SQLException {
        String sql = "UPDATE AccountHolders SET tier1Threshold = ? WHERE accountID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setInt(1, tier1Threshold);
        query.setString(2, accountID);
        query.executeUpdate();
    }

    // Updates the second tier discount of a specified account holder
    public void setTier2Discount(String accountID, double tier2Discount) throws SQLException {
        String sql = "UPDATE AccountHolders SET tier2Discount = ? WHERE accountID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setDouble(1, tier2Discount);
        query.setString(2, accountID);
        query.executeUpdate();
    }

    // Updates the second discount threshold of a specified account holder
    public void setTier2Threshold(String accountID, int tier2Threshold) throws SQLException {
        String sql = "UPDATE AccountHolders SET tier2Threshold = ? WHERE accountID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setInt(1, tier2Threshold);
        query.setString(2, accountID);
        query.executeUpdate();
    }

    // Updates the third tier discount of a specified account holder
    public void setTier3Discount(String accountID, double tier3Discount) throws SQLException {
        String sql = "UPDATE AccountHolders SET tier3Discount = ? WHERE accountID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setDouble(1, tier3Discount);
        query.setString(2, accountID);
        query.executeUpdate();
    }

    // Updates the account status of a specified account holder
    public void setStatus(String accountID, String status) throws SQLException {
        String sql = "UPDATE AccountHolders SET status = ? WHERE accountID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, status);
        query.setString(2, accountID);
        query.executeUpdate();
    }

    // Updates the status of the first reminder for a specified account holder
    public void setStatus1stReminder(String accountID, String status) throws SQLException {
        String sql = "UPDATE AccountHolders SET status1stReminder = ? WHERE accountID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, status);
        query.setString(2, accountID);
        query.executeUpdate();
    }

    // Updates the status of the second reminder for a specified account holder
    public void setStatus2ndReminder(String accountID, String status) throws SQLException {
        String sql = "UPDATE AccountHolders SET status2ndReminder = ? WHERE accountID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, status);
        query.setString(2, accountID);
        query.executeUpdate();
    }

    // Creates new record for a new account. For the columns of unused discount type, use 0
    public String createAccount(String name, String address, int balance, int balanceLimit,
                              String discountType, double discount, double tier1Discount,
                              int tier1Threshold, double tier2Discount, int tier2Threshold, double tier3Discount,
                              String status, String status1stReminder, String status2ndReminder) throws SQLException {
        String sql = "INSERT INTO AccountHolders VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement query = con.prepareStatement(sql);
        String id = getUniqueID();
        query.setString(1, id);
        query.setString(2, name);
        query.setString(3, address);
        query.setInt(4, balance);
        query.setInt(5, balanceLimit);
        query.setString(6, discountType);
        query.setDouble(7, discount);
        query.setDouble(8, tier1Discount);
        query.setInt(9, tier1Threshold);
        query.setDouble(10, tier2Discount);
        query.setInt(11, tier2Threshold);
        query.setDouble(12, tier3Discount);
        query.setString(13, status);
        query.setString(14, status1stReminder);
        query.setString(15, status2ndReminder);
        query.executeUpdate();
        return id;
    }

    // Deletes a record of a specified account holder
    public void deleteAccount(String accountID) throws SQLException {
        String sql = "DELETE FROM AccountHolders WHERE accountID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, accountID);
        query.executeUpdate();
    }

    // Returns a record of AccountHolders with a specified account ID
    public ResultSet getCustomerInfo(String accountID) throws SQLException {
        String sql = "SELECT * FROM AccountHolders WHERE accountID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, accountID);
        return query.executeQuery();
    }


    // Used to generate a unique ID when creating a new record.
    private String getUniqueID() throws SQLException {
        String sql = "SELECT accountID FROM AccountHolders ORDER BY accountID";
        PreparedStatement query = con.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        int currentNum = 1;
        while (rs.next()) {
            String id = rs.getString("accountID");
            int num = Integer.parseInt(id.substring(3));
            if (num > currentNum) {
                break;
            }
            currentNum++;
        }
        return "ACC" + String.format("%04d", currentNum);
    }

}
