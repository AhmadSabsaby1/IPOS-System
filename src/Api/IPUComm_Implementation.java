package Api;

import java.io.IOException;

import database.DBAccountHolders;
import database.DBTransactions;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.ResultSet;

public class IPUComm_Implementation implements IPUCommAPI {

    ///i think WORKS!!
    private static final String IPU_COMM_URL = "https://thevylethomsa.free.beeceptor.com";
    /// once we get the actual URL from the other teams we would replace them

    @Override
    public boolean sendEmail(String accountID, String body, String subject) {
        try{
        DBAccountHolders dbAcc = new database.DBAccountHolders();
        ResultSet rs =dbAcc.getCustomerInfo(accountID);

        if (!rs.next()) return false; // Account not found
        String targetEmail = rs.getString("email");

        String jsonRequestBody = String.format(
                "{\"email\": %s, \"body\": %s, \"subject\":%s}",
                targetEmail, body, subject
        );


                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(IPU_COMM_URL)).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody)).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Email sent sucessfully to" + targetEmail);
                return true;
            } else {
                System.out.println("Email sending failed with status code: ");
                return false;
            }


        } catch (Exception e) {

            System.out.println("couldnt reach team IPU Comms: " + e.getMessage());
            return false;

        }


    }

/// Sending order updates to the account holder
    @Override
    public boolean getOrderUpdate(int orderID, String status) {

        try {

            String formattedID = "IP" + String.format("%04d", orderID);
            DBTransactions db = new DBTransactions();


            if (db.getOrderInfo(formattedID).next()) {
                System.out.println("Order found in database");
            }


        String jsonRequestBody = String.format(
                "{\"orderID\": %d, \"status\": \"%s\"}",
                orderID, status
        );



            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(IPU_COMM_URL)).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody)).build();
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
