package ord.offlineData;

public class MerchantUser {

    private String id;
    private String userName;
    private String password;

    public String merchant_Id = "";
    public String account_number = "";
    public String company_name = "";
    public String contact_name = "";
    public String contact_email = "";
    public String contact_phone = "";

    public String address = "";
    public double credit_limit = 0.0;
    public String discount_plan_type = "";
    public double fixed_discount_rate = 0.0;
    public String account_status = "";
    public String flexible_thresholds = "";
    public int tier_1_threshold = 0;
    public int tier_2_threshold = 0;
    public double tier_1_discount = 0.0;
    public double tier_2_discount = 0.0;
    public double tier_3_discount = 0.0;

    public MerchantUser(
            String userName,
            String password,
            String merchant_Id,
            String account_number,
            String company_name,
            String contact_name,
            String contact_email,
            String contact_phone,
            String address,
            double credit_limit,
            String discount_plan_type,
            double fixed_discount_rate,
            String account_status,
            String flexible_thresholds) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.merchant_Id = merchant_Id;
        this.account_number = account_number;
        this.company_name = company_name;
        this.contact_name = contact_name;
        this.contact_email = contact_email;
        this.contact_phone = contact_phone;
        this.address = address;
        this.credit_limit = credit_limit;
        this.discount_plan_type = discount_plan_type;
        this.fixed_discount_rate = fixed_discount_rate;
        this.account_status = account_status;
        this.flexible_thresholds = flexible_thresholds;



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

    public String getMerchant_Id() {
        return merchant_Id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getContact_name() {
        return contact_name;
    }

    public String getContact_email() {
        return contact_email;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public String getAddress() {
        return address;
    }

    public double getCredit_limit() {
        return credit_limit;
    }

    public String getDiscount_plan_type() {
        return discount_plan_type;
    }

    public double getFixed_discount_rate() {
        return fixed_discount_rate;
    }

    public String getAccount_status() {
        return account_status;
    }

    public String getFlexible_thresholds() {
        return flexible_thresholds;
    }
}
