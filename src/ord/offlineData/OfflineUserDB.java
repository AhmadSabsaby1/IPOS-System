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
        users.add(new MerchantUser(
                "cosymed",
                "bondstreet",
                "Ahmad123",
                "ACC0002",
                "Cosymed Ltd",
                "Mr Alex Wright",
                "Sabsaby@hotmail.com",
                "0207 321 8001",
                "25, Bond Street, London WC1V 8LS",
                5000,
                "flexible",
                null,
                "normal",
                null
        ));
    }

    public boolean checkCredentials(String username, String password) {
        for (MerchantUser u : users) {
            if (u.getUserName().equals(username) && u.getPassword().equals(password)) {
                SessionManager.merchant_Id = u.getMerchant_Id();
                SessionManager.company_name = u.getCompany_name();
                SessionManager.fixed_discount_rate = u.getFixed_discount_rate();
                SessionManager.address = u.getAddress();
                SessionManager.account_number = u.getAccount_number();
                SessionManager.account_status = u.getAccount_status();
                SessionManager.contact_email = u.getContact_email();
                SessionManager.contact_phone = u.getContact_phone();
                SessionManager.credit_limit = u.getCredit_limit();
                SessionManager.flexible_thresholds = u.getFlexible_thresholds();
                SessionManager.contact_name = u.getContact_name();
                SessionManager.discount_plan_type = u.getDiscount_plan_type();

                System.out.println("Offline login");
                return true;
            }
        }

        return false;
    }
}
