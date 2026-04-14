package ord.controller;

import main.Global;
import ord.model.CartItem;
import ord.model.Item;
import ord.model.ORDModel;
import ord.view.*;

import java.util.ArrayList;

public class ORDController {
    //the main view changes from one view to another, changing screens as needed
    private ORDMainView mainView;
    //the view for the catalogue screen
    private CatalogueView catalogueView;
    private HubView hubView;
    private PreviousOrdersView previousOrdersView;
    private OrderProgressView orderProgressView;
    private MerchantLoginView merchantLoginView;

    //the view for the cart screen
    private CartView cartView;
    //the model of this package
    private ORDModel model;
    private String merchantId;

    public ORDController() {
        catalogueView = new CatalogueView(this);
        cartView = new CartView(this);
        previousOrdersView = new PreviousOrdersView(this);
        orderProgressView = new OrderProgressView(this);
        hubView = new HubView(this);
        merchantLoginView = new MerchantLoginView(this);
        mainView = new ORDMainView();

        //adds the views to the main view, each with its own id.
        mainView.addCardLayout(hubView, HubView.cardId());
        mainView.addCardLayout(catalogueView, CatalogueView.cardId());
        mainView.addCardLayout(cartView, CartView.cardId());
        mainView.addCardLayout(previousOrdersView, PreviousOrdersView.cardId());
        mainView.addCardLayout(orderProgressView, OrderProgressView.cardId());
        mainView.addCardLayout(merchantLoginView, MerchantLoginView.cardId());

        //instantiates the model
        model = new ORDModel();

        //populates the catalogue table with the catalogue the model provides
        catalogueView.populateCatalogue(getCatalogue());
    }

    ////////////// SCREEN CHANGES ////////////

    public void goToMainMenu() {
        Global.get().goToMainMenu();
        mainView.dispose();
    }

    public void goToLogin() {
        mainView.changeCardView(MerchantLoginView.cardId());
    }

    /**
     * Orders the main view to change the view to the cart
     */
    public void goToCartScreen(){
        //first we populate the table of the cart, then we change the view
        cartView.populateTable(model.getCartList());
        mainView.changeCardView(CartView.cardId());
    }

    /**
     * Orders the main view to change the view to the catalogue
     */
    public void goToCatalogueScreen(){
        mainView.changeCardView(CatalogueView.cardId());
    }

    public void goToPreviousOrdersScreen(){
        previousOrdersView.populateOrdersTable(model.getOrders());
        mainView.changeCardView(PreviousOrdersView.cardId());
    }

    public void goToHubScreen(){
        hubView.checkLogin();
        mainView.changeCardView(HubView.cardId());
    }

    public void goToOrderProgressScreen() {
        orderProgressView.populateTable(model.getOrders());
        mainView.changeCardView(OrderProgressView.cardId());
    }

    ////////////////////////////////////////////////////////

    /**
     * Returns an <code>Item</code>, searching by its id.
     * @param id the internal id of the item to be searched for
     * @return the <code>Item</code> with the <code>id</code>
     */
    public Item getItemByID(String id){
        return model.getItemByID(id);
    }

    public ArrayList<Item> getCatalogue(){
        return model.getCatalogue();
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

    public void createOrder(){
        model.createOrder();
    }

    public void removeAllCartItems() {
        model.removeAllCartItems();
    }

    public double calculateGrandTotal() {
        return model.calculateGrandTotal();
    }

    public void removeFromCart(String itemId) {
        model.removeFromCart(itemId);
    }

    public ArrayList<CartItem> getCartList() {
        return model.getCartList();
    }

    public void changeCartItemQuantity(String id, int quantity) {
        model.changeCartItemQuantity(id, quantity);
    }

    public boolean isCartEmpty() {
        return model.isCartEmpty();
    }

    public ArrayList<Item> searchByField(String field, String searchText) {
        return model.searchByField(field, searchText);
    }

    public boolean merchantLogin(String username, String password) {
        return model.merchantLogin(username, password);
    }
}
