package ord.model;

import java.util.ArrayList;

public class Order {
    private String merchantID;
    private String orderId;
    private String ordered;
    private ArrayList<CartItem> itemsOrdered;
    private double cost;

    //order status
    private String dispatched;
    private String delivered;
    private String paid;

    static public String[] previousOrdersColumnId(){
        return new String[] {"Order ID", "Cost, £", "Ordered", "ItemID", "Quantity", "Unit cost, £", "Amount, £"};
    }

    static public String[] orderProgressColumnId(){
        return new String[] {"Order ID", "Ordered", "Amount, £", "Dispatched", "Delivered", "Paid"};
    }

    public Order(String merchantID, String orderId, String ordered, ArrayList<CartItem> itemsOrdered) {
        this.merchantID = merchantID;
        this.orderId = orderId;
        this.ordered = ordered;
        this.itemsOrdered = itemsOrdered;

        cost = 0;
        for(CartItem item : itemsOrdered){
            cost += item.getTotal();
        }

        dispatched = "Pending";
        delivered = "Pending";
        paid = "Pending";
    }

    public String getMerchantID() {
        return merchantID;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrdered() {
        return ordered;
    }

    public ArrayList<CartItem> getItemsOrdered() {
        return itemsOrdered;
    }

    public double getCost() {
        return cost;
    }

    public String getDispatched() {
        return dispatched;
    }

    public String getDelivered() {
        return delivered;
    }

    public String getPaid() {
        return paid;
    }

    public String[] getPrevOrdersRowData(){
        return new String[] {orderId, Double.toString(cost), ordered, "", "", "", ""};
    }

    public String[] getPrevOrderItemRowData(int index){
        CartItem item = itemsOrdered.get(index);
        return new String[] {"", "", "", item.getItemId(), Integer.toString(item.getQuantity()), Double.toString(item.getCost()), Double.toString(item.getTotal())};
    }

    public String[] getOrderProgressRowData(){
        return new String[] {orderId, ordered, Double.toString(cost), dispatched, delivered, paid};
    }
}
