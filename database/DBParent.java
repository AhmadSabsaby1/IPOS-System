package database;

import java.sql.*;



class DBParent {

    Connection con;

    // IMPORTANT
    // Change the attributes below to your local MySQL Server to connect to it
    // If you get an error that starts with "Establishing SSL connection..." you can put
    // ?useSSL=false after the localhost:port part and that should fix it.
    String databaseUrl = "jdbc:mysql://yourDatabaseHere?useSSL=false";
    String username = "";
    String password = "";

    public DBParent() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(databaseUrl, username, password);
    }

    // Personal testing;
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        DBLocalStock stock = new DBLocalStock();
//        stock.newProduct("1003434", "Paracetamol", "Box", "mg", 1000, 200, 20, 10, 2);
//        stock.newProduct("1003390", "Ibuprofen", "Box", "mg", 2000, 300, 50, 30, 5);

//        String accountID = "ACC1012";

//        DBAccountHolders dbAcc = new DBAccountHolders();
//        dbAcc.createAccount(accountID, "John", "Another address", 0, 3000, "Fixed", 1, 0,0,0,0,0, "Normal", "No need", "No need");

//        DBTransactions db = new DBTransactions();
//
//        String orderID = "IP1044";
//
//        db.deleteOrder(orderID);
//
//        db.newTransaction(orderID, "Card", 0, "Debit", 4092, 1234, 529, "Test address idc");
//        System.out.println(db.getTransactions());
//        db.setAmountReceived(orderID, 1500);
//        db.addOrderItem(orderID, "1003434", 2);
//        db.addOrderItem(orderID, "1003390", 1);
//        db.newAccountTransaction(orderID, accountID);
//
//        ResultSet rs = db.getOrderInfo(orderID);
//        while (rs.next()) {
//            System.out.println(rs.getString(rs.findColumn("accountID")));
//        }
//        db.deleteOrder(orderID);

//        DBLocalStock db = new DBLocalStock();
//        ResultSet rs = db.getItemInfo("1003390", "1003434");
//        while (rs.next()) {
//            System.out.println(rs.getString(1));
//        }
    }
}
