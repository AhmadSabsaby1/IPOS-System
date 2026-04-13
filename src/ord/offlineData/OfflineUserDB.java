package ord.offlineData;

import Api.SessionManager;

import java.util.ArrayList;

public class OfflineUserDB {
    private ArrayList<MerchantUser> users;

    public OfflineUserDB() {
        populateOfflineDB();
    }

    private void populateOfflineDB() {
        users = new ArrayList<>();
        users.add(new MerchantUser("ACC01", "city", "north"));
        users.add(new MerchantUser("ACC02", "a", "a"));
    }

    public boolean checkCredentials(String username, String password) {
        for (MerchantUser u : users) {
            if (u.getUserName().equals(username) && u.getPassword().equals(password)) {
                SessionManager.merchant_Id = u.getId();
                return true;
            }
        }

        return false;
    }
}
