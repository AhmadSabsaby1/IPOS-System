package cust.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountHolder {
    public static String ACCOUNT_ID = "Account ID";
    public static String NAME = "Name";
    public static String ADDRESS = "Address";
    public static String PHONE = "Phone Number";
    public static String EMAIL = "Email";
    public static String BALANCE = "Balance";
    public static String BALANCE_LIMIT = "Balance Limit";
    public static String DISCOUNT_TYPE = "Discount's Type";
    public static String FIXED_DISCOUNT = "Fixed Discount";
    public static String TIER_1_DISCOUNT = "Tier 1 Discount";
    public static String TIER_2_DISCOUNT = "Tier 2 Discount";
    public static String TIER_3_DISCOUNT = "Tier 3 Discount";
    public static String TIER_1_THRESHOLD = "Tier 1 Threshold";
    public static String TIER_2_THRESHOLD = "Tier 2 Threshold";
    public static String STATUS = "Status";
    public static String STATUS_1ST = "Status 1st";
    public static String STATUS_2ND = "Status 2nd";


    private String accountId;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private double balance;
    private int balanceLimit;
    private DiscountType discountType;
    private double fixedDiscount;
    private double tier1Discount;
    private int tier1Threshold;
    private double tier2Discount;
    private int tier2Threshold;
    private double tier3Discount;
    private AccountStatus status;
    private ReminderStatus status1stReminder;
    private ReminderStatus status2ndReminder;

    static public String[] accountColumnId(){
        return new String[] {"Account ID", "Name", "Status"};
    }

    /// ////////////// ENUMS ///////////////////////

    public enum DiscountType{
        NONE("none"),
        FIXED("fixed"),
        FLEXIBLE("flexible");

        public String discountType;

        public static String[] getOptions(){
            return new String[] {NONE.toString(), FIXED.toString(), FLEXIBLE.toString()};
        }

        public static DiscountType getValue(String discountType){
            if (discountType.equals(FIXED.toString())){
                return FIXED;
            }else if (discountType.equals(FLEXIBLE.toString())){
                return FLEXIBLE;
            }else if (discountType.equals(NONE.toString())){
                return NONE;
            }

            return null;
        }

        DiscountType(String discountType){
            this.discountType = discountType;
        }

        @Override
        public String toString(){
            return discountType;
        }
    }

    public enum AccountStatus {
        NORMAL("normal"),
        SUSPENDED("suspended"),
        IN_DEFAULT("in_default");

        private final String text;

        public static String[] getOptions() {
            return new String[] {NORMAL.toString(), SUSPENDED.toString(), IN_DEFAULT.toString()};
        }

        public static AccountStatus getValue(String status){
            if (status.equals(NORMAL.toString())){
                return NORMAL;
            }else if (status.equals(SUSPENDED.toString())){
                return SUSPENDED;
            }else if (status.equals(IN_DEFAULT.toString())){
                return IN_DEFAULT;
            }

            return null;
        }
        AccountStatus(String status) {
            this.text = status;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public enum ReminderStatus {
        NO_NEED("no_need"),
        SENT("sent"),
        DUE("due");

        public static String[] getOptions() {
            return new String[] {NO_NEED.toString(), SENT.toString(), DUE.toString()};
        }
        private final String text;
        ReminderStatus(String status) {
            this.text = status;
        }

        public static ReminderStatus getValue(String status) {
            if (status.equals(NO_NEED.toString())){
                return NO_NEED;
            }else if (status.equals(SENT.toString())){
                return SENT;
            }else if (status.equals(DUE.toString())){
                return DUE;
            }

            return null;
        }

        @Override
        public String toString() {
            return text;
        }
    }
    /// ////////////// END OF ENUMS ///////////////////////////

    public AccountHolder(ResultSet rs) throws SQLException {
        if (rs.isBeforeFirst())
            rs.next();

        String accountId = rs.getString("accountId");
        String name = rs.getString("name");
        String address = rs.getString("address");
        String phoneNumber = rs.getString("phoneNum");
        email = rs.getString("email");
        double balance = rs.getDouble("balance");
        int balanceLimit = rs.getInt("balanceLimit");
        AccountHolder.DiscountType discountType = AccountHolder.DiscountType.getValue(rs.getString("discountType"));
        double discount = rs.getDouble("discount");
        double tier1Discount = rs.getDouble("tier1Discount");
        int tier1Threshold = rs.getInt("tier1Threshold");
        double tier2Discount = rs.getDouble("tier2Discount");
        int tier2Threshold = rs.getInt("tier2Threshold");
        double tier3Discount = rs.getDouble("tier3Discount");
        AccountHolder.AccountStatus status = AccountHolder.AccountStatus.getValue(rs.getString("status"));
        AccountHolder.ReminderStatus status1stReminder = AccountHolder.ReminderStatus.getValue(rs.getString("status1stReminder"));
        AccountHolder.ReminderStatus status2ndReminder = AccountHolder.ReminderStatus.getValue(rs.getString("status2ndReminder"));

        this.accountId = accountId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.balanceLimit = balanceLimit;
        this.discountType = discountType;
        this.fixedDiscount = discount;
        this.tier1Discount = tier1Discount;
        this.tier1Threshold = tier1Threshold;
        this.tier2Discount = tier2Discount;
        this.tier2Threshold = tier2Threshold;
        this.tier3Discount = tier3Discount;
        this.status = status;
        this.status1stReminder = status1stReminder;
        this.status2ndReminder = status2ndReminder;
    }

    public AccountHolder(
            String name,
            String address,
            String phoneNumber,
            String email,
            int balanceLimit,
            DiscountType discountType,
            double fixedDiscount
    ){
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.balance = balanceLimit;
        this.balanceLimit = balanceLimit;
        this.discountType = discountType;
        this.fixedDiscount = fixedDiscount;

        this.tier1Threshold = 0;
        this.tier2Threshold = 0;
        this.tier1Discount = 0;
        this.tier2Discount = 0;
        this.tier3Discount = 0;

        this.status = AccountStatus.NORMAL;
        this.status1stReminder = ReminderStatus.NO_NEED;
        this.status2ndReminder = ReminderStatus.NO_NEED;
    }

    public AccountHolder(
            String name,
            String address,
            String phoneNumber,
            String email,
            int balanceLimit,
            DiscountType discountType,
            int tier1Threshold,
            int tier2Threshold,
            double tier1Discount,
            double tier2Discount,
            double tier3Discount
    ){
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.balance = balanceLimit;
        this.balanceLimit = balanceLimit;
        this.discountType = discountType;
        this.fixedDiscount = 0;

        this.tier1Threshold = tier1Threshold;
        this.tier2Threshold = tier2Threshold;
        this.tier1Discount = tier1Discount;
        this.tier2Discount = tier2Discount;
        this.tier3Discount = tier3Discount;

        this.status = AccountStatus.NORMAL;
        this.status1stReminder = ReminderStatus.NO_NEED;
        this.status2ndReminder = ReminderStatus.NO_NEED;
    }

    public AccountHolder(
            String name,
            String address,
            String phoneNumber,
            String email,
            int balanceLimit
    ){
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.balance = balanceLimit;
        this.balanceLimit = balanceLimit;
        this.discountType = DiscountType.NONE;

        this.fixedDiscount = 0;
        this.tier1Threshold = 0;
        this.tier2Threshold = 0;
        this.tier1Discount = 0;
        this.tier2Discount = 0;
        this.tier3Discount = 0;

        this.status = AccountStatus.NORMAL;
        this.status1stReminder = ReminderStatus.NO_NEED;
        this.status2ndReminder = ReminderStatus.NO_NEED;
    }

    /// /////////// GETTERS ////////////////
    public String getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getEmail(){
        return email;
    }

    public double getBalance() {
        return balance;
    }
    public int getBalanceLimit(){
        return balanceLimit;
    }

    public String getDiscountType() {
        return discountType.toString();
    }

    public double getFixedDiscount() {
        return fixedDiscount;
    }

    public double calculateFixedDiscount(double payment){
        return payment * (1 - (fixedDiscount / 100));
    }

    public double getFlexDiscount(double amount) {
        if (amount <= tier1Threshold)
            return tier1Discount;
        else if (amount <= tier2Threshold)
            return tier2Discount;
        else
            return tier3Discount;
    }

    public int getTier1Threshold() {
        return tier1Threshold;
    }

    public int getTier2Threshold() {
        return tier2Threshold;
    }

    public double getTier1Discount() {
        return tier1Discount;
    }

    public double getTier2Discount() {
        return tier2Discount;
    }

    public double getTier3Discount() {
        return tier3Discount;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public ReminderStatus getStatus1stReminder() {
        return status1stReminder;
    }

    public ReminderStatus getStatus2ndReminder() {
        return status2ndReminder;
    }

    /// /////////////////////////////////////

    public void modifyField(String field, String value){
        if (field.equals(ACCOUNT_ID))
            accountId = value;
        else if (field.equals(NAME))
            name = value;
        else if (field.equals(ADDRESS))
            address = value;
        else if (field.equals(BALANCE))
            balance = Double.parseDouble(value);
        else if (field.equals(BALANCE_LIMIT))
            balanceLimit = Integer.parseInt(value);
        else if (field.equals(DISCOUNT_TYPE))
            discountType = DiscountType.getValue(value);
        else if (field.equals(FIXED_DISCOUNT))
            fixedDiscount = Double.parseDouble(value);
        else if (field.equals(TIER_1_THRESHOLD))
            tier1Threshold = Integer.parseInt(value);
        else if (field.equals(TIER_2_THRESHOLD))
            tier2Threshold = Integer.parseInt(value);
        else if (field.equals(TIER_1_DISCOUNT))
            tier1Discount = Double.parseDouble(value);
        else if (field.equals(TIER_2_DISCOUNT))
            tier2Discount = Double.parseDouble(value);
        else if (field.equals(TIER_3_DISCOUNT))
            tier3Discount = Double.parseDouble(value);
        else if (field.equals(STATUS))
            status = AccountStatus.getValue(value);
        else if (field.equals(STATUS_1ST))
            status1stReminder = ReminderStatus.getValue(value);
        else if (field.equals(STATUS_2ND))
            status2ndReminder = ReminderStatus.getValue(value);
    }

    public String[] accountRowData(){
        return new String[] {accountId, name, status.toString()};
    }
}
