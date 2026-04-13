package Api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.lang.String.*;

public class ISALogin_Implementation implements ISALoginAPI {
    private static final String ISA_LOGIN_API_URL = "https://grtggfghfgh.free.beeceptor.com";
    /// to logni call GET .../api/auth/login
    /// after login call GET .../api/merchants/me
    //String TOken

    /// once we get the actual URL from the other teams we would replace them


    @Override
    public boolean merchantLogin(String username, String password) {

        String jsonRequestBody = format(
                "{\"username\": \"%s\", \"password\": \"%s\"}",
                username, password
        );

        try {

            HttpRequest loginRequest = HttpRequest.newBuilder().uri(URI.create(ISA_LOGIN_API_URL + "/api/auth/login")).header("Content-Type", "merchantLogin/json").POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody)).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> loginResponse = client.send(loginRequest, HttpResponse.BodyHandlers.ofString());

            if (loginResponse.statusCode() == 200) {
                SessionManager.token = loginResponse.body().split("\"access_token\":\"")[1].split("\"")[0];
                HttpRequest infoRequest = HttpRequest.newBuilder().uri(URI.create(ISA_LOGIN_API_URL + "/api/merchants/me")).header("Authorization","Bearer" + SessionManager.token).POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody)).build();
                client = HttpClient.newHttpClient();
                HttpResponse<String> infoResponse = client.send(infoRequest, HttpResponse.BodyHandlers.ofString());

                if (infoResponse.statusCode() == 200) {
                    ///profile info

                    SessionManager.merchant_Id = infoResponse.body().split("\"id\":\"")[1].split("\"")[0];
                    SessionManager.user_ID = infoResponse.body().split("\"user_Id\":\"")[1].split("\"")[0];
                    SessionManager.account_number = infoResponse.body().split("\"accountNumber\":\"")[1].split("\"")[0];
                    SessionManager.contact_name = infoResponse.body().split("\"contactName\":\"")[1].split("\"")[0];
                    SessionManager.contact_email = infoResponse.body().split("\"contactEmail\":\"")[1].split("\"")[0];
                    SessionManager.contact_phone = infoResponse.body().split("\"contact\":\"")[1].split("\"")[0];
                    SessionManager.address = infoResponse.body().split("\"address\":\"")[1].split("\"")[0];
                    SessionManager.credit_limit = Double.parseDouble(infoResponse.body().split("\"creditLimit\":\"")[1].split("\"")[0]);
                    SessionManager.discount_plan_type = infoResponse.body().split("\"discountPlanType\":\"")[1].split("\"")[0];
                    SessionManager.account_status = infoResponse.body().split("\"accountStatus\":\"")[1].split("\"")[0];
                    SessionManager.fixed_discount_rate = Double.parseDouble(infoResponse.body().split("\"fixedDiscountRate\":\"")[1].split("\"")[0]);

                    System.out.println("Login successfully.");
                    return true;
                }
            }
            else if (loginResponse.statusCode() == 401) {
                System.out.println("Unauthorized: Wrong username or password.");
                return false;
            }
            else if (loginResponse.statusCode() == 402) {
                System.out.println("Account is inactive, contact support.");
            }
            else {
                System.out.println("failed to reach ISA" + loginResponse.statusCode());
                return false;
            }

            return false;
        } catch (Exception e) {

            System.out.println("couldnt reach team ISA Login API: " + e.getMessage());
            return false;
        }
    }


    @Override
    public boolean merchantDisconnect() {
        String jsonRequestBody = "";
        try {
            HttpRequest logoutRequest = HttpRequest.newBuilder().uri(URI.create(ISA_LOGIN_API_URL + "/api/auth/logout")).header("Authorization","Bearer" + SessionManager.token).POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody)).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> logoutResponse = client.send(logoutRequest, HttpResponse.BodyHandlers.ofString());
            if (logoutResponse.statusCode() == 200) {
                SessionManager.token = "";
                SessionManager.merchant_Id = "";
                return true;
            }
            else {
                System.out.println("failed to reach ISA" + logoutResponse.statusCode());
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }
}

