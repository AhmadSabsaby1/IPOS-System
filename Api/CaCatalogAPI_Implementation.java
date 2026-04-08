package Api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.MarshalledObject;
import java.sql.ResultSet;
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
        ArrayList<Item> list = new ArrayList<>();
        try {
            ResultSet rs = InternalDB.getStock();


            while(rs.next()){
                Item item = new Item(
                        rs.getString("itemId"),
                        rs.getString("description"),
                        rs.getString("packageType"),
                        rs.getString("unit"),
                        rs.getInt("unitsInAPack"),
                        rs.getDouble("packageCost"),
                        rs.getInt("availability"),
                        rs.getInt("stockLimit")
                );
                list.add(item);
            }
        } catch (SQLException e) {
            System.err.println("DB Error: " + e.getMessage());
        }

        String[] catalogue = new String[list.size()];

        StringBuilder sb = new StringBuilder("[");

/// reads each item row one by one in the DB useing the getters in item.java then adding it to the JSON string
        for (int i = 0; i < list.size(); i++) {
            Item currentItem = list.get(i);

            String[] row = currentItem.catalogueRowData();

            catalogue[i] = String.join(", ", row);

            sb.append("{").append("\"itemId\":\"").append(currentItem.getId()).append("\",")
                    .append("\"description\":\"").append(currentItem.getDescription()).append("\",")
                    .append("\"packageType\":\"").append(currentItem.getType()).append("\",")
                    .append("\"unit\":\"").append(currentItem.getUnit()).append("\",")
                    .append("\"unitsInAPack\":\"").append(currentItem.getPack()).append("\",")
                    .append("\"packageCost\":\"").append(currentItem.getCost()).append("\",")
                    .append("\"availability\":\"").append(currentItem.getAvailability()).append("\",")
                    .append("\"stockLimit\":\"").append(currentItem.getLimit()).append("}");

            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
                try {
                    /** we would replace the URL with the actual one from team ISA once we get it
                    *might need to check with them if they use different format
                     */

                    HttpClient client = HttpClient.newHttpClient( );
                    ///THIS WORKS!!
                    HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://webhook.site/5cf3ace0-37e4-415b-aeda-b502c7c0edd0")).header("content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(sb.toString())).build();
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
