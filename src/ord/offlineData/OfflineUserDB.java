package ord.offlineData;

public class OfflineUserDB {
    private String id;
    private String userName;
    private String password;

    public OfflineUserDB(String id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
