package ipos.ca.login.model;

/**
 * User – matches the DB schema exactly:
 *   Users(username PK, password, role)
 *
 * Username is the primary key so it doubles as the unique identifier.
 */
public class User {

    private String   username;
    private String   password;
    private UserRole role;

    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role     = role;
    }

    public String   getUsername() { return username; }
    public String   getPassword() { return password; }
    public UserRole getRole()     { return role; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(UserRole role)       { this.role     = role; }

    @Override
    public String toString() {
        return "User{username='" + username + "', role=" + role + "}";
    }
}
