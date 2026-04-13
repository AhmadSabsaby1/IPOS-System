package Api;

import Api.IPUComm_Implementation;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

public class ApiTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        IPUComm_Implementation IPUapi = new IPUComm_Implementation();
        CaCatalogAPI_Implementation CAapi = new CaCatalogAPI_Implementation();
        ISAOrder_Implementation ISAapi = new ISAOrder_Implementation();
        IPUPayment_Implementation IPUpayment = new IPUPayment_Implementation();

        ///CA CATALOG TEST

        ///getCatalogue.POST
        try {
            System.out.println("--- Testing getCatalogue ---");
            String[] localResult = CAapi.getCatalogue();

            /// Check if local array is populated
            System.out.println("Items in local catalogue array: " + localResult.length);
            for (String row : localResult) {
                System.out.println("Row Data: " + row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        ///sendOrderDetails.POST
        System.out.println("\n--- Testing sendOrderDetails ---");
        HashMap<Integer, Integer> orderDetails = new HashMap<>();
        orderDetails.put(1001, 5);
        orderDetails.put(1002, 3);
        boolean sendOrderResult = CAapi.sendOrderDetails(orderDetails,55,"longjohn");
        System.out.println("Send Order Details Success: " + sendOrderResult);





        ///IPU COMM TEST-allhere

        /// sendEmail.POST
        //send email to customer getOrder update will use  and
        System.out.println("--- Testing sendEmail ---");
        boolean emailResult = IPUapi.sendEmail("ACC0001", "This is a test body", "Test Subject");
        System.out.println("Email Success: " + emailResult);

        /// getOrderUpdate.POST
        System.out.println("\n--- Testing getOrderUpdate ---");
        boolean orderUpdateResult = IPUapi.getOrderUpdate(12345, "Shipped");
        System.out.println("Order Update Success: " + orderUpdateResult);


        ///IPU PAYMENT TEST-=allhere

        /// submitPayment
        System.out.println("\n--- Testing submitPayment ---");
        boolean paymentResult = IPUpayment.submitPayment(100, "John Doe", 1234567890, 1225, "Visa");
        System.out.println("Payment Success: " + paymentResult);





        ///ISA ORDER TEST-allhere

        /// placeOrder.POST
        //for merchant
        System.out.println("--- Testing placeOrder ---");
        HashMap<Integer, Integer> cart = new HashMap<>();
        cart.put(1001, 10);
        cart.put(1002, 2);
        boolean placeResult = ISAapi.placeOrder( cart);
        System.out.println("Place Order Success: " + placeResult);

        /// trackOrderProgress.GET()
        System.out.println("\n--- Testing trackOrderProgress ---");
        String orderStatus = ISAapi.trackOrderProgress("12345");
        System.out.println("Order Status: " + orderStatus);

        ///queryBalance.GET()
        System.out.println("\n--- Testing queryBalance ---");
        String balance = ISAapi.queryBalance(SessionManager.merchant_Id );
        System.out.println(balance);

        /// viewPreviousOrders.GET()
        System.out.println("\n--- Testing viewPreviousOrders ---");
        String[] previousOrders = ISAapi.viewPreviousOrders("55");
        System.out.println("Previous Orders: " + Arrays.toString(previousOrders));

        /// getCatalogue.GET()
        //get sa catalogue
        System.out.println("\n--- Testing getCatalogue ---");
        String[] getCatalogue = ISAapi.getCatalogue();
        System.out.println("Catalogue Body: " + Arrays.toString(getCatalogue));

        ///viewInvoices.GET()
        System.out.println("\n--- Testing viewInvoices---");
        String[]  viewInvoices = ISAapi.viewInvoices("55");
        System.out.println("Catalogue Body: " + Arrays.toString(viewInvoices));




        ///ISA LOGIN TEST

        /// merchantLogin.POST
        ISALogin_Implementation loginAPI = new ISALogin_Implementation();
        System.out.println("\n--- Testing merchantLogin ---");
        boolean loginResult = loginAPI.merchantLogin("testUser", "testPassword");
        System.out.println("Login Success: " + loginResult);

        //merchantDisconnect



    }
}