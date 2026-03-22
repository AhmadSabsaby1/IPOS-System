public interface ISAOrderAPI {

	/**
	 * Sends to the SA an order that consists of a product and its quantiy.
	 * @param productID The product's ID of the order
	 * @param quantity  The quantity of the products of the order
	 */
	abstract boolean placeOrder(int productID, int quantity);

	/**
	 * Requests to the SA the progress of an order.
	 * @param orderID The ID of the order to be tracked
	 */
	abstract String trackOrderProgress(int orderID);

	/**
	 * Requests to the SA the outstanding balance of a merchant.
	 * @param merchantID The ID of the merchant whose balance is to be checked
	 */
	abstract int queryBalance(int merchantID);

	/**
	 * Requests to the SA a list of the previous orders the merchant has made.
	 * @param merchantID The ID of the merchant whose orders are to be listed
	 */
	abstract String[] viewPreviousOrders(int merchantID);

	/**
	 * Requests to the SA the entire catalogue of products.
	 * */
	abstract String[] getCatalogue();

	/**
	 * Requests to the SA the invoices of a merchant.
	 * @param merchantID The ID of the merchant whose invoices are to be viewed
	 */
	abstract String[] viewInvoices(int merchantID);

}