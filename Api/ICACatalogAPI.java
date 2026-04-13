package Api;

import java.util.HashMap;

public interface ICACatalogAPI {


    String[] getCatalogue();

    /**
     *
     * @param products
     * @param OrderID
     * @param ShippingAddress
     */

    boolean sendOrderDetails(HashMap<Integer, Integer> products, int OrderID, String ShippingAddress);

}