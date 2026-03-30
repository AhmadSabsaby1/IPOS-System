package cust.model;

import java.util.ArrayList;
import java.util.Stack;

public class CUSTModel {
    //TEMP
    private ArrayList<AccountHolder> accountHolders;
    private ArrayList<LocalItem> catalogueDB;
    private ArrayList<Order> ordersDB;

    private ArrayList<OrderItem> cartList;
    private Stack<String> orderIds;
    private Stack<String> orderDates;

    public CUSTModel(){
        MOCK_createAccountHolders();
        MOCK_createCatalogue();
        MOCK_createStacks();

        ordersDB = new ArrayList();
        cartList = new ArrayList();
    }

    /// /////////////////// MOCK ///////////////
    public void MOCK_createAccountHolders(){
        accountHolders = new ArrayList<>();
        accountHolders.add(new AccountHolder(
                "AH001",
                "Cesar E.",
                "Croydon",
                "Shiny",
                "1234",
                "4321",
                "01/27",
                180,
                "discountType?",
                17,
                "status?",
                "no_need",
                "no_need"
        ));

        accountHolders.add(new AccountHolder(
                "AH002",
                "Arthur C.",
                "Camelot",
                "Old",
                "5678",
                "8765",
                "07/28",
                500,
                "discountType?",
                5,
                "status?",
                "no_need",
                "no_need"
        ));
    }
    private void MOCK_createCatalogue(){
        catalogueDB = new ArrayList<LocalItem>();
        catalogueDB.add(new LocalItem("10000001", "Paracetamol", "box", "caps", 20, 0.10, 10345, 300));
        catalogueDB.add(new LocalItem("10000002", "Aspirin", "box", "caps", 20, 0.50, 12453, 500));
        catalogueDB.add(new LocalItem("10000003", "Analgin", "box", "caps", 10, 1.20, 4235, 200));
        catalogueDB.add(new LocalItem("10000004", "Celebrex, caps 100 mg", "box", "caps", 10, 10.00, 3420, 200));
        catalogueDB.add(new LocalItem("10000005", "Celebrex, caps 200 mg", "box", "caps", 10, 18.50, 1450, 150));
        catalogueDB.add(new LocalItem("10000006", "Retin-A Tretin, 30 g", "box", "caps", 20, 25.00, 2013, 200));
        catalogueDB.add(new LocalItem("10000007", "Lipitor TB, 20 mg", "box", "caps", 30, 15.50, 1562, 200));
        catalogueDB.add(new LocalItem("10000008", "Claritin CR, 60g", "box", "caps", 20, 19.50, 2540, 200));

        catalogueDB.add(new LocalItem("20000004", "Iodine tincture", "bottle", "ml", 100, 0.30, 22134, 200));
        catalogueDB.add(new LocalItem("20000005", "Rhynol", "bottle", "ml", 200, 2.50, 1908, 300));

        catalogueDB.add(new LocalItem("30000001", "Ospen", "box", "caps", 20, 10.50, 809, 200));
        catalogueDB.add(new LocalItem("30000002", "Amopen", "box", "caps", 30, 15.00, 1340, 300));

        catalogueDB.add(new LocalItem("40000001", "Vitamin C", "box", "caps", 30, 1.20, 3258, 300));
        catalogueDB.add(new LocalItem("40000002", "Vitamin B12", "box", "caps", 30, 1.30, 2673, 300));

    }

    public void MOCK_createStacks(){
        //MOCK IDS
        orderIds = new Stack<>();
        orderIds.push("IP3021");
        orderIds.push("IP2780");
        orderIds.push("IP2034");

        orderDates = new Stack<>();
        orderDates.push("29-01-2003");
        orderDates.push("17-01-2003");
        orderDates.push("12-01-2003");
    }
    /// /////////////////////////////////////////

    public ArrayList<AccountHolder> getAccountHolders() {
        return accountHolders;
    }

