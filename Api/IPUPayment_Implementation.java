package Api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class IPUPayment_Implementation implements IPUPaymentAPI {

    private static final String IPU_PAYMENT_API_URL = "https://api.ipupayment.com/submitPayment";
    /// once we get the actual URL from the other teams we would replace them




    @Override
    public boolean submitPayment(int paymentAmount, String name, int cardNumber, int expiryDate, String cardType) {

        String jsonRequestBody = String.format(
                "{\"paymentAmount\": %d, \"name\": \"%s\", \"cardNumber\": %d, \"expiryDate\": %d, \"cardType\": \"%s\"}",
                paymentAmount, name, cardNumber, expiryDate, cardType
        );

        try {

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(IPU_PAYMENT_API_URL)).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody)).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Payment processed successfully.");
                return true;
            } else {
                System.out.println("Payment processing failed with status code: " + response.statusCode());
                return false;
            }


        } catch (Exception e) {

            System.out.println("couldnt reach team IPU Payment API: " + e.getMessage());
            return false;

        }

    }
}
