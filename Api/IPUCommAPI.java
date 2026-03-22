public interface IPUCommAPI {

	/**
	 * Sends to SA a request for a commercial application.
	 * @param subject   The adress of the business
	 * @param email     The email of contact
	 * @param body   Holds any other information of the application, like
	 *                     additional details on the Company Director(s)
	 */
	boolean sendEmail(String email, String body, String subject);

	boolean  getOrderUpdate(int orderID, String status);
}