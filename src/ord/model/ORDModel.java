package ord.model;

import Api.ISAOrder_Implementation;
import custom.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ORDModel {
    private ISAOrder_Implementation ISAOrderAPI;
    
    private ArrayList<Item> catalogueSA;

    //the cart list of items
    private ArrayList<CartItem> cartList;

    public ORDModel() {
        cartList = new ArrayList<>();

        ISAOrderAPI = new ISAOrder_Implementation();
        populateCatalogue();
    }

    /// /////////////// PRIVATE //////////////////
    private void populateCatalogue() {
        //TODO get the catalogue
        //String[] rawCat = ISAOrderAPI.getCatalogue();
        String[] rawCat = new String[]{
                "{\"itemId\":\"100000001\", \"description\":\"Paracetamol\", \"packageType\":\"box\", \"unit\":\"Caps\", \"unitsInAPack\":20, \"packageCost\":0.1}",
                "{\"itemId\":\"100000002\", \"description\":\"Aspirin\", \"packageType\":\"box\", \"unit\":\"Caps\", \"unitsInAPack\":20, \"packageCost\":0.5}"
        };

        catalogueSA = new ArrayList<>();
        for (String json : rawCat) {
            JsonObject o = JsonObject.parse(json);
            System.out.println("JsonString: " + o.toJsonString());
            catalogueSA.add(new Item(
                    o.get("itemId"),
                    o.get("description"),
                    o.get("packageType"),
                    o.get("unit"),
                    o.getInt("unitsInAPack"),
                    o.getDouble("packageCost"),
                    0,
                    0
            ));
        }
    }
    /// ///////////////////////////////////////////

    public ArrayList<Item> getCatalogue(){
        return catalogueSA;
    }

    public ArrayList<Order> getOrders(String merchantId){
        ArrayList<Order> orders = new ArrayList<>();
        //TODO make it String
        String [] rawOrders = ISAOrderAPI.viewPreviousOrders(1);
        for (String json : rawOrders) {
            JsonObject o = JsonObject.parse(json);
            //TODO fill the orders
            //orders.add(new Order());
        }

        return orders;
    }

    public Item getItemByID(String id) {
        for (Item item : catalogueSA) {
            if (item.getId() == id){
                return item;
            }
        }

        return null;
    }

    public CartItem addToCart(String id, int quantity){
        //checks if the item is already in the cart
        if (cartItemExists(id))
            return null;

        //if not in the cart, we get the item to be added from the DB/API
        CartItem ci = new CartItem(id, getItemByID(id).getDescription(), quantity, getItemByID(id).getCost());
        cartList.add(ci);

        //we return the newly created item. This might be useful in the
        // future, but for now it's not actually used
        return ci;
    }

    public ArrayList<CartItem> getCartList(){
        return cartList;
    }

    public boolean cartItemExists(String id){
        for(CartItem item : cartList){
            if(item.getItemId().equals(id)){
                return true;
            }
        }

        return false;
    }

    public void createOrder(String merchantId){
        //TODO create the order and send it to SA
        Map<String, String> orderDetails = new HashMap<>();

        for (CartItem i : cartList) {
            orderDetails.put("itemId", i.getItemId());
            orderDetails.put("quantity", String.valueOf(i.getQuantity()));
        }

        cartList = new ArrayList<>();
        //ISAOrderAPI.placeOrder(merchantId, orderDetails);
    }

    public void removeAllCartItems() {
        cartList.clear();
    }

    public double calculateGrandTotal() {
        double total = 0;
        for (CartItem item : cartList) {
            total += item.getTotal();
        }
        return total;
    }

    public void removeFromCart(String itemId) {
        for(CartItem item : cartList){
            if (item.getItemId().equals(itemId)){
                cartList.remove(item);
                return;
            }
        }
    }

    public void changeCartItemQuantity(String id, int quantity) {
        for (CartItem item : cartList) {
            if (item.getItemId().equals(id)) {
                item.setQuantity(quantity);
            }
        }
    }

    public boolean isCartEmpty() {
        return cartList.isEmpty();
    }

    public ArrayList<Item> searchByField(String field, String searchText) {
        searchText = searchText.toLowerCase();
        ArrayList<Item> found = new ArrayList<>();
        for (Item item : catalogueSA) {
            if (field.equals(Item.ITEM_ID)) {
                if (item.getId().toLowerCase().contains(searchText))
                    found.add(item);
            }else if (field.equals(Item.DESCRIPTION)) {
                if (item.getDescription().toLowerCase().contains(searchText))
                    found.add(item);
            }
        }

        return found;
    }
}
