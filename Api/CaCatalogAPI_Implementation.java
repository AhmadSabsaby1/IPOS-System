package IPOS_CADetailedModel;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.MarshalledObject;
import java.util.HashMap;
import java.util.ArrayList;
import ord.mock.MOCKCatalogueDB;

public class CaCatalogAPI_Implementation implements ICACatalogAPI {

    private MOCKCatalogue InternalDB = new MOCKCatalogueDB();

    @Override
    public String[] getCatalogue() {

        ArrayList<Catalogue> list = InternalDB.getCatalogue();

        String[] catalogue = new String[list.size()];

        stringBuilder sb = new StringBuilder("[");

/// reads each item row one by one in the DB useing the getters in item.java then adding it to the JSON string
        for (int i = 0; i < list.size(); i++) {
            Catalogue item = list.get(i);

            String[] row = currentItem.catalogueRowData();

            catalogue[i] = String.join(", ", row)

            jsonBuilder.append("{").append("\"id\":\"").append(item.getID()).append("\",")
                    .append("\"description\":\"").append(item.getDescription()).append("\",")
                    .append("\"package Type\":\"").append(item.getType()).append("\",")
                    .append("\"Unit\":\"").append(item.getUnit()).append("\",")
                    .append("\"Units in a Pack\":\"").append(item.getPack()).append("\",")
                    .append("\"Package Cost\":\"").append(item.getCost()).append("\",")
                    .append("\"Availability\":\"").append(item.getAvailability()).append("\",")
                    .append("\"packs\":\"").append(item.getLimit()).append("}");

            if (i < items.size() - 1) {
                jsonBuilder.append(",");
                jsonBuilder.append("]")
                try {
                    /** we would replace the URL with the actual one from team ISA once we get it
                    *might need to check with them if they use different format
                     */

                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder().uri(URI.Create("https://api.ISA.com/get catalogue")).header("content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonBuilder.toString())).build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                } catch (Exception e) {
                    System.out.println("couldnt reach team ISA Catalogue API: " + e.getMessage());
                }

                return catalogue;

            }
        }






	}

    /**
     *
     * @param products
     * @param OrderID
     * @param shippingAddress
     */
    @Override
    public boolean sendOrderDetails(HashMap<Integer, Integer> products, int OrderID, String shippingAddress) {

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
