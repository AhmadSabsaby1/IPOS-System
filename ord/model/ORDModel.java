package ord.model;

import ord.mock.MOCKISAOrderAPI;

import java.util.ArrayList;

public class ORDModel {
    //the mock DB to test the code
    private MOCKISAOrderAPI ISAOrderAPIController;
    
    private ArrayList<Item> catalogue;

    //the cart list of items
    private ArrayList<CartItem> cartList;

    public ORDModel() {
        ISAOrderAPIController = new MOCKISAOrderAPI();
        cartList = new ArrayList<>();
        catalogue = ISAOrderAPIController.getCatalogue();
    }

    public ArrayList<Item> getCatalogue(){
        return catalogue;
    }

    public ArrayList<Order> getOrders(String merchantId){
        return ISAOrderAPIController.getOrders(merchantId);
    }

    public Item getItemByID(String id) {
        for (Item item : catalogue) {
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
        CartItem ci = new CartItem(id, getItemByID(id).getDescription(), quantity, getItemByID(id).cost);
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
        ISAOrderAPIController.createOrder(merchantId, cartList);
        cartList = new ArrayList<>();
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
}
