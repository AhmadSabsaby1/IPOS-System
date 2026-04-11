package rpt.model;

import cust.model.AccountHolder;
import cust.model.LocalItem;
import cust.model.Order;
import cust.model.OrderItem;
import database.DBAccountHolders;
import database.DBLocalStock;
import database.DBTransactions;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * RPTModel – pulls data from the DB for report generation.
 * UC4  – Generate Business Report (turnover, stock, debt summary)
 * UC23 – Query Outstanding Balance
 * UC24 – Generate Monthly Statements
 */
public class RPTModel {

    private DBAccountHolders accountHoldersDB;
    private DBLocalStock     localStockDB;
    private DBTransactions   transactionsDB;

    public RPTModel() {
        try {
            accountHoldersDB = new DBAccountHolders();
            localStockDB     = new DBLocalStock();
            transactionsDB   = new DBTransactions();
        } catch (Exception e) {
            System.err.println("[RPTModel] DB init error: " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------------
    // UC4 – Business Report data
    // -----------------------------------------------------------------------

    /** Returns all account holders (for debt summary in business report). */
    public ArrayList<AccountHolder> getAccountHolders() {
        ArrayList<AccountHolder> list = new ArrayList<>();
        try {
            ResultSet rs = accountHoldersDB.getAccounts();
            while (rs.next()) {
                list.add(new AccountHolder(rs));
            }
        } catch (Exception e) {
            System.err.println("[RPTModel] getAccountHolders error: " + e.getMessage());
        }
        return list;
    }

    /** Returns all stock items (for stock availability report). */
    public ArrayList<LocalItem> getStock() {
        ArrayList<LocalItem> list = new ArrayList<>();
        try {
            ResultSet rs = localStockDB.getStock();
            while (rs.next()) {
                list.add(new LocalItem(rs));
            }
        } catch (Exception e) {
            System.err.println("[RPTModel] getStock error: " + e.getMessage());
        }
        return list;
    }

    /** Returns all orders (for turnover/sales report). */
    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> list = new ArrayList<>();
        try {
            ResultSet rs = transactionsDB.getTransactions();
            while (rs.next()) {
                Order o = new Order(rs);
                o.setItemsOrdered(getOrderItems(o.getOrderID()));
                list.add(o);
            }
        } catch (Exception e) {
            System.err.println("[RPTModel] getAllOrders error: " + e.getMessage());
        }
        return list;
    }

    /** Gets items for a specific order. */
    public ArrayList<OrderItem> getOrderItems(String orderId) {
        ArrayList<OrderItem> items = new ArrayList<>();
        try {
            ResultSet rs = transactionsDB.getOrderInfo(orderId);
            while (rs.next()) {
                String itemId      = rs.getString("itemID");
                int    quantity    = rs.getInt("quantity");
                ResultSet itemData = localStockDB.getItemInfo(itemId);
                if (itemData.next()) {
                    String description = itemData.getString("description");
                    double cost        = itemData.getDouble("packageCost");
                    items.add(new OrderItem(itemId, description, quantity, cost));
                }
            }
        } catch (Exception e) {
            System.err.println("[RPTModel] getOrderItems error: " + e.getMessage());
        }
        return items;
    }

    // -----------------------------------------------------------------------
    // UC23 – Query Outstanding Balance
    // -----------------------------------------------------------------------

    /** Returns a single account holder by ID for balance query. */
    public AccountHolder getAccountById(String accountId) {
        try {
            ResultSet rs = accountHoldersDB.getCustomerInfo(accountId);
            if (rs.next()) return new AccountHolder(rs);
        } catch (Exception e) {
            System.err.println("[RPTModel] getAccountById error: " + e.getMessage());
        }
        return null;
    }

    // -----------------------------------------------------------------------
    // UC24 – Monthly Statements
    // -----------------------------------------------------------------------

    /** Returns orders for a specific account holder (for monthly statement). */
    public ArrayList<Order> getOrdersByAccount(String accountId) {
        ArrayList<Order> list = new ArrayList<>();
        try {
            ResultSet rs = transactionsDB.getTransactionsByAccountID(accountId);
            while (rs.next()) {
                Order o = new Order(rs);
                o.setItemsOrdered(getOrderItems(o.getOrderID()));
                list.add(o);
            }
        } catch (Exception e) {
            System.err.println("[RPTModel] getOrdersByAccount error: " + e.getMessage());
        }
        return list;
    }

    // -----------------------------------------------------------------------
    // Summary calculations
    // -----------------------------------------------------------------------

    /** Total turnover across all orders. */
    public double calculateTotalTurnover(ArrayList<Order> orders) {
        double total = 0;
        for (Order o : orders) total += o.getTotalCost();
        return total;
    }

    /** Total aggregated debt across all account holders. */
    public double calculateTotalDebt(ArrayList<AccountHolder> accounts) {
        double total = 0;
        for (AccountHolder a : accounts) total += a.getBalance();
        return total;
    }
}
