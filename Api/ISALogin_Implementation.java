package Api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ISALogin_Implementation implements ISALoginAPI {
    private static final String ISA_LOGIN_API_URL = "https://webhook.site/e2225256-8db6-4234-ba8e-af3a51faa852";
    /// once we get the actual URL from the other teams we would replace them




    @Override
    public boolean merchantLogin(String username, String password) {

        String jsonRequestBody = String.format(
                "{\"username\": %s, \"password\": \"%s\"}",
                username, password
        );

        try {

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ISA_LOGIN_API_URL)).header("Content-Type", "merchantLogin/json").POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody)).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Login successfully.");
                ///should get something in response to sendus to login page
                String merchant = response.body();
                return true;
            } else {
                System.out.println("Wrong username or password, status code: " + response.statusCode());
                return false;
            }


        } catch (Exception e) {

            System.out.println("couldnt reach team ISA Login API: " + e.getMessage());
            return false;

        }

    }


    @Override
    public boolean merchantDisconnect(int merchantID) {
        //idk look around or ask how to do




        return true;
    }




}


