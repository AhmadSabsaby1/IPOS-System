package ipos.ca.login.controller;

import ipos.ca.login.db.UserDB;
import ipos.ca.login.model.Session;
import ipos.ca.login.model.User;
import ipos.ca.login.model.UserRole;

import java.util.Optional;

/**
 * LoginController – business logic for UC15 (Login) and UC16 (Logout).
 * The view calls these methods; this class knows nothing about Swing.
 */
public class LoginController {

    /**
     * UC15 – Login.
     * @return the authenticated User on success, null on failure.
     */
    public User login(String username, String password) {
        if (username == null || username.isBlank()) return null;
        if (password == null || password.isBlank()) return null;

        Optional<User> opt = UserDB.authenticate(username, password);
        if (opt.isEmpty()) return null;

        Session.getInstance().login(opt.get());
        System.out.println("[Session] Logged in: " + opt.get());
        return opt.get();
    }

    /**
     * UC16 – Logout.
     * Clears the current session.
     */
    public void logout() {
        if (Session.getInstance().isLoggedIn()) {
            System.out.println("[Session] Logged out: " + Session.getInstance().getCurrent());
        }
        Session.getInstance().logout();
    }

    /** Convenience for views – role-based access check. */
    public boolean currentUserHasRole(UserRole role) {
        return Session.getInstance().hasRole(role);
    }
}
