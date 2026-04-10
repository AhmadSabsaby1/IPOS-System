package database;

import java.sql.*;



class DBParent {

    Connection con;

    // IMPORTANT
    // Change the attributes below to your local MySQL Server to connect to it
    // If you get an error that starts with "Establishing SSL connection..." you can put
    // ?useSSL=false after the localhost:port part and that should fix it.
    String databaseUrl = "jdbc:mysql://localhost:3306/iposca?useSSL=false";
    String username = "root";
    String password = "123123";

    public DBParent() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(databaseUrl, username, password);
    }
}
