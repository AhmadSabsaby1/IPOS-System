package cust.controller;

import cust.model.*;
import cust.view.*;

import java.util.ArrayList;

public class CUSTController {
    private CUSTModel model;

    private CUSTMainView mainView;
    private AccountManagerView accountManagerView;
    private HubView hubView;
    private OrderManagerView ordersView;
    private ModifyAccountView modifyAccountView;
    private CreateAccountView createAccountView;

    private OrderManagerView orderManagerView;
    private SeeOrdersView seeOrdersView;
    private CreateOrderView createOrderView;
    private OrderCart orderCart;


    public CUSTController(){
        mainView = new CUSTMainView();
        accountManagerView = new AccountManagerView(this);
        hubView = new HubView(this);
        ordersView = new OrderManagerView(this);
        modifyAccountView = new ModifyAccountView(this);
        createAccountView = new CreateAccountView(this);
        orderManagerView = new OrderManagerView(this);
        seeOrdersView = new SeeOrdersView(this);
        createOrderView = new CreateOrderView(this);
        orderCart = new OrderCart(this);



        //add the views' cards to the main view
        mainView.addCardLayout(hubView, HubView.cardId());
        mainView.addCardLayout(ordersView, OrderManagerView.cardId());
        mainView.addCardLayout(accountManagerView, AccountManagerView.cardId());
        mainView.addCardLayout(modifyAccountView, ModifyAccountView.cardId());
        mainView.addCardLayout(createAccountView, CreateAccountView.cardId());
        mainView.addCardLayout(orderManagerView, OrderManagerView.cardId());
        mainView.addCardLayout(seeOrdersView, SeeOrdersView.cardId());
        mainView.addCardLayout(createOrderView, CreateOrderView.cardId());
        mainView.addCardLayout(orderCart, OrderCart.cardId());


        model = new CUSTModel();
    }

    /// /////////// SCREEN CHANGES //////////////////
    public void goToHubScreen(){
        mainView.changeCardView(HubView.cardId());
    }

    public void goToAccountHolderManagerScreen(){
        accountManagerView.populateAccountTable(getAccountHolders());
        mainView.changeCardView(AccountManagerView.cardId());
    }
    

    public void goToModifyAccountHolderScreen(String id){
        modifyAccountView.fillAccountData(model.getAccountById(id));
        mainView.changeCardView(ModifyAccountView.cardId());
    }

    public void goToCreateAccountScreen() {
        mainView.changeCardView(CreateAccountView.cardId());
    }

    public void goToOrderManagerScreen(){
        mainView.changeCardView(OrderManagerView.cardId());
    }


    public void goToSeeOrdersScreen(){
        seeOrdersView.populateAccounts();
        mainView.changeCardView(SeeOrdersView.cardId());
    }

    public void goToCreateOrderScreen() {
        createOrderView.populateCatalogue(model.getCatalogue());
        createOrderView.populateAccounts(model.getAccountHolders());
        mainView.changeCardView(CreateOrderView.cardId());
    }

    public void goToCartScreen(String accountId) {
        orderCart.populateTable(model.getCartList());
        orderCart.fillAccountDetails(model.getAccountById(accountId));
        mainView.changeCardView(OrderCart.cardId());
    }
    ////////////////////////////////////////////////

    public void createAccount(AccountHolder account){
        model.createAccount(account);
    }

    public void modifyAccountField(String id, String field, String value) {
        model.modifyAccountHolderField(id, field, value);
    }

    public void deleteAccount(String id) {
        model.deleteAccount(id);
    }

    public ArrayList<Order> getOrdersByAccount(String selectedRowColumn) {
        return model.getOrdersByAccount(selectedRowColumn);
    }

    public ArrayList<AccountHolder> getAccountHolders(){
        return model.getAccountHolders();
    }

    /// //////////// CATALOGUE /////////////
    public LocalItem getItemByID(String id){
        return model.getItemByID(id);
    }

    public ArrayList<LocalItem> getCatalogue(){
        return model.getCatalogue();
    }

    public OrderItem addToCart(String id, int quantity){
        return model.addToCart(id, quantity);
    }

    public boolean cartItemExists(String id){
        return model.cartItemExists(id);
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

    public ArrayList<OrderItem> getCartList() {
        return model.getCartList();
    }

    public void changeCartItemQuantity(String id, int quantity) {
        model.changeCartItemQuantity(id, quantity);
    }

    public boolean isCartEmpty() {
        return model.isCartEmpty();
    }

    public ArrayList<LocalItem> searchByField(String field, String searchText) {
        return model.searchByField(field, searchText);
    }

    public void createOrder(String accountHolderId){
        model.createOrder(accountHolderId);
    }



}

