package ord.offlineData;

import Api.SessionManager;
import ord.model.CartItem;
import ord.model.OrderSA;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Stack;

/**
 * <strong>THIS IS A MOCK CLASS AND IT IS NOT REPRESENTATIVE OF ANY ACTUAL DB OR API CLASS</strong>
 * <p>Most of the returned values will be a <code>ResultSet</code>,a <code>JSon</code> object or
 * something like that. It <strong>WON'T</strong> be an <code>Item</code>.
 */
public class OfflineOrderAPI {
    private ArrayList<OrderSA> orderDB;

    private Stack<String> orderIds;

    public OfflineOrderAPI() {
        populateOrdersOffline();

        orderIds = new Stack<>();
        orderIds.push("IP3021");
        orderIds.push("IP2780");
        orderIds.push("IP2034");
        orderIds.push("IP2022");
        orderIds.push("IP2021");
        orderIds.push("IP2001");
        orderIds.push("IP2000");
        orderIds.push("IP0000");
        orderIds.push("IP1111");
    }

    public void populateOrdersOffline(){
        orderDB = new ArrayList<>();
    }

    public void createOrder(String merchantId, double total, ArrayList<CartItem> cartItems) {
        orderDB.add(new OrderSA(
                merchantId,
                orderIds.pop(),
                LocalDate.now().toString(),
                total,
                0,
                total,
                "accepted",
                cartItems
        ));
    }

    public ArrayList<OrderSA> getOrders() {
        ArrayList<OrderSA> previousOrdersByMerchant = new ArrayList<>();
        for (OrderSA order : orderDB) {
            //TODO merchant ID is relevant? remove?
            if (order.getMerchantID().equals(SessionManager.merchant_Id))
                previousOrdersByMerchant.add(order);
        }

        return previousOrdersByMerchant;
    }
}