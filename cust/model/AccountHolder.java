package cust.model;

import javax.smartcardio.Card;

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
    private CardType cardType;
    private String firstFour;
    private String lastFour;
    private String expiryDate;
    private double balance;
    private DiscountType discountType;
    private double discount;
    private AccountStatus status;
    private ReminderStatus status1stReminder;
    private ReminderStatus status2ndReminder;

    static public String[] accountColumnId(){
        return new String[] {"Account ID", "Name", "Status"};
    }

    /// ////////////// ENUMS ///////////////////////

    public enum DiscountType{
        FIXED("fixed"),
        FLEXIBLE("flexible");

        public String discountType;

        public static String[] getOptions(){
            return new String[] {FIXED.toString(), FLEXIBLE.toString()};
        }

        public static DiscountType getValue(String discountType){
            if (discountType.equals(FIXED.toString())){
                return FIXED;
            }else if (discountType.equals(FLEXIBLE.toString())){
                return FLEXIBLE;
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

    public enum CardType{
        CREDIT("credit"),
        DEBIT("debit");

        private String cardType;

        public static String[] getOptions() {
            return new String[] {CREDIT.toString(), DEBIT.toString()};
        }

        public static CardType getValue(String cardType){
            if (cardType.equals(CREDIT.toString())){
                return CREDIT;
            }else if (cardType.equals(DEBIT.toString())){
                return DEBIT;
            }

            return null;
        }

        CardType(String cardType){
            this.cardType = cardType;
        }

        @Override
        public String toString(){
            return cardType;
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

    public AccountHolder(String accountId, String name, String address, CardType cardType, String firstFour, String lastFour, String expiryDate, double balance, DiscountType discountType, double discount) {
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

        this.status = AccountStatus.NORMAL;
        this.status1stReminder = ReminderStatus.NO_NEED;
        this.status2ndReminder = ReminderStatus.NO_NEED;
    }

    public AccountHolder(String name, String address, CardType cardType, String firstFour, String lastFour, String expiryDate, double balance, DiscountType discountType, double discount) {
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

    public String getCardType() {
        return cardType.toString();
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
        return discountType.toString();
    }

    public double getDiscount() {
        return discount;
    }

    public String getStatus() {
        return status.toString();
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
        else if (field.equals(CARD_TYPE))
            cardType = CardType.getValue(value);
        else if (field.equals(FIRST_DIGITS))
            firstFour = value;
        else if (field.equals(LAST_DIGITS))
            lastFour = value;
        else if (field.equals(EXPIRY_DATE))
            expiryDate = value;
        else if (field.equals(BALANCE))
            balance = Double.parseDouble(value);
        else if (field.equals(DISCOUNT_TYPE))
            discountType = DiscountType.getValue(value);
        else if (field.equals(DISCOUNT))
            discount = Double.parseDouble(value);
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
