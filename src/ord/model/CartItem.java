package ord.model;

public class CartItem {
    private String itemId;
    private String description;
    private int quantity;
    private double cost;
    private double total;

    /**
     * A helper method that returns the list of labels for each of the columns in the cart's table.
     * @return an array of Strings containing the labels for a <code>JTable</code>
     */
    static public String[] cartItemColumndId(){
        return new String[] {"Item ID", "Description", "Quantity", "Unit Cost, £", "Total, £"};
    }

    public CartItem(String itemId, String description, int quantity, double cost) {
        this.itemId = itemId;
        this.description = description;
        this.quantity = quantity;
        this.cost = cost;
        calculateTotal();
    }

    private void calculateTotal(){
        total = quantity * cost;
    }

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

    /**
     * Returns the data for a row in the cart's table.
     * @return an array of Strings containing the data of this <code>CartItem</code> to be put in a <code>JTable</code>.
     */
    public String[] rowData(){
        return new String[]{itemId, description, Integer.toString(quantity), Double.toString(cost), Double.toString(total)};
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotal();
    }
}
