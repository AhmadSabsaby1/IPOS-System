package users.controller;

import database.DBUsers;
import users.model.Session;
import users.model.User;
import users.model.UserRole;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * UserAdminController - UC1 (Create), UC2 (Delete), UC10 (Assign Role).
 * Calls DBUsers directly, no UserDB bridge needed.
 */
public class UserAdminController {

    // UC1 - Create new user account (Admin only)
    public boolean createUser(String username, String password, UserRole role) {
        if (!callerIsAdmin()) return false;
        if (username == null || username.isBlank()) return false;
        if (password == null || password.isBlank()) return false;
        if (findByUsername(username) != null) {
            System.out.println("[UserAdmin] Username already exists: " + username);
            return false;
        }
        try {
            DBUsers db = new DBUsers();
            db.createNewUser(username, password, role.name());
            System.out.println("[UserAdmin] Created user: " + username + " role=" + role);
            sendWelcomeEmail(username);
            return true;
        } catch (Exception e) {
            System.err.println("[UserAdmin] createUser error: " + e.getMessage());
            return false;
        }
    }

    // UC2 - Delete user account (Admin only)
    public boolean deleteUser(String username) {
        if (!callerIsAdmin()) return false;
        try {
            DBUsers db = new DBUsers();
            db.deleteUser(username);
            System.out.println("[UserAdmin] Deleted user: " + username);
            return true;
        } catch (Exception e) {
            System.err.println("[UserAdmin] deleteUser error: " + e.getMessage());
            return false;
        }
    }

    // UC10 - Assign role (Admin only)
    public boolean assignRole(String username, UserRole role) {
        if (!callerIsAdmin()) return false;
        try {
            DBUsers db = new DBUsers();
            db.setRole(username, role.name());
            System.out.println("[UserAdmin] Assigned role " + role + " to " + username);
            return true;
        } catch (Exception e) {
            System.err.println("[UserAdmin] assignRole error: " + e.getMessage());
            return false;
        }
    }

    // Returns all users for the admin JTable
    public List<User> getAllUsers() {
        if (!callerIsAdmin()) return List.of();
        List<User> list = new ArrayList<>();
        try {
            DBUsers db = new DBUsers();
            ResultSet rs = db.getUsers();
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String role     = rs.getString("role");
                list.add(new User(username, password, UserRole.valueOf(role.toUpperCase())));
            }
        } catch (Exception e) {
            System.err.println("[UserAdmin] getAllUsers error: " + e.getMessage());
        }
        return list;
    }

    private boolean callerIsAdmin() {
        return Session.getInstance().hasRole(UserRole.ADMIN);
    }

    private User findByUsername(String username) {
        try {
            DBUsers db = new DBUsers();
            ResultSet rs = db.getUserInfo(username);
            if (rs.next()) {
                String password = rs.getString("password");
                String role     = rs.getString("role");
                return new User(username, password, UserRole.valueOf(role.toUpperCase()));
            }
        } catch (Exception e) {
            System.err.println("[UserAdmin] findByUsername error: " + e.getMessage());
        }
        return null;
    }

    private void sendWelcomeEmail(String username) {
        System.out.println("[MockEmail] Credentials emailed to user: " + username);
    }
}