    public AccountHolder getAccountById(String id) {
        for (AccountHolder a : accountHolders) {
            if (a.getAccountId().equals(id)) {
                return a;
            }
        }

        return null;
    }

    public void modifyAccountHolderField(String id, String field, String value) {
        for (AccountHolder a : accountHolders) {
            if (a.getAccountId().equals(id)) {
                a.modifyField(field, value);
            }
        }
    }

    public void createAccount(AccountHolder account) {
        accountHolders.add(account);
    }

    public void deleteAccount(String id) {
        for (AccountHolder a : accountHolders) {
            if (a.getAccountId().equals(id)) {
                accountHolders.remove(a);
                break;
            }
        }
    }

    /// ///////////////// ORDERS //////////////////////
    public void createOrder(String accountHolderId) {
        ordersDB.add(new Order(accountHolderId, orderIds.pop(), orderDates.pop(), cartList));
        cartList = new ArrayList<>();
    }

    public ArrayList<Order> getOrdersByAccount(String accountHolderId) {
        ArrayList<Order> orders = new ArrayList<>();
        for (Order o : ordersDB) {
            if (o.getAccountHolderID().equals(accountHolderId))
                orders.add(o);
        }

        return orders;
    }
    /// ////////////////////////////////////////////

    /// //////// CATALOGUE //////////////////
    public ArrayList<LocalItem> getCatalogue(){
        return catalogueDB;
    }

    public LocalItem getItemByID(String id) {
        for (LocalItem item : catalogueDB) {
            if (item.getId().equals(id)){
                return item;
            }
        }

        return null;
    }

    public OrderItem addToCart(String id, int quantity){
        //checks if the item is already in the cart
        if (OrderItemExists(id))
            return null;

        OrderItem ci = new OrderItem(id, getItemByID(id).getDescription(), quantity, getItemByID(id).getCost());
        cartList.add(ci);

        //we return the newly created item. This might be useful in the
        // future, but for now it's not actually used
        return ci;
    }

    public ArrayList<OrderItem> getCartList(){
        return cartList;
    }

    public boolean OrderItemExists(String id){
        for(OrderItem item : cartList){
            if(item.getItemId().equals(id)){
                return true;
            }
        }

        return false;
    }

    public void removeAllOrderItems() {
        cartList.clear();
    }

    public double calculateGrandTotal() {
        double total = 0;
        for (OrderItem item : cartList) {
            total += item.getTotal();
        }
        return total;
    }

    public void removeFromCart(String itemId) {
        for(OrderItem item : cartList){
            if (item.getItemId().equals(itemId)){
                cartList.remove(item);
                return;
            }
        }
    }

    public void changeOrderItemQuantity(String id, int quantity) {
        for (OrderItem item : cartList) {
            if (item.getItemId().equals(id)) {
                item.setQuantity(quantity);
            }
        }
    }

    public void changeCartItemQuantity(String id, int quantity) {
        for (OrderItem item : cartList) {
            if (item.getItemId().equals(id)) {
                item.setQuantity(quantity);
            }
        }
    }

    public boolean isCartEmpty() {
        return cartList.isEmpty();
    }

    public ArrayList<LocalItem> searchByField(String field, String searchText) {
        searchText = searchText.toLowerCase();
        ArrayList<LocalItem> found = new ArrayList<>();
        for (LocalItem item : catalogueDB) {
            if (field.equals(LocalItem.ITEM_ID)) {
                if (item.getId().toLowerCase().contains(searchText))
                    found.add(item);
            }else if (field.equals(LocalItem.DESCRIPTION)) {
                if (item.getDescription().toLowerCase().contains(searchText))
                    found.add(item);
            }
        }

        return found;
    }

    public void removeAllCartItems() {
        cartList.clear();
    }

    public boolean cartItemExists(String id){
        for(OrderItem item : cartList){
            if(item.getItemId().equals(id)){
                return true;
            }
        }

        return false;
    }
}
