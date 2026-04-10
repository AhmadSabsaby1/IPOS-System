package cust.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Order {
    private String accountHolderID;
    private String orderID;
    private String shippingAddress;
    private PaymentType paymentType;
    private double amountReceived;
    private double totalCost;
    private String orderDate;

    private ArrayList<OrderItem> itemsOrdered;

    /// /////////////// ENUM //////////////////////
    public enum PaymentType{
        CARD("card"),
        CASH("cash"),
        NONE("none");

        private String paymentType;

        public static String[] getOptions() {
            return new String[] {NONE.toString(), CARD.toString(), CASH.toString()};
        }

        public static PaymentType getValue(String paymentType){
            if (paymentType.equals(CARD.toString())){
                return CARD;
            }else if (paymentType.equals(CASH.toString())){
                return CASH;
            }else if (paymentType.equals(NONE.toString())){
                return NONE;
            }

            return null;
        }

        PaymentType(String paymentType){
            this.paymentType = paymentType;
        }

        @Override
        public String toString(){
            return paymentType;
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
    /// ////////////////////////////

    static public String[] ordersByAccountColumnId(){
        return new String[] {"Order ID", "Cost, £", "Paid", "Ordered", "ItemID", "Description", "Quantity", "Unit cost, £", "Amount, £"};
    }

    public Order(ResultSet rs) throws SQLException {
        if (rs.isBeforeFirst())
            rs.next();

        orderID = rs.getString("orderID");
        paymentType = PaymentType.getValue(rs.getString("paymentType"));
        amountReceived = rs.getDouble("amountReceived");
        orderDate = rs.getString("orderDate");
        shippingAddress = rs.getString("shippingAddress");
        totalCost = rs.getDouble("totalCost");
    }

    public Order(String accountHolderID, String orderID, String orderDate, double totalCost, ArrayList<OrderItem> itemsOrdered) {
        this.accountHolderID = accountHolderID;
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.itemsOrdered = itemsOrdered;
        this.totalCost = totalCost;
    }

    public Order(
            String orderId,
            String paymentType,
            double amount,
            String shipping,
            String date,
            double totalCost,
            ArrayList<OrderItem> itemsOrdered
    ){
        this.orderID = orderId;
        this.paymentType = PaymentType.getValue(paymentType);
        this.amountReceived = amount;
        this.shippingAddress = shipping;
        this.orderDate = date;
        this.itemsOrdered = itemsOrdered;
        this.totalCost = totalCost;
    }

    ///  /////////////////// GETTERS //////////////////
    public String getOrderID() {
        return orderID;
    }

    public String getAccountHolderID() {
        return accountHolderID;
    }

    public String getOrderDate() {
        return orderDate;
    }
    public String getShippingAddress() {
        return shippingAddress;
    }

    public double getTotalCost(){
        return totalCost;
    }

    public ArrayList<OrderItem> getItemsOrdered() {
        return itemsOrdered;
    }

    /// ////////////////////////////////////////

    public String[] getOrderIdRowData(){
        return new String[]{orderID, Double.toString(totalCost), isPaid() ? "Yes" : "No", orderDate, "", "", "", "", ""};
    }

    public boolean isPaid(){
        return amountReceived == totalCost;
    }
}
