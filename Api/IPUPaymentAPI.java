public interface IPUPaymentAPI {

    boolean submitPayment(int paymentAmount, String name, int cardNumber, int expiryDate, String cardType);

}
