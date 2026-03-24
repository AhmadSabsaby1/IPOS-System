package ord.mock;

import ord.model.CartItem;
import ord.model.Item;
import ord.model.Order;

import java.util.ArrayList;
import java.util.Stack;

/**
 * <strong>THIS IS A MOCK CLASS AND IT IS NOT REPRESENTATIVE OF ANY ACTUAL DB OR API CLASS</strong>
 * <p>Most of the returned values will be a <code>ResultSet</code>,a <code>JSon</code> object or
 * something like that. It <strong>WON'T</strong> be an <code>Item</code>.
 */
public class MOCKISAOrderAPI {
    private ArrayList<Item> catalogueDB;
    private ArrayList<MOCKUser> userDB;
    private ArrayList<Order> orderDB;

    private Stack<String> orderIds;
    private Stack<String> orderDates;

    public MOCKISAOrderAPI() {
        catalogueDB = new ArrayList<>();
        catalogueDB.add(new Item("10000001", "Paracetamol", "box", "caps", 20, 0.10, 10345, 300));
        catalogueDB.add(new Item("10000002", "Aspirin", "box", "caps", 20, 0.50, 12453, 500));
        catalogueDB.add(new Item("10000003", "Analgin", "box", "caps", 10, 1.20, 4235, 200));
        catalogueDB.add(new Item("10000004", "Celebrex, caps 100 mg", "box", "caps", 10, 10.00, 3420, 200));
        catalogueDB.add(new Item("10000005", "Celebrex, caps 200 mg", "box", "caps", 10, 18.50, 1450, 150));
        catalogueDB.add(new Item("10000006", "Retin-A Tretin, 30 g", "box", "caps", 20, 25.00, 2013, 200));
        catalogueDB.add(new Item("10000007", "Lipitor TB, 20 mg", "box", "caps", 30, 15.50, 1562, 200));
        catalogueDB.add(new Item("10000008", "Claritin CR, 60g", "box", "caps", 20, 19.50, 2540, 200));

        catalogueDB.add(new Item("20000004", "Iodine tincture", "bottle", "ml", 100, 0.30, 22134, 200));
        catalogueDB.add(new Item("20000005", "Rhynol", "bottle", "ml", 200, 2.50, 1908, 300));

        catalogueDB.add(new Item("30000001", "Ospen", "box", "caps", 20, 10.50, 809, 200));
        catalogueDB.add(new Item("30000002", "Amopen", "box", "caps", 30, 15.00, 1340, 300));

        catalogueDB.add(new Item("40000001", "Vitamin C", "box", "caps", 30, 1.20, 3258, 300));
        catalogueDB.add(new Item("40000002", "Vitamin B12", "box", "caps", 30, 1.30, 2673, 300));

        userDB = new ArrayList<>();
        userDB.add(new MOCKUser("mu001", "realMe", "forReal"));
        userDB.add(new MOCKUser("mu002", "john_snow", "knowsNothing"));
        userDB.add(new MOCKUser("mu003", "KillBill69", "mynameisbuck"));

        orderDB = new ArrayList<>();

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

    public ArrayList<Item> getCatalogue() {
        return catalogueDB;
    }

    public void createOrder(String merchantId, ArrayList<CartItem> cartItems) {
        orderDB.add(new Order(merchantId, orderIds.pop(), orderDates.pop(), cartItems));
    }

    public ArrayList<Order> getOrders(String merchantId) {
        ArrayList<Order> previousOrdersByMerchant = new ArrayList<>();
        for (Order order : orderDB) {
            if (order.getMerchantID().equals(merchantId))
                previousOrdersByMerchant.add(order);
        }

        return previousOrdersByMerchant;
    }
}