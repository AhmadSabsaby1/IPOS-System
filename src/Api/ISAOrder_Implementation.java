package Api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class ISAOrder_Implementation implements ISAOrderAPI {

    private static final String ISA_ORDER_API_URL = "https://webhook.site/e2225256-8db6-4234-ba8e-af3a51faa852";
    /// once we get the actual URL from the other teams we would replace them

    @Override
    public boolean placeOrder(int merchantID, HashMap<Integer, Integer> orderDetails) {

        StringBuilder sb= new StringBuilder();

        sb.append("{").append("\"merchantId\":\"").append(merchantID).append("\",");
        sb.append("\"orderDetails\":[");

        int i = 0;

        for (int productId : orderDetails.keySet()) {
            int quantity = orderDetails.get(productId);
            sb.append("{").append("\"productId\":\"").append(productId).append("\",")
                    .append("\"quantity\":\"").append(quantity).append("\"}");


            if (i < orderDetails.size() - 1) {
                sb.append(",");

            }
            i++;
        }
        sb.append("]");
        sb.append("}");

        String jsonRequestBody = sb.toString();

        try {

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ISA_ORDER_API_URL)).header("Content-Type", "placeOrder/json").POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody)).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Order placed successfully.");
                return true;
            } else {
                System.out.println("Order placement failed with status code: " + response.statusCode());
                return false;
            }

        } catch (Exception e) {

            System.out.println("couldnt reach team ISA Order: " + e.getMessage());
            return false;

        }

    }

    @Override
    public String trackOrderProgress(int orderID) {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ISA_ORDER_API_URL)).header("Content-Type", "trackOrderProgress/json").GET().build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Order progress retrieved successfully.");
                return response.body();
            } else {
                return "Unable to track order progress at the moment.";
            }

        } catch (Exception e) {
            return "Couldn't reach team ISA Order API.";
        }
    }

    @Override
    public int queryBalance(int merchantID) {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ISA_ORDER_API_URL)).header("Content-Type", "queryBalance/json").GET().build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Balance retrieved successfully.");
                return Integer.parseInt(response.body());
            } else {
                System.out.println("Failed to retrieve balance with status code: " + response.statusCode());
                return 0;
            }

        } catch (Exception e) {
            System.out.println("couldnt reach team ISA Order API: ");
            return 0;
        }

    }

    @Override
    public String[] viewPreviousOrders(int merchantID) {

        try {

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ISA_ORDER_API_URL)).header("Content-Type", "viewPreviousOrders/json").GET().build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Order retrieved successfully.");

            }
        } catch (Exception e) {
            System.out.println("couldnt reach team ISA Order API: " + e.getMessage());
            return new String[0];
        }
        return new String[0];
    }

    @Override
    public String[] getCatalogue() {
        try {

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ISA_ORDER_API_URL)).header("Content-Type", "getCatalogue/json").GET().build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Catalogue fetched sucessfully.");
                return new String[]{response.body().replace("[", "").replace("]", "")};

            } else {
                return new String[0];
            }
        } catch (Exception e) {
            System.out.println("couldnt reach team ISA Order API: " + e.getMessage());
            return new String[0];
        }
    }


    @Override
    public String[] viewInvoices(int merchantID) {
        try {

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ISA_ORDER_API_URL)).header("Content-Type", "viewInvoices/json").GET().build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Invoices fetched sucessfully.");
                return new String[]{response.body().replace("[", "").replace("]", "")};
            } else {
                return new String[0];
            }
        } catch (Exception e) {
            System.out.println("couldnt reach team ISA Order API: " + e.getMessage());
            return new String[0];
        }
    }
}


