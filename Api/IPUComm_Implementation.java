package Api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class IPUComm_Implementation implements IPUCommAPI {

    private static final String IPU_COMM_URL = "https://api.ipucomm.com/commapi";
    /// once we get the actual URL from the other teams we would replace them

    @Override
    public boolean sendEmail(String email, String body, String subject) {

        String jsonRequestBody = String.format(
                "{\"email\": %s, \"body\": %s, \"subject\":%s}",
                email, body, subject
        );

        try {

            HttpRequest request = HttpRequest.newBuilder().build().uri(URI.create(IPU_COMM_URL)).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody)).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Email sent sucessfully.");
                return true;
            } else {
                System.out.println("Email sending failed with status code: ");
                return false;
            }


        } catch (Exception e) {

            System.out.println("couldnt reach team IPU Comms: " + e.getMessage());
            return false;

        }


        return false;
    }


    @Override
    public boolean getOrderUpdate(int orderID, String status) {
        String jsonRequestBody = String.format(
                "{\"orderID\": %d, \"status\": \"%s\"}",
                orderID, status
        );

        try {

            HttpRequest request = HttpRequest.newBuilder().build().uri(URI.create(IPU_COMM_URL)).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody)).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Order update sent sucessfully.");
                return true;
            } else {
                System.out.println("Order update failed with status code: ");
                return false;
            }

        } catch (Exception e) {
            return false;
        }

    }
}
