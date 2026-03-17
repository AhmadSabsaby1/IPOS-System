package ord.controller;

import ord.model.CartItem;
import ord.model.Item;
import ord.model.ORDModel;
import ord.view.CartView;
import ord.view.CatalogueView;
import ord.view.ORDMainView;

public class ORDController {
    //the main view changes from one view to another, changing screens as needed
    ORDMainView mainView;
    //the view for the catalogue screen
    CatalogueView catalogueView;
    //the view for the cart screen
    CartView cartView;
    //the model of this package
    ORDModel model;
    public ORDController() {
        catalogueView = new CatalogueView(this);
        cartView = new CartView(this);
        mainView = new ORDMainView();

        //adds the two views to the main view, each with its own id.
        mainView.addCardLayout(catalogueView, CatalogueView.cardId());
        mainView.addCardLayout(cartView, CartView.cardId());

        //instantiates the model
        model = new ORDModel();

        //populates the catalogue table with the catalogue the model provides
        catalogueView.populateCatalogue(model.getCatalogue());
    }

    /**
     * Orders the main view to change the view to the cart
     */
    public void changeScreenCart(){
        //first we populate the table of the cart, then we change the view
        cartView.populateTable(model.getCartList());
        mainView.changeCardView(CartView.cardId());
    }

    /**
     * Orders the main view to change the view to the catalogue
     */
    public void changeScreenCatalogue(){
        mainView.changeCardView(CatalogueView.cardId());
    }

    /**
     * Returns an <code>Item</code>, searching by its id.
     * @param id the internal id of the item to be searched for
     * @return the <code>Item</code> with the <code>id</code>
     */
    public Item getItemByID(String id){
        return model.getItemByID(id);
    }

    /**
     * Adds an item to the cart.
     * @param id the internal id of the item to be added to the cart
     * @param quantity the number of items to be added to the cart
     * @return the <code>CartItem</code> that was added to the cart.
     * Returns <code>null</code> if the item was already in the cart
     */
    public CartItem addToCart(String id, int quantity){
        return model.addToCart(id, quantity);
    }

    /**
     * Checks whether an item already exists in the cart.
     * @param id the internal id of the object to search for
     * @return <code>true</code> if the item is in the cart, <code>false</code> if not
     */
    public boolean cartItemExists(String id){
        return model.cartItemExists(id);
    }
}
