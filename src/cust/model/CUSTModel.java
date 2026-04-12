package cust.model;

import database.DBAccountHolders;
import database.DBLocalStock;
import database.DBTransactions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Stack;

public class CUSTModel {
    //Databases
    private DBLocalStock localStockDB;
    private DBAccountHolders accountHoldersDB;
    private DBTransactions transactionsDB;

    //TEMP
    private ArrayList<AccountHolder> accountHolders;
    private ArrayList<LocalItem> catalogueDB;
    private ArrayList<Order> ordersDB;

    private ArrayList<OrderItem> cartList;
    private Stack<String> orderIds;
    private Stack<String> orderDates;

    public CUSTModel(){
        //MOCK_createAccountHolders();
        //MOCK_createCatalogue();
        MOCK_createStacks();

        ordersDB = new ArrayList();
        cartList = new ArrayList();

        try{
            accountHoldersDB = new DBAccountHolders();
            localStockDB = new DBLocalStock();
            transactionsDB = new DBTransactions();
            //DEBUG_InsertAccountHolders();
            //DEBUG_InsertLocalStock();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    /// /////////////////// MOCK AND DEBUGS ///////////////

    private void DEBUG_InsertLocalStock(){
        try{
            System.out.println("Inserting local stock");

            localStockDB.newProduct(
                    "Paracetamol",
                    "Box",
                    "Caps",
                    20,
                    0.1,
                    121,
                    10,
                    0
            );

            localStockDB.newProduct(
                    "Aspirin",
                    "Box",
                    "Caps",
                    20,
                    0.5,
                    201,
                    15,
                    0
            );

            localStockDB.newProduct(
                    "Analgin",
                    "Box",
                    "Caps",
                    10,
                    1.2,
                    25,
                    10,
                    0
            );

            localStockDB.newProduct(
                    "Celebrex, caps 100 mg",
                    "Box",
                    "Caps",
                    10,
                    10,
                    43,
                    10,
                    0
            );

            localStockDB.newProduct(
                    "Celebrex, caps 200 mg",
                    "Box",
                    "Caps",
                    10,
                    18.5,
                    35,
                    5,
                    0
            );

            localStockDB.newProduct(
                    "Retin-A Tretin, 30 g",
                    "Box",
                    "Caps",
                    20,
                    25,
                    28,
                    10,
                    0
            );

            localStockDB.newProduct(
                    "Lipitor TB, 20 mg",
                    "Box",
                    "Caps",
                    30,
                    15.5,
                    10,
                    10,
                    0
            );

            localStockDB.newProduct(
                    "Claritin CR, 60 g",
                    "Box",
                    "Caps",
                    20,
                    19.5,
                    21,
                    10,
                    0
            );

            localStockDB.newProduct(
                    "Iodine tincture",
                    "Bottle",
                    "M1",
                    100,
                    0.3,
                    35,
                    10,
                    0
            );

            localStockDB.newProduct(
                    "Rhynol",
                    "Bottle",
                    "M1",
                    200,
                    2.5,
                    14,
                    15,
                    0
            );

            localStockDB.newProduct(
                    "Ospen",
                    "Box",
                    "Caps",
                    20,
                    10.5,
                    78,
                    10,
                    0
            );

            localStockDB.newProduct(
                    "Amopen",
                    "Box",
                    "Caps",
                    30,
                    15,
                    90,
                    15,
                    0
            );

            localStockDB.newProduct(
                    "Vitamin C",
                    "Box",
                    "Caps",
                    30,
                    1.2,
                    22,
                    15,
                    0
            );

            localStockDB.newProduct(
                    "Vitamin B12",
                    "Box",
                    "Caps",
                    30,
                    1.3,
                    43,
                    15,
                    0
            );

            System.out.println("Local stock created");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private void DEBUG_InsertAccountHolders(){
        try{
            System.out.println("CREATING ACCOUNTS");
            accountHoldersDB.createAccount(
                    "Ms Eva Bauyer",
                    "1, Liverpool street, London EC2V 8NS",
                    500,
                    500,
                    AccountHolder.DiscountType.FIXED.toString(),
                    3,
                    0,
                    0,
                    0,
                    0,
                    0,
                    AccountHolder.AccountStatus.NORMAL.toString(),
                    AccountHolder.ReminderStatus.NO_NEED.toString(),
                    AccountHolder.ReminderStatus.NO_NEED.toString(),
                    "0207 321 8001",
                    "evabauyer@gmail.com"
            );

            accountHoldersDB.createAccount(
                    "Ms Glynne Morrison",
                    "1, Liverpool street, London EC2V 8NS",
                    500,
                    500,
                    AccountHolder.DiscountType.FLEXIBLE.toString(),
                    0,
                    0,
                    100,
                    1,
                    300,
                    2,
                    AccountHolder.AccountStatus.NORMAL.toString(),
                    AccountHolder.ReminderStatus.NO_NEED.toString(),
                    AccountHolder.ReminderStatus.NO_NEED.toString(),
                    "0207 321 8001",
                    "morrisonglynne@gmail.com"
            );
            System.out.println("ACCOUNTS CREATED");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public void MOCK_createAccountHolders(){
//        accountHolders = new ArrayList<>();
//        accountHolders.add(new AccountHolder(
//                "AH001",
//                "Cesar E.",
//                "Croydon",
//                500,
//                AccountHolder.DiscountType.FIXED,
//                3.0
//        ));
//
//        accountHolders.add(new AccountHolder(
//                "AH002",
//                "Arthur C.",
//                "Camelot",
//                500,
//                AccountHolder.DiscountType.FLEXIBLE,
//                1000,
//                2000,
//                0.0,
//                1.0,
//                3.0
//        ));
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
        ArrayList<AccountHolder> list = new ArrayList<>();
        try{
            ResultSet acc = accountHoldersDB.getAccounts();
            while(acc.next()){
                list.add(new AccountHolder(acc));
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public void computeAutomaticStatusChange(){
        ArrayList<AccountHolder> accountHolders = getAccountHolders();
        for (AccountHolder a : accountHolders){
            AutomaticStatusChange.computeStatus(a, this, true);
        }
    }

    public AccountHolder getAccountById(String id) {
        AccountHolder accountHolder = null;
        try{
            accountHolder = new AccountHolder(accountHoldersDB.getCustomerInfo(id));
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        return accountHolder;
    }

    public Order getOrderById(String orderId) {
        Order order = null;
        try{
            order = new Order(transactionsDB.getOrderInfo(orderId));
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return order;
    }

    public void modifyAccountHolderField(String id, String field, String value) throws SQLException {
        if (field.equals(AccountHolder.NAME))
            accountHoldersDB.setName(id, value);
        else if (field.equals(AccountHolder.ADDRESS))
            accountHoldersDB.setAddress(id, value);
        else if (field.equals(AccountHolder.PHONE))
            accountHoldersDB.setPhoneNumber(id, value);
        else if (field.equals(AccountHolder.EMAIL))
            accountHoldersDB.setEmail(id, value);
        else if (field.equals(AccountHolder.BALANCE))
            accountHoldersDB.setBalance(id, Double.parseDouble(value));
        else if (field.equals(AccountHolder.BALANCE_LIMIT))
            accountHoldersDB.setBalanceLimit(id, Integer.parseInt(value));
        else if (field.equals(AccountHolder.DISCOUNT_TYPE))
            accountHoldersDB.setDiscountType(id, value);
        else if (field.equals(AccountHolder.FIXED_DISCOUNT))
            accountHoldersDB.setFixedDiscount(id, Double.parseDouble(value));
        else if (field.equals(AccountHolder.TIER_1_THRESHOLD))
            accountHoldersDB.setTier1Threshold(id, Integer.parseInt(value));
        else if (field.equals(AccountHolder.TIER_2_THRESHOLD))
            accountHoldersDB.setTier2Threshold(id, Integer.parseInt(value));
        else if (field.equals(AccountHolder.TIER_1_DISCOUNT))
            accountHoldersDB.setTier1Discount(id, Double.parseDouble(value));
        else if (field.equals(AccountHolder.TIER_2_DISCOUNT))
            accountHoldersDB.setTier2Discount(id, Double.parseDouble(value));
        else if (field.equals(AccountHolder.TIER_3_DISCOUNT))
            accountHoldersDB.setTier3Discount(id, Double.parseDouble(value));
        else if (field.equals(AccountHolder.STATUS))
            accountHoldersDB.setStatus(id, value);
        else if (field.equals(AccountHolder.STATUS_1ST))
            accountHoldersDB.setStatus1stReminder(id, value);
        else if (field.equals(AccountHolder.STATUS_2ND))
            accountHoldersDB.setStatus2ndReminder(id, value);
    }

    public void createAccount(AccountHolder account) throws SQLException {
        accountHoldersDB.createAccount(
                account.getName(),
                account.getAddress(),
                account.getBalance(),
                account.getBalanceLimit(),
                account.getDiscountType(),
                account.getFixedDiscount(),
                account.getTier1Discount(),
                account.getTier1Threshold(),
                account.getTier2Discount(),
                account.getTier2Threshold(),
                account.getTier3Discount(),
                account.getStatus().toString(),
                account.getStatus1stReminder().toString(),
                account.getStatus2ndReminder().toString(),
                account.getPhoneNumber(),
                account.getEmail()
        );
    }

    public void deleteAccount(String id) {
        try{
            accountHoldersDB.deleteAccount(id);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    /// ///////////////// ORDERS //////////////////////

    private void decreaseBalance(String accountId, double amount){
        double balance = 0;
        try{
            ResultSet rs = accountHoldersDB.getCustomerInfo(accountId);
            rs.next();
            balance = rs.getDouble("balance");
            accountHoldersDB.setBalance(accountId, balance - amount);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private boolean increaseBalance(String accountId, double payment){
        double balance = 0;
        int balanceLimit = 0;

        try{
            ResultSet rs = accountHoldersDB.getCustomerInfo(accountId);
            rs.next();
            balance = rs.getDouble("balance");
            balanceLimit = rs.getInt("balanceLimit");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        if ((payment + balance) > balanceLimit){
            return false;
        }else{
            try{
                accountHoldersDB.setBalance(accountId, balance + payment);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        }

        return true;
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
            double totalCost) {

        int firstFour = 0;
        int lastFour = 0;
        double amountReceived = 0;

        if (paymentType.equals(Order.PaymentType.NONE.toString())) {
            if (!increaseBalance(accountHolder.getAccountId(), totalCost))
                return false;
        }else{
            amountReceived = totalCost;
        }

        if (paymentType.equals(Order.PaymentType.CARD.toString())) {
            firstFour = Integer.parseInt(creditCard.substring(0, 4));
            lastFour = Integer.parseInt(creditCard.substring(creditCard.length() - 4));
        }

        try{
            String orderId = transactionsDB.newTransaction(
                    paymentType,
                    amountReceived,
                    cardType,
                    firstFour,
                    lastFour,
                    expiryDate,
                    shippingAddress,
                    orderDate,
                    totalCost
            );

            if (!accountHolder.isOccasional())
                transactionsDB.newAccountTransaction(orderId, accountHolder.getAccountId());

            for (OrderItem o : cartList){
                transactionsDB.addOrderItem(orderId, o.getItemId(), o.getQuantity());
                //TODO we do nothing if the item stock gets below 0
                ResultSet rs = localStockDB.getItemInfo(o.getItemId());
                rs.next();
                localStockDB.updateStock(o.getItemId(), rs.getInt("availability") - o.getQuantity());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        cartList = new ArrayList<>();

        return true;
    }

    public void finishPayment(
            AccountHolder accountHolder,
            String orderId,
            double amount,
            String cardType,
            String creditCard,
            String securityNumber,
            String expiryDate) {

        int firstFour = Integer.parseInt(creditCard.substring(0, 4));
        int lastFour = Integer.parseInt(creditCard.substring(creditCard.length() - 4));

        try{
            transactionsDB.modifyPaymentDetails(orderId, amount, cardType, firstFour, lastFour, expiryDate);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        decreaseBalance(accountHolder.getAccountId(), amount);

        cartList = new ArrayList<>();

        AutomaticStatusChange.computeStatus(accountHolder, this, false);
    }

    public ArrayList<Order> getOrdersByAccount(String accountHolderId) {
        ArrayList<Order> orderList = new ArrayList<>();

        String orderId;
        String payment;
        double amount;
        String cardType;
        int firstFour;
        int lastFour;
        String expiry;
        String shipping;
        String date;
        String itemId;
        ArrayList<OrderItem> itemList;
        int quantity;
        String description;
        double totalCost;

        try{
            ResultSet tbai = transactionsDB.getTransactionsByAccountID(accountHolderId);
            while(tbai.next()){
                orderId = tbai.getString("orderId");
                payment = tbai.getString("paymentType");
                amount = tbai.getDouble("amountReceived");
                cardType = tbai.getString("cardType");
                firstFour = tbai.getInt("firstFour");
                lastFour = tbai.getInt("lastFour");
                expiry = tbai.getString("expiryDate");
                shipping = tbai.getString("shippingAddress");
                date = tbai.getString("orderDate");
                totalCost = tbai.getDouble("totalCost");

                itemList = getOrderItems(orderId);
                orderList.add(new Order(
                        orderId,
                        payment,
                        amount,
                        shipping,
                        date,
                        totalCost,
                        itemList
                ));
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return orderList;
    }

    public ArrayList<OrderItem> getOrderItems(String orderId) {
        ArrayList<OrderItem> itemList = new ArrayList<>();
        String itemId;
        int quantity;
        double cost;
        String description;
        try{
            ResultSet orderItems = transactionsDB.getOrderInfo(orderId);
            while(orderItems.next()){
                itemId = orderItems.getString("itemID");
                quantity = orderItems.getInt("quantity");

                ResultSet itemData = localStockDB.getItemInfo(itemId);
                itemData.next();
                description = itemData.getString("description");
                cost = itemData.getDouble("packageCost");
                itemList.add(new OrderItem(itemId, description, quantity, cost));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return itemList;
    }
    /// ////////////////////////////////////////////

    /// //////// CATALOGUE //////////////////
    public ArrayList<LocalItem> getCatalogue(){
        ArrayList<LocalItem> list = new ArrayList<>();
        try{
            ResultSet acc = localStockDB.getStock();
            while(acc.next()){
                list.add(new LocalItem(acc));
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        return list;
    }

    public LocalItem getItemByID(String id) {
        LocalItem item = null;
        try{
            item = new LocalItem(localStockDB.getItemInfo(id));
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return item;
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
        ArrayList<LocalItem> itemList = new ArrayList<>();
        try{
            ResultSet rs = null;
            if (field.equals(LocalItem.ITEM_ID)) {
                rs = localStockDB.getItemByID(searchText);
            }else if (field.equals(LocalItem.DESCRIPTION)) {
                rs = localStockDB.getItemByName(searchText);
            }
            
            while (rs.next()) {
                itemList.add(new LocalItem(rs));
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        return itemList;
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

    public void setStatus(String accountId, String status) {
        try{
            accountHoldersDB.setStatus(accountId, status);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void set1stReminderStatus(String accountId, String status) {
        try{
            accountHoldersDB.setStatus1stReminder(accountId, status);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void set2ndReminderStatus(String accountId, String status) {
        try{
            accountHoldersDB.setStatus2ndReminder(accountId, status);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void sendReminder(boolean firstReminder, AccountHolder accountHolder) {
        //TODO send the actual reminders
        try{
            if (firstReminder) {
                accountHoldersDB.setStatus1stReminder(accountHolder.getAccountId(), AccountHolder.ReminderStatus.SENT.toString());
            }else{
                accountHoldersDB.setStatus2ndReminder(accountHolder.getAccountId(), AccountHolder.ReminderStatus.SENT.toString());
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();

        try{
            ResultSet rs = transactionsDB.getTransactions();
            while (rs.next()) {
                orders.add(new Order(rs).setItemsOrdered(getOrderItems(rs.getString("orderID"))));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return orders;
    }
}
