package Api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.UUID;
import java.util.UUID.*;


public class ISAOrder_Implementation implements ISAOrderAPI {
    private static final String ISA_ORDER_API_URL = "https://pioiyuo.free.beeceptor.com";


    /// once we get the actual URL from the other teams we would replace them


    @Override
    public boolean placeOrder(HashMap<String, Integer> orderDetails) {

        StringBuilder sb= new StringBuilder();


        sb.append("\"items\":[");

        int i = 0;

        for (String productId : orderDetails.keySet()) {
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

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ISA_ORDER_API_URL)).header("Content-Type", "placeOrder/json").header("Authorization","Bearer" + SessionManager.token).POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody)).build();
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
    public String trackOrderProgress(String orderID) {

        try {
            UUID orderUUID = UUID.fromString(orderID);

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ISA_ORDER_API_URL +"/orders/" + orderUUID)).header("Content-Type", "trackOrderProgress/json").GET().build();
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
    public String queryBalance(String merchantID) {

        try {

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ISA_ORDER_API_URL + "/merchants/" + SessionManager.merchant_Id+"/balance")).header("Content-Type", "queryBalance/json").header("Authorization","Bearer" + SessionManager.token).GET().build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                String body = response.body();
                String merchant_id = body.split("\"merchant_id\":")[1].split("\"")[1];
                String credit_limit = body.split("\"credit_limit\":")[1].split("\"")[0].trim();
                String outstandBalance = body.split("\"oustanding_balance\":")[1].split("\"")[0].trim();
                String availBalance = body.split("\"available_balance\":")[1].split("\"")[0].trim();

                double creditLimit = Double.parseDouble(credit_limit);
                double outstandingBalance = Double.parseDouble(outstandBalance);
                double availableBalance = Double.parseDouble(availBalance);
                return new MerchantBalance(merchant_id, creditLimit, outstandingBalance,availableBalance).toString();

            } else {
                System.out.println("Failed to retrieve balance: " + response.statusCode());
                return "{}";

            }

        } catch (Exception e) {
            System.out.println("couldnt reach team ISA Order API: ");

        }

        return "";
    }

    @Override
    public String[] viewPreviousOrders(String merchantID) {

        try {

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ISA_ORDER_API_URL+"/orders")).header("Content-Type", "viewPreviousOrders/json").header("Authorization","Bearer" + SessionManager.token).GET().build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body().replace("[", "").replace("]", "").split("},");


            }
        } catch (Exception e) {
            System.out.println("couldnt reach team ISA Order API: " + e.getMessage());
            return new String[0];
        }
        return new String[0];
    }


    /**Catalogue response
     [
        {
         "id": "b9d3c2a1-aaaa-4b5c-8d9e-000000000001",
         "product_code": "AMOX-500",
         "name": "Amoxicillin 500mg Capsules",
         "description": "Broad-spectrum antibiotic capsules",
         "package_type": "Box",
         "unit": "capsule",
         "units_per_pack": 21,
         "package_cost": 4.50,
         "stock_quantity": 340,
         "min_stock_level": 50,
         "restock_percentage": 10.00,
         "created_at": "2026-01-10T09:00:00Z",
         "updated_at": "2026-04-01T12:00:00Z"
        },
        {
         "id": "b9d3c2a1-bbbb-4b5c-8d9e-000000000002",
         "product_code": "IBUP-400",
         "name": "Ibuprofen 400mg Tablets",
         "description": "Anti-inflammatory pain relief tablets",
         "package_type": "Box",
         "unit": "tablet",
         "units_per_pack": 24,
         "package_cost": 2.80,
         "stock_quantity": 600,
         "min_stock_level": 100,
         "restock_percentage": 10.00,
         "created_at": "2026-01-10T09:00:00Z",
         "updated_at": "2026-04-01T12:00:00Z"
        }
     ]*/

    @Override
    public String[] getCatalogue() {
        try {

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ISA_ORDER_API_URL +"/catalogue")).header("Content-Type", "getCatalogue/json").GET().build();
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



    /**Invoice response
     * [
     {
     "id": "e1f2a3b4-cccc-4444-9999-aabbccddeeff",
     "order_id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
     "merchant_id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
     "invoice_date": "2026-04-12",
     "total_amount": 59.00,
     "discount_amount": 2.95,
     "amount_due": 56.05
     }, ...]*/
    @Override
    public String[] viewInvoices(String merchantID) {
        try {

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ISA_ORDER_API_URL)).header("Content-Type", "viewInvoices/json").header("Authorization","Bearer" + SessionManager.token).GET().build();
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


