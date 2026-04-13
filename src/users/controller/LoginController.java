package users.controller;

import database.DBUsers;
import users.model.Session;
import users.model.User;
import users.model.UserRole;

import java.sql.ResultSet;

/**
 * LoginController – UC15 (Login) and UC16 (Logout).
 * Calls DBUsers directly, no UserDB bridge needed.
 */
public class LoginController {

    /**
     * UC15 – Login.
     * @return authenticated User on success, null on failure.
     */
    public User login(String username, String password) {
        if (username == null || username.isBlank()) return null;
        if (password == null || password.isBlank()) return null;
        try {
            DBUsers db = new DBUsers();
            ResultSet rs = db.getUserInfo(username);
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                String role           = rs.getString("role");
                if (storedPassword.equals(password)) {
                    User user = new User(username, storedPassword, UserRole.valueOf(role.toUpperCase()));
                    Session.getInstance().login(user);
                    System.out.println("[Session] Logged in: " + user);
                    return user;
                }
            }
        } catch (Exception e) {
            System.err.println("[Login] Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * UC16 – Logout. Clears the current session.
     */
    public void logout() {
        if (Session.getInstance().isLoggedIn()) {
            System.out.println("[Session] Logged out: " + Session.getInstance().getCurrent());
        }
        Session.getInstance().logout();
    }

    public boolean currentUserHasRole(UserRole role) {
        return Session.getInstance().hasRole(role);
    }
}
