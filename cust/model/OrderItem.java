package cust.model;

public class OrderItem {
    private String itemId;
    private String description;
    private int quantity;
    private double cost;
    private double total;

    static public String[] cartItemColumnId(){
        return new String[] {"Item ID", "Description", "Quantity", "Unit Cost, £", "Total, £"};
    }

    public OrderItem(String itemId, String description, int quantity, double cost) {
        this.itemId = itemId;
        this.description = description;
        this.quantity = quantity;
        this.cost = cost;

        calculateTotal();
    }

    private void calculateTotal(){
        total = quantity * cost;
    }

    /// ///////////// GETTER /////////////////
    public String getItemId() {
        return itemId;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCost() {
        return cost;
    }

    public double getTotal() {
        return total;
    }
    /// /////////////////////////////////

    public String[] getOrderedItemRowData(){
        return new String[]{"", "", "", itemId, description, String.valueOf(quantity), String.valueOf(cost), String.valueOf(total)};
    }

    public String[] getItemRowData(){
        return new String[]{itemId, description, String.valueOf(quantity), String.valueOf(cost), String.valueOf(total)};
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotal();
    }
}
