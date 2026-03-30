package cust.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Order {
    private String accountHolderID;
    private String orderID;
    private double cost;
    private String orderDate;
    private ArrayList<OrderItem> itemsOrdered;
    private Reminder status1stReminder;
    private Reminder status2ndReminder;
    private ReminderDate date1stReminder;
    private ReminderDate date2ndReminder;

    public enum Reminder {
        NO_NEED("no_need"),
        DUE("due"),
        SENT("sent");

        private final String text;
        Reminder(String text){
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    static public String[] ordersByAccountColumnId(){
        return new String[] {"Order ID", "Cost, £", "Ordered", "ItemID", "Description", "Quantity", "Unit cost, £", "Amount, £"};
    }

    public Order(String accountHolderID, String orderID, String orderDate, ArrayList<OrderItem> itemsOrdered) {
        this.accountHolderID = accountHolderID;
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.itemsOrdered = itemsOrdered;
        cost = 0.0;

        for (OrderItem i : itemsOrdered){
            cost += i.getTotal();
        }
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

    public ArrayList<OrderItem> getItemsOrdered() {
        return itemsOrdered;
    }

    /// ////////////////////////////////////////

    public String[] getOrderIdRowData(){
        return new String[]{orderID, Double.toString(cost), orderDate, "", "", "", "", ""};
    }
}
