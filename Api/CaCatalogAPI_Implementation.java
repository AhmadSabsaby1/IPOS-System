package Api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.MarshalledObject;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ArrayList;
import database.DBLocalStock;
import ord.model.Item;


public class CaCatalogAPI_Implementation implements ICACatalogAPI {

    private DBLocalStock InternalDB = new DBLocalStock();

    public CaCatalogAPI_Implementation() throws SQLException, ClassNotFoundException {
        super();
    }

    @Override
    public String[] getCatalogue() {

        ArrayList<Item> list = null;
        try {
            list = (ArrayList<Item>) InternalDB.getStock();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String[] catalogue = new String[list.size()];

        StringBuilder sb = new StringBuilder("[");

/// reads each item row one by one in the DB useing the getters in item.java then adding it to the JSON string
        for (int i = 0; i < list.size(); i++) {
            Item currentItem = list.get(i);

            String[] row = currentItem.catalogueRowData();

            catalogue[i] = String.join(", ", row);

            sb.append("{").append("\"id\":\"").append(currentItem.getId()).append("\",")
                    .append("\"description\":\"").append(currentItem.getDescription()).append("\",")
                    .append("\"package Type\":\"").append(currentItem.getType()).append("\",")
                    .append("\"Unit\":\"").append(currentItem.getUnit()).append("\",")
                    .append("\"Units in a Pack\":\"").append(currentItem.getPack()).append("\",")
                    .append("\"Package Cost\":\"").append(currentItem.getCost()).append("\",")
                    .append("\"Availability\":\"").append(currentItem.getAvailability()).append("\",")
                    .append("\"packs\":\"").append(currentItem.getLimit()).append("}");

            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
                try {
                    /** we would replace the URL with the actual one from team ISA once we get it
                    *might need to check with them if they use different format
                     */

                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.ISA.com/get catalogue")).header("content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(sb.toString())).build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                } catch (Exception e) {
                    System.out.println("couldnt reach team ISA Catalogue API: " + e.getMessage());
                }

        return catalogue;

	}

    /**
     *
     * @param products
     * @param OrderID
     * @param shippingAddress
     */
    @Override
    public boolean sendOrderDetails(HashMap<Integer, Integer> products, int OrderID, String shippingAddress) {
        /// inc

        System.out.println("Order ID: " + OrderID);
        System.out.println("Shipping Address: " + shippingAddress);
        System.out.println("Number of products: " + products.size());
        System.out.println("Products:");
        for (Integer itemID : products.keySet()){
            int quantity = products.get(itemID);
            System.out.println("Item ID: " + itemID + ", Quantity: " + quantity);
        }

        return true;
    }


}
