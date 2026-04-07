package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUsers extends DBParent {

    public DBUsers() throws ClassNotFoundException, SQLException {
        super();
    }

    // Returns all records of User
    public ResultSet getUsers() throws SQLException {
        String sql = "SELECT * FROM Users";
        PreparedStatement query = con.prepareStatement(sql);
        return query.executeQuery();
    }

    // Returns a record of the specified user
    public ResultSet getUserInfo(String username) throws SQLException {
        String sql = "SELECT * FROM Users WHERE username = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, username);
        return query.executeQuery();
    }

    // Updates the username of a specified user
    public void setUsername(String oldUsername, String newUsername) throws SQLException {
        String sql = "UPDATE Users SET username = ? WHERE username = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, newUsername);
        query.setString(2, oldUsername);
        query.executeUpdate();
    }

    // Updates the password of a specified user
    public void setPassword(String username, String password) throws SQLException {
        String sql = "UPDATE Users SET password = ? WHERE username = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, password);
        query.setString(2, username);
        query.executeUpdate();
    }

    // Updates the role of a specified user
    public void setRole(String username, String role) throws SQLException {
        String sql = "UPDATE Users SET role = ? WHERE username = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, role);
        query.setString(2, username);
        query.executeUpdate();
    }

    // Creates new record for a new User
    public void createNewUser(String username, String password, String role) throws SQLException {
        String sql = "INSERT INTO Users VALUES(?, ?, ?)";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, username);
        query.setString(2, password);
        query.setString(3, role);
        query.executeUpdate();
    }

    // Deletes a specified user
    public void deleteUser(String username) throws SQLException {
        String sql = "DELETE FROM Users WHERE username = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, username);
        query.executeUpdate();
    }

    // Returns all records of User with a specified role
    public ResultSet getFullRoles(String role) throws SQLException {
        String sql = "SELECT * FROM Users WHERE role = ?";
        PreparedStatement query = con.prepareStatement(sql);
        query.setString(1, role);
        return query.executeQuery();
    }



}
