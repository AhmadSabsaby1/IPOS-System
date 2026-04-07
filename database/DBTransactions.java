package database;

import java.sql.*;

public class DBTransactions extends DBParent {

    public DBTransactions() throws ClassNotFoundException, SQLException {
        super();
    }

    /**
     * Returns all records of Transactions and the corresponding records of
     * LocalStock_Transactions and AccountHolders_Transactions (The orderIDs and accountID)
     */
    public ResultSet getTransactions() throws SQLException {
        String sql = "SELECT * FROM Transactions LEFT JOIN LocalStock_Transactions AS Products ON Transactions.orderID = Products.orderID LEFT JOIN AccountHolders_Transactions AS Accounts ON Transactions.orderID = Accounts.orderID";
        PreparedStatement query = con.prepareStatement(sql);
        return query.executeQuery();
    }

    /**
     * Updates the amount received for a specified transaction.
     *Does NOT ensure that only account holders' transactions can be changed
     */
    public void setAmountReceived(String orderID, double amountReceived) throws SQLException {
        String sql = "UPDATE Transactions SET amountReceived = ? WHERE orderID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setDouble(1, amountReceived);
        query.setString(2, orderID);
        query.executeUpdate();
    }

    // Creates new record of a transaction. This does NOT create a corresponding record
    // in AccountHolders_Transactions and LocalStock_Transactions (See newAccountTransaction
    // and addOrderItem)
    public String newTransaction(String paymentType, double amountReceived, String cardType,
                               int firstFour, int lastFour, String expiryDate,
                               String shippingAddress) throws SQLException {
        String sql = "INSERT INTO Transactions VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement query = con.prepareStatement(sql);
        String id = getUniqueID();
        query.setString(1, id);
        query.setString(2, paymentType);
        query.setDouble(3, amountReceived);
        query.setString(4, cardType);
        query.setInt(5, firstFour);
        query.setInt(6, lastFour);
        query.setString(7, expiryDate);
        query.setString(8, shippingAddress);
        query.executeUpdate();
        return id;
    }

    // Creates a record of AccountHolders_Transactions linking a specified account holder
    // to a specified transaction
    public void newAccountTransaction(String orderID, String accountID) throws SQLException {
        String sql = "INSERT INTO AccountHolders_Transactions VALUES (?,?)";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, orderID);
        query.setString(2, accountID);
        query.executeUpdate();
    }

    // Creates a record to LocalStock_Transactions of specified item in a specified order.
    // When adding multiple items to the same order, call this method multiple times
    public void addOrderItem(String orderID, String itemID, int quantity) throws SQLException {
        String sql = "INSERT INTO LocalStock_Transactions VALUES (?,?,?)";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, itemID);
        query.setString(2, orderID);
        query.setInt(3, quantity);
        query.executeUpdate();
    }

    // Deletes the record of a specified order AND the corresponding records in
    // LocalStock_Transactions and AccountHolders_Transactions
    public void deleteOrder(String orderID) throws SQLException {
        String sql1 = "DELETE FROM LocalStock_Transactions WHERE orderID = ?";
        PreparedStatement query1 = con.prepareStatement(sql1);
        query1.setString(1, orderID);
        query1.executeUpdate();

        String sql2 = "DELETE FROM AccountHolders_Transactions WHERE orderID = ?";
        PreparedStatement query2 = con.prepareStatement(sql2);
        query2.setString(1, orderID);
        query2.executeUpdate();

        String sql3 = "DELETE FROM Transactions WHERE orderID = ?";
        PreparedStatement query3 = con.prepareStatement(sql3);
        query3.setString(1, orderID);
        query3.executeUpdate();
    }

    /**
     * Returns the record of a specified transaction and the corresponding products in it
     */
    public ResultSet getOrderInfo(String orderID) throws SQLException {
        String sql = "SELECT * FROM Transactions LEFT JOIN LocalStock_Transactions AS Products ON Transactions.orderID = Products.orderID LEFT JOIN AccountHolders_Transactions AS Accounts ON Transactions.orderID = Accounts.orderID WHERE Transactions.orderID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, orderID);
        return query.executeQuery();
    }

    /**
     * Returns all records of Transactions and the corresponding records of
     * LocalStock_Transactions and AccountHolders_Transactions with the specified accountID
     */
    public ResultSet getTransactionsByAccountID(String accountID) throws SQLException {
        String sql = "SELECT * FROM Transactions LEFT JOIN LocalStock_Transactions AS Products ON Transactions.orderID = Products.orderID LEFT JOIN AccountHolders_Transactions AS Accounts ON Transactions.orderID = Accounts.orderID WHERE Accounts.accountID = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, accountID);
        return query.executeQuery();
    }

    // Used to generate a unique ID when creating a new record.
    private String getUniqueID() throws SQLException {
        String sql = "SELECT orderID FROM Transactions ORDER BY orderID";
        PreparedStatement query = con.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        int currentNum = 1;
        while (rs.next()) {
            String id = rs.getString("orderID");
            int num = Integer.parseInt(id.substring(2));
            if (num > currentNum) {
                break;
            }
            currentNum++;
        }
        return "IP" + String.format("%04d", currentNum);
    }

}
