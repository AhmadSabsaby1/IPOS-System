package ord.model;

import ord.mock.MOCKCatalogueDB;

import java.util.ArrayList;

public class ORDModel {
    //the mock DB to test the code
    private MOCKCatalogueDB catalogueDB;

    //the cart list of items
    private ArrayList<CartItem> cartList;

    public ORDModel() {
        catalogueDB = new MOCKCatalogueDB();
        cartList = new ArrayList<>();
    }

    public ArrayList<Item> getCatalogue(){
        //KEEP IN MIND
        // The real DB/API won't return a
        // list of Items, but some raw data that this method
        // will have to transform into an ArrayList of Items, THEN return
        return catalogueDB.getCatalogue();kkk
    }

    public Item getItemByID(String id){
        //KEEP IN MIND
        //The real DB/API won't return a
        // list of Items, but some raw data that this method
        // will have to transform into an Item, THEN return
        return catalogueDB.getItemByID(id);
    }

    public CartItem addToCart(String id, int quantity){
        //checks if the item is already in the cart
        if (cartItemExists(id))
            return null;

        //if not in the cart, we get the item to be added from the DB/API
        //KEEP IN MIND that the real DB/API won't return an Item, as I
        // said above (see getItemByID method)
        Item item = catalogueDB.getItemByID(id);

        //we create a new cart item using the item's description and cost.
        // With a real DB/API we might not need to create an actual Item
        // instance to do this, but for the purposes of this example, I'm
        // creating one
        CartItem ci = new CartItem(id, item.getDescription(), quantity, item.cost);
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
}
