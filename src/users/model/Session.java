package users.model;

/**
 * Session – singleton that holds the currently logged-in user.
 * Other packages check Session.getCurrent() to know who is logged in
 * and what role they have (for access control).
 */
public class Session {

    private static final Session instance = new Session();
    private User currentUser;

    private Session() {}

    public static Session getInstance() {
        return instance;
    }

    public UserRole getUserRole() {
        if (currentUser == null) {
            return null;
        }

        return currentUser.getRole();
    }

    /** Called on successful login. */
    public void login(User user) {
        this.currentUser = user;
    }

    /** Called on logout – clears the session. */
    public void logout() {
        this.currentUser = null;
    }

    /** @return the logged-in user, or null if no one is logged in. */
    public User getCurrent() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /** Convenience – check if current user has a given role. */
    public boolean hasRole(UserRole role) {
        return isLoggedIn() && currentUser.getRole() == role;
    }
}
