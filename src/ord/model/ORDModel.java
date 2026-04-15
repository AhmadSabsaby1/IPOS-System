package ord.model;

import Api.ISALogin_Implementation;
import Api.ISAOrder_Implementation;
import Api.SessionManager;
import custom.JsonObject;
import ord.offlineData.OfflineOrderAPI;
import ord.offlineData.OfflineUserDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ORDModel {
    private ISAOrder_Implementation ISAOrderAPI;
    private ISALogin_Implementation ISALoginAPI;
    
    private ArrayList<Item> catalogueSA;
    private OfflineOrderAPI offlineOrderAPI;
    private OfflineUserDB offlineUserDB;

    //the cart list of items
    private ArrayList<CartItem> cartList;

    public ORDModel() {
        cartList = new ArrayList<>();

        ISAOrderAPI = new ISAOrder_Implementation();
        ISALoginAPI = new ISALogin_Implementation();
        populateCatalogue();

        offlineOrderAPI = new OfflineOrderAPI();
        offlineUserDB = new OfflineUserDB();
    }

    /// /////////////// PRIVATE //////////////////

    private void populateCatalogueOffline(){
        catalogueSA = new ArrayList<>();
        catalogueSA.add(new Item("SA10000001", "Paracetamol", "box", "caps", 20, 0.10, 10345, 300));
        catalogueSA.add(new Item("SA10000002", "Aspirin", "box", "caps", 20, 0.50, 12453, 500));
        catalogueSA.add(new Item("SA10000003", "Analgin", "box", "caps", 10, 1.20, 4235, 200));
        catalogueSA.add(new Item("SA10000004", "Celebrex, caps 100 mg", "box", "caps", 10, 10.00, 3420, 200));
        catalogueSA.add(new Item("SA10000005", "Celebrex, caps 200 mg", "box", "caps", 10, 18.50, 1450, 150));
        catalogueSA.add(new Item("SA10000006", "Retin-A Tretin, 30 g", "box", "caps", 20, 25.00, 2013, 200));
        catalogueSA.add(new Item("SA10000007", "Lipitor TB, 20 mg", "box", "caps", 30, 15.50, 1562, 200));
        catalogueSA.add(new Item("SA10000008", "Claritin CR, 60g", "box", "caps", 20, 19.50, 2540, 200));

        catalogueSA.add(new Item("SA20000004", "Iodine tincture", "bottle", "ml", 100, 0.30, 22134, 200));
        catalogueSA.add(new Item("SA20000005", "Rhynol", "bottle", "ml", 200, 2.50, 1908, 300));

        catalogueSA.add(new Item("SA30000001", "Ospen", "box", "caps", 20, 10.50, 809, 200));
        catalogueSA.add(new Item("SA30000002", "Amopen", "box", "caps", 30, 15.00, 1340, 300));

        catalogueSA.add(new Item("SA40000001", "Vitamin C", "box", "caps", 30, 1.20, 3258, 300));
        catalogueSA.add(new Item("SA40000002", "Vitamin B12", "box", "caps", 30, 1.30, 2673, 300));
    }
    private void populateCatalogue() {
        String[] rawCat = ISAOrderAPI.getCatalogue();
        //right now they respond with a greeting... so let's see if this catches it
        if (rawCat == null || rawCat.length == 0 || !rawCat[0].contains("{")) {
            //something went wrong fetching the catalogue
            populateCatalogueOffline();
            return;
        }

        catalogueSA = new ArrayList<>();
        for (String json : rawCat) {
            JsonObject o = JsonObject.parse(json);
            //System.out.println("JsonString: " + o.toJsonString());
            catalogueSA.add(new Item(
                    o.get("id"),
                    o.get("description"),
                    o.get("package_type"),
                    o.get("unit"),
                    o.getInt("units_per_pack"),
                    o.getDouble("package_cost"),
                    o.getInt("stock_quantity"),
                    0
            ));
        }
    }
    /// ///////////////////////////////////////////

    public ArrayList<Item> getCatalogue(){
        return catalogueSA;
    }

    public ArrayList<OrderSA> getOrders(){
        //Viewpreviousorders
        ArrayList<OrderSA> orders = new ArrayList<>();
        String [] rawOrders = ISAOrderAPI.viewPreviousOrders(SessionManager.merchant_Id);

        //right now they respond with a greeting... so let's see if this catches it
        if (rawOrders == null || rawOrders.length == 0 || !rawOrders[0].contains(":")) {
            //something went wrong fetching the catalogue
            System.out.println("No API orders: " + Arrays.toString(rawOrders));
            return offlineOrderAPI.getOrders();
        }

        for (String json : rawOrders) {
            JsonObject o = JsonObject.parse(json);
            orders.add(new OrderSA(
                    o.get("merchant_id"),
                    o.get("id"),
                    o.get("order_date"),
                    o.getDouble("total"),
                    o.getDouble("discount_amount"),
                    o.getDouble("amount_due"),
                    o.get("status"),
                    orderedItemsFromJson(JsonObject.parseArray(o.get("items")))
            ));
        }

        return orders;
    }

    private ArrayList<CartItem> orderedItemsFromJson(String[] jsonArray){
        ArrayList<CartItem> items = new ArrayList<>();
        for (String json : jsonArray) {
            JsonObject o = JsonObject.parse(json);
            items.add(new CartItem(
                    o.get("product_id"),
                    o.get("product_name"),
                    o.getInt("quantity"),
                    o.getDouble(o.get("cost"))
            ));
        }
        return items;
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

    public void createOrder(){
        HashMap<String, Integer> orderDetails = new HashMap<>();

        for (CartItem i : cartList) {
            orderDetails.put(i.getItemId(), i.getQuantity());
        }

        if (!ISAOrderAPI.placeOrder(orderDetails)){
            offlineOrderAPI.createOrder(SessionManager.merchant_Id, calculateGrandTotal(), cartList);
        }

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

    public boolean merchantLogin(String username, String password) {
        if (!ISALoginAPI.merchantLogin(username, password)){
            //offline login check
            return offlineUserDB.checkCredentials(username, password);
        }

        return true;
    }

    public String queryBalance() {
        String s = ISAOrderAPI.queryBalance(SessionManager.merchant_Id);
        s = s.replace("\n", "").replace(" ", "");
        return s;
    }
}
