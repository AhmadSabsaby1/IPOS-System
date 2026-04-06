package ipos.ca.login.db;

import ipos.ca.login.model.User;
import ipos.ca.login.model.UserRole;

import database.DBUsers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UserDB – bridges the login package to the real DBUsers database class.
 * Replaces MockUserDB entirely. All DB errors are caught here and logged
 * so the controllers stay clean.
 *
 * Note: DBUsers (and DBParent) do NOT validate nulls — that is done here
 * before any DB call is made, as stated in the README.
 */
public class UserDB {

    // -----------------------------------------------------------------------
    // UC15 – Authenticate
    // -----------------------------------------------------------------------

    /**
     * Returns the matching active User if credentials are correct, empty otherwise.
     */
    public static Optional<User> authenticate(String username, String password) {
        if (isBlank(username) || isBlank(password)) return Optional.empty();
        try {
            DBUsers db = new DBUsers();
            ResultSet rs = db.getUserInfo(username);
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                String role           = rs.getString("role");
                if (storedPassword.equals(password)) {
                    return Optional.of(mapRow(rs, username, role));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("[UserDB] authenticate error: " + e.getMessage());
        }
        return Optional.empty();
    }

    // -----------------------------------------------------------------------
    // UC1 – Create user account
    // -----------------------------------------------------------------------

    /**
     * Creates a new user. Returns true on success, false if username already
     * exists or a DB error occurs.
     */
    public static boolean createUser(String username, String password, UserRole role) {
        if (isBlank(username) || isBlank(password) || role == null) return false;
        // Check for duplicate first (alternative flow: username already exists)
        if (findByUsername(username).isPresent()) {
            System.out.println("[UserDB] createUser failed – username already exists: " + username);
            return false;
        }
        try {
            DBUsers db = new DBUsers();
            db.createNewUser(username, password, role.name());
            System.out.println("[UserDB] Created user: " + username + " role=" + role);
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("[UserDB] createUser error: " + e.getMessage());
            return false;
        }
    }

    // -----------------------------------------------------------------------
    // UC2 – Delete user account
    // -----------------------------------------------------------------------

    /**
     * Deletes the user with the given username. Returns true on success.
     */
    public static boolean deleteUser(String username) {
        if (isBlank(username)) return false;
        try {
            DBUsers db = new DBUsers();
            db.deleteUser(username);
            System.out.println("[UserDB] Deleted user: " + username);
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("[UserDB] deleteUser error: " + e.getMessage());
            return false;
        }
    }

    // -----------------------------------------------------------------------
    // UC10 – Assign role
    // -----------------------------------------------------------------------

    /**
     * Updates the role of the given user. Returns true on success.
     */
    public static boolean assignRole(String username, UserRole role) {
        if (isBlank(username) || role == null) return false;
        try {
            DBUsers db = new DBUsers();
            db.setRole(username, role.name());
            System.out.println("[UserDB] Assigned role " + role + " to " + username);
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("[UserDB] assignRole error: " + e.getMessage());
            return false;
        }
    }

    // -----------------------------------------------------------------------
    // Lookups (used by admin table view + duplicate checks)
    // -----------------------------------------------------------------------

    /**
     * Returns all users in the DB — used to populate the admin JTable.
     */
    public static List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            DBUsers db = new DBUsers();
            ResultSet rs = db.getUsers();
            while (rs.next()) {
                String username = rs.getString("username");
                String role     = rs.getString("role");
                list.add(mapRow(rs, username, role));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("[UserDB] getAllUsers error: " + e.getMessage());
        }
        return list;
    }

    /**
     * Returns a single user by username, or empty if not found.
     */
    public static Optional<User> findByUsername(String username) {
        if (isBlank(username)) return Optional.empty();
        try {
            DBUsers db = new DBUsers();
            ResultSet rs = db.getUserInfo(username);
            if (rs.next()) {
                return Optional.of(mapRow(rs, username, rs.getString("role")));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("[UserDB] findByUsername error: " + e.getMessage());
        }
        return Optional.empty();
    }

    // -----------------------------------------------------------------------
    // Private helpers
    // -----------------------------------------------------------------------

    /**
     * Maps a ResultSet row to a User object.
     * The DB schema is: username, password, role — no ID column.
     * We use username as the unique key (it is the PK in the Users table).
     */
    private static User mapRow(ResultSet rs, String username, String roleStr) throws SQLException {
        String password = rs.getString("password");
        UserRole role;
        try {
            role = UserRole.valueOf(roleStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Gracefully fall back if someone stored a role string that doesn't match our enum
            System.err.println("[UserDB] Unknown role in DB: " + roleStr + " — defaulting to PHARMACIST");
            role = UserRole.PHARMACIST;
        }
        // User model: username used as both ID and username since that's the PK
        return new User(username, password, role);
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
