package Api;

import java.util.HashMap;

public interface ISAOrderAPI {

	/**
	 * Sends to the SA an order that consists of a product and its quantiy.
	 * @param orderDetails The product's ID of the order and its quantity, stored in a HashMap where the key is the product's ID and the value is the quantity of that product in the order
	 */
	abstract boolean placeOrder(HashMap<Integer, Integer> orderDetails);

	/**
	 * Requests to the SA the progress of an order.
	 * @param orderID The UUID of the order to be tracked
	 */
	abstract String trackOrderProgress(String orderID);

	/**
	 * Requests to the SA the outstanding balance of a merchant.
	 *
	 * @param merchantID The UUID of the merchant whose balance is to be checked
	 */
	abstract String queryBalance(String merchantID);

	/**
	 * Requests to the SA a list of the previous orders the merchant has made.
	 * @param merchantID The UUID of the merchant whose orders are to be listed
	 */
	abstract String[] viewPreviousOrders(String merchantID);

	/**
	 * Requests to the SA the entire catalogue of products.
	 * */
	abstract String[] getCatalogue();

	/**
	 * Requests to the SA the invoices for a merchant.
	 * @param merchantID The UUID of the merchant whose invoices are to be viewed
	 */
	abstract String[] viewInvoices(String merchantID);


	/**
	 * Requests to the SA the invoice for a merchant.
	 * @param order_id UUID of the individual invoice the merchant wants to view
	 */
	abstract String[] viewIndividualInvoice( String order_id);

}