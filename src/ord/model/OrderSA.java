package ord.model;

import java.util.ArrayList;

public class OrderSA {
    private String merchantID;
    private String orderId;
    private String orderDate;
    private ArrayList<CartItem> itemsOrdered;
    private double total;
    private double discountAmount;
    private double amountDue;
    private String orderStatus;

    static public String[] previousOrdersColumnId(){
        return new String[] {"Order ID", "Amount Due, £", "Discount, £", "Ordered On", "ItemID", "Quantity", "Unit cost, £", "Amount, £"};
    }

    static public String[] orderProgressColumnId(){
        return new String[] {"Order ID", "Date Ordered", "Amount Due, £", "Discount, £", "Status"};
    }

    public OrderSA(String merchantID, String orderId, String orderDate, double total, double discountAmount, double amountDue, String status, ArrayList<CartItem> itemsOrdered) {
        this.merchantID = merchantID;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.itemsOrdered = itemsOrdered;
        this.total = total;
        this.discountAmount = discountAmount;
        this.amountDue = amountDue;

        orderStatus = status;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public ArrayList<CartItem> getItemsOrdered() {
        return itemsOrdered;
    }

    public double getTotal() {
        return total;
    }

    public double getAmountDue(){
        return amountDue;
    }

    public String getStatus() {
        return orderStatus;
    }

    //{"Order ID", "Amount Due, £", "Discount, £", "Ordered On", "ItemID", "Quantity", "Unit cost, £", "Amount, £"};
    public String[] getPrevOrdersRowData(){
        return new String[] {orderId, Double.toString(amountDue), Double.toString(discountAmount), orderDate, "", "", "", ""};
    }

    public String[] getPrevOrderItemRowData(int index){
        CartItem item = itemsOrdered.get(index);
        return new String[] {"", "", "", "", item.getItemId(), Integer.toString(item.getQuantity()), Double.toString(item.getCost()), Double.toString(item.getTotal())};
    }

    //{"Order ID", "Date Ordered", "Amount Due, £", "Discount, £", "Status"};
    public String[] getOrderProgressRowData(){
        return new String[] {orderId, orderDate, Double.toString(amountDue), Double.toString(discountAmount), orderStatus};
    }
}
