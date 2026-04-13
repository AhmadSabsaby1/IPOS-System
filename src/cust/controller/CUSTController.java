package cust.controller;

import cust.model.*;
import cust.view.*;
import main.Global;

import java.sql.SQLException;
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
    private ManageAccountOrdersView manageAccountOrdersView;
    private SeeAllOrdersView seeAllOrdersView;
    private CreateOrderView createOrderView;
    private OrderCart orderCart;
    private MakePaymentView makePaymentView;

    public CUSTController(){
        mainView = new CUSTMainView();
        accountManagerView = new AccountManagerView(this);
        hubView = new HubView(this);
        ordersView = new OrderManagerView(this);
        modifyAccountView = new ModifyAccountView(this);
        createAccountView = new CreateAccountView(this);
        orderManagerView = new OrderManagerView(this);
        manageAccountOrdersView = new ManageAccountOrdersView(this);
        seeAllOrdersView = new SeeAllOrdersView(this);
        createOrderView = new CreateOrderView(this);
        orderCart = new OrderCart(this);
        makePaymentView = new MakePaymentView(this);

        //add the views' cards to the main view
        mainView.addCardLayout(hubView, HubView.cardId());
        mainView.addCardLayout(ordersView, OrderManagerView.cardId());
        mainView.addCardLayout(accountManagerView, AccountManagerView.cardId());
        mainView.addCardLayout(modifyAccountView, ModifyAccountView.cardId());
        mainView.addCardLayout(createAccountView, CreateAccountView.cardId());
        mainView.addCardLayout(orderManagerView, OrderManagerView.cardId());
        mainView.addCardLayout(manageAccountOrdersView, ManageAccountOrdersView.cardId());
        mainView.addCardLayout(seeAllOrdersView, SeeAllOrdersView.cardId());
        mainView.addCardLayout(createOrderView, CreateOrderView.cardId());
        mainView.addCardLayout(orderCart, OrderCart.cardId());
        mainView.addCardLayout(makePaymentView, MakePaymentView.cardId());

        model = new CUSTModel();

        //TODO put the automatic status change in another place?
        model.computeAutomaticStatusChange();
    }

    /// /////////// SCREEN CHANGES //////////////////

    public void goToMainMenu() {
        Global.get().goToMainMenu();
        mainView.dispose();
    }

    public void goToSeeAllOrdersScreen() {
        seeAllOrdersView.populateTable(model.getAllOrders());
        mainView.changeCardView(SeeAllOrdersView.cardId());
    }
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

    public void goToManageAccountOrdersScreen(){
        manageAccountOrdersView.populateAccounts();
        mainView.changeCardView(ManageAccountOrdersView.cardId());
    }

    public void goToCreateOrderScreen() {
        createOrderView.populateCatalogue(model.getCatalogue());
        createOrderView.populateAccounts(model.getAccountHolders());
        mainView.changeCardView(CreateOrderView.cardId());
    }

    public void goToCartScreen(String accountId) {
        if (accountId.equals(AccountHolder.OCCASIONAL_CUSTOMER_ID))
            orderCart.fillAccountDetails(AccountHolder.occasionalAccountData());
        else
            orderCart.fillAccountDetails(model.getAccountById(accountId));

        orderCart.populateTable(model.getCartList());
        mainView.changeCardView(OrderCart.cardId());
    }

    public void goToMakePaymentScreen(String accountId, Order order) {
        makePaymentView.fillOrderDetails(model.getAccountById(accountId), order);
        makePaymentView.populateTable(order.getItemsOrdered());
        mainView.changeCardView(MakePaymentView.cardId());
    }
    ////////////////////////////////////////////////

    public void createAccount(AccountHolder account){
        try{
            model.createAccount(account);
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void modifyAccountField(String id, String field, String value) {
        try{
            model.modifyAccountHolderField(id, field, value);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

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

    public boolean createOrder(
            AccountHolder accountHolder,
            String shippingAddress,
            String orderDate,
            String paymentType,
            String cardType,
            String creditCard,
            String securityNumber,
            String expiryDate,
            double totalCost
    ){
        return model.createOrder(
                accountHolder,
                shippingAddress,
                orderDate,
                paymentType,
                cardType,
                creditCard,
                securityNumber,
                expiryDate,
                totalCost);
    }

    public void finishPayment(
            AccountHolder accountHolder,
            String orderId,
            double amount,
            String cardType,
            String creditCard,
            String securityNumber,
            String expiryDate
    ){
        model.finishPayment(
                accountHolder,
                orderId,
                amount,
                cardType,
                creditCard,
                securityNumber,
                expiryDate);
    }

    public void sendReminder(boolean firstReminder, AccountHolder accountHolder) {
        model.sendReminder(firstReminder, accountHolder);
    }
}
