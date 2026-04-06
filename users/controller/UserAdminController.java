package users.controller;

import ipos.ca.login.db.UserDB;
import ipos.ca.login.model.Session;
import ipos.ca.login.model.User;
import ipos.ca.login.model.UserRole;

import java.util.List;

/**
 * UserAdminController – UC1 (Create), UC2 (Delete), UC10 (Assign Role).
 * Backed by the real MySQL database via UserDB.
 * Username is the primary key — no numeric IDs.
 */
public class UserAdminController {

    /**
     * UC1 – Create new user account (Admin only).
     * @return true on success, false if username taken or caller not Admin.
     */
    public boolean createUser(String username, String password, UserRole role) {
        if (!callerIsAdmin()) {
            System.err.println("[UserAdmin] Access denied – caller is not Admin.");
            return false;
        }
        boolean ok = UserDB.createUser(username, password, role);
        if (ok) sendWelcomeEmail(username);
        return ok;
    }

    /**
     * UC2 – Delete user account (Admin only).
     * @return true on success.
     */
    public boolean deleteUser(String username) {
        if (!callerIsAdmin()) {
            System.err.println("[UserAdmin] Access denied – caller is not Admin.");
            return false;
        }
        return UserDB.deleteUser(username);
    }

    /**
     * UC10 – Assign role (Admin only).
     * @return true on success.
     */
    public boolean assignRole(String username, UserRole role) {
        if (!callerIsAdmin()) {
            System.err.println("[UserAdmin] Access denied – caller is not Admin.");
            return false;
        }
        return UserDB.assignRole(username, role);
    }

    /** Returns all users for the admin JTable. */
    public List<User> getAllUsers() {
        if (!callerIsAdmin()) return List.of();
        return UserDB.getAllUsers();
    }

    // -----------------------------------------------------------------------
    // Private helpers
    // -----------------------------------------------------------------------

    private boolean callerIsAdmin() {
        return Session.getInstance().hasRole(UserRole.ADMIN);
    }

    /** Mock email – replace with real IPOS-PU COMMS call when ready. */
    private void sendWelcomeEmail(String username) {
        System.out.println("[MockEmail] Credentials emailed to user: " + username);
    }
}
