package cust.model;

public class AccountHolder {
    public static String ACCOUNT_ID = "Account ID";
    public static String NAME = "Name";
    public static String ADDRESS = "Address";
    public static String CARD_TYPE = "Card Type";
    public static String FIRST_DIGITS = "Card's First Four Digits";
    public static String LAST_DIGITS = "Card's Last Four Digits";
    public static String EXPIRY_DATE = "Card's Expiry Date";
    public static String BALANCE = "Balance";
    public static String DISCOUNT_TYPE = "Discount's Type";
    public static String DISCOUNT = "Discount";
    public static String STATUS = "Status";
    public static String STATUS_1ST = "Status 1st";
    public static String STATUS_2ND = "Status 2nd";


    private String accountId;
    private String name;
    private String address;
    private String cardType;
    private String firstFour;
    private String lastFour;
    private String expiryDate;
    private double balance;
    private String discountType;
    private double discount;
    private String status;
    private String status1stReminder;
    private String status2ndReminder;

    static public String[] accountColumnId(){
        return new String[] {"Account ID", "Name", "Status"};
    }

    public enum Status {
        NORMAL("normal"),
        SUSPENDED("suspended"),
        IN_DEFAULT("in_default");

        private final String text;
        Status(String status) {
            this.text = status;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public AccountHolder(String accountId, String name, String address, String cardType, String firstFour, String lastFour, String expiryDate, double balance, String discountType, double discount, String status, String status1stReminder, String status2ndReminder) {
        this.accountId = accountId;
        this.name = name;
        this.address = address;
        this.cardType = cardType;
        this.firstFour = firstFour;
        this.lastFour = lastFour;
        this.expiryDate = expiryDate;
        this.balance = balance;
        this.discountType = discountType;
        this.discount = discount;
        this.status = status;
        this.status1stReminder = status1stReminder;
        this.status2ndReminder = status2ndReminder;
    }

    public AccountHolder(String name, String address, String cardType, String firstFour, String lastFour, String expiryDate, double balance, String discountType, double discount, String status) {
        accountId = "Placeholder";
        this.name = name;
        this.address = address;
        this.cardType = cardType;
        this.firstFour = firstFour;
        this.lastFour = lastFour;
        this.expiryDate = expiryDate;
        this.balance = balance;
        this.discountType = discountType;
        this.discount = discount;
        this.status = status;

        status1stReminder = "";
        status2ndReminder = "";
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

    public String getCardType() {
        return cardType;
    }

    public String getFirstFour() {
        return firstFour;
    }

    public String getLastFour() {
        return lastFour;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public double getBalance() {
        return balance;
    }

    public String getDiscountType() {
        return discountType;
    }

    public double getDiscount() {
        return discount;
    }

    public String getStatus() {
        return status;
    }

    public String getStatus1stReminder() {
        return status1stReminder;
    }

    public String getStatus2ndReminder() {
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
        else if (field.equals(CARD_TYPE))
            cardType = value;
        else if (field.equals(FIRST_DIGITS))
            firstFour = value;
        else if (field.equals(LAST_DIGITS))
            lastFour = value;
        else if (field.equals(EXPIRY_DATE))
            expiryDate = value;
        else if (field.equals(BALANCE))
            balance = Double.parseDouble(value);
        else if (field.equals(DISCOUNT_TYPE))
            discountType = value;
        else if (field.equals(DISCOUNT))
            discount = Double.parseDouble(value);
        else if (field.equals(STATUS))
            status = value;
        else if (field.equals(STATUS_1ST))
            status1stReminder = value;
        else if (field.equals(STATUS_2ND))
            status2ndReminder = value;
    }

    public String[] accountRowData(){
        return new String[] {accountId, name, status};
    }
}
