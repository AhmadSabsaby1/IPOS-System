import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ISAOrder_Implementation implements ISAOrderAPI {

    private static final String ISA_ORDER_API_URL = "https://api.isaorder.com/orderapi";
    /// once we get the actual URL from the other teams we would replace them


    @Override
    public boolean placeOrder(int productID, int quantity) {

        String jsonRequestBody = String.format(
                "{\"productID\": %d, \"quantity\": \"%d\"}",
                productID, quantity
        );

        try {

            HttpRequest request = HttpRequest.newBuilder().build().uri(URI.create(ISA_ORDER_API_URL)).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody)).build();
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


        return false;
    }

    @Override
    public String trackOrderProgress(int orderID) {

        try {

            HttpRequest request = HttpRequest.newBuilder().build().uri(URI.create(ISA_ORDER_API_URL)).header("Content-Type", "application/json").GET().build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return "Order is being processed.";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int queryBalance(int merchantID) {

        try {

            HttpRequest request = HttpRequest.newBuilder().build().uri(URI.create(ISA_ORDER_API_URL)).header("Content-Type", "application/json").GET().build();
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
            System.out.println("couldnt reach team ISA Order API: " + e.getMessage());
            return 0;
        }

    }

    @Override
    public String[] viewPreviousOrders(int merchantID) {

        try {

            HttpRequest request = HttpRequest.newBuilder().build().uri(URI.create(ISA_ORDER_API_URL)).header("Content-Type", "application/json").GET(HttpRequest.BodyPublishers.ofString(jsonRequestBody)).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Order placed successfully.");
                return true;
                return new String[]{"Previous orders: Order1, Order2, Order3."};


            }
        } catch (Exception e) {
            System.out.println("couldnt reach team ISA Order API: " + e.getMessage());
            return new String[0];
        }
    }

    @Override
    public String[] getCatalogue() {
        try {

            HttpRequest request = HttpRequest.newBuilder().build().uri(URI.create(ISA_ORDER_API_URL)).header("Content-Type", "application/json").GET(HttpRequest.BodyPublishers.ofString(jsonRequestBody)).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Catalogue fetched sucessfully.");
                return new String[]{response.body().replace("[", "")};

            } else {
                return new String[0]
                        ;
            }
        } catch (Exception e) {
            System.out.println("couldnt reach team ISA Order API: " + e.getMessage());
            return new String[0];
        }
    }


    @Override
    public String[] viewInvoices(int merchantID) {
        try {

            HttpRequest request = HttpRequest.newBuilder().build().uri(URI.create(ISA_ORDER_API_URL)).header("Content-Type", "application/json").GET(HttpRequest.BodyPublishers.ofString(jsonRequestBody)).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Invoices fetched sucessfully.");
                return new String[]{response.body().replace("[", "")};
            } else {
                return new String[0];
            }
        } catch (Exception e) {
            System.out.println("couldnt reach team ISA Order API: " + e.getMessage());
            return new String[0];
        }
    }
}


