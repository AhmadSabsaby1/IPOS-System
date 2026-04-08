package Api;

import Api.IPUComm_Implementation;

public class ApiTest {
    public static void main(String[] args) {
        IPUComm_Implementation api = new IPUComm_Implementation();

        boolean emailResult = api.sendEmail("ACC0001", "This is a test body", "Test Subject");
        System.out.println("Email Success: " + emailResult);

    }
}