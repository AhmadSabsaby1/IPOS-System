package rpt.model;

import database.DBAccountHolders;
import database.DBLocalStock;
import database.DBTransactions;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * RPTModel - pulls raw data from the DB for report generation.

 *
 * UC4  - Generate Business Report (turnover, stock, debt summary)
 * UC23 - Query Outstanding Balance
 * UC24 - Generate Monthly Statements
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

    // UC4 - Turnover rows: {orderID, paymentType, amountReceived, shippingAddress}
    public ArrayList<String[]> getTurnoverRows() {
        ArrayList<String[]> rows = new ArrayList<>();
        try {
            ResultSet rs = transactionsDB.getTransactions();
            while (rs.next()) {
                rows.add(new String[]{
                    rs.getString("orderID"),
                    rs.getString("paymentType"),
                    String.format("%.2f", rs.getDouble("amountReceived")),
                    rs.getString("shippingAddress")
                });
            }
        } catch (Exception e) {
            System.err.println("[RPTModel] getTurnoverRows error: " + e.getMessage());
        }
        return rows;
    }

    public double getTotalTurnover() {
        double total = 0;
        try {
            ResultSet rs = transactionsDB.getTransactions();
            while (rs.next()) total += rs.getDouble("amountReceived");
        } catch (Exception e) {
            System.err.println("[RPTModel] getTotalTurnover error: " + e.getMessage());
        }
        return total;
    }

    // UC4 - Stock rows: {itemID, description, packageType, unit, availability, stockLimit}
    public ArrayList<String[]> getStockRows() {
        ArrayList<String[]> rows = new ArrayList<>();
        try {
            ResultSet rs = localStockDB.getStock();
            while (rs.next()) {
                rows.add(new String[]{
                    rs.getString("itemID"),
                    rs.getString("description"),
                    rs.getString("packageType"),
                    rs.getString("unit"),
                    String.valueOf(rs.getInt("availability")),
                    String.valueOf(rs.getInt("stockLimit"))
                });
            }
        } catch (Exception e) {
            System.err.println("[RPTModel] getStockRows error: " + e.getMessage());
        }
        return rows;
    }

    // UC4 - Debt rows: {accountID, name, balance, balanceLimit, status}
    public ArrayList<String[]> getDebtRows() {
        ArrayList<String[]> rows = new ArrayList<>();
        try {
            ResultSet rs = accountHoldersDB.getAccounts();
            while (rs.next()) {
                rows.add(new String[]{
                    rs.getString("accountID"),
                    rs.getString("name"),
                    String.format("%.2f", rs.getDouble("balance")),
                    String.valueOf(rs.getInt("balanceLimit")),
                    rs.getString("status")
                });
            }
        } catch (Exception e) {
            System.err.println("[RPTModel] getDebtRows error: " + e.getMessage());
        }
        return rows;
    }

    public double getTotalDebt() {
        double total = 0;
        try {
            ResultSet rs = accountHoldersDB.getAccounts();
            while (rs.next()) total += rs.getDouble("balance");
        } catch (Exception e) {
            System.err.println("[RPTModel] getTotalDebt error: " + e.getMessage());
        }
        return total;
    }

    // UC23 - Account details: {accountID, name, balance, balanceLimit, status}
    public String[] getAccountDetails(String accountId) {
        try {
            ResultSet rs = accountHoldersDB.getCustomerInfo(accountId);
            if (rs.next()) {
                return new String[]{
                    rs.getString("accountID"),
                    rs.getString("name"),
                    String.format("%.2f", rs.getDouble("balance")),
                    String.valueOf(rs.getInt("balanceLimit")),
                    rs.getString("status")
                };
            }
        } catch (Exception e) {
            System.err.println("[RPTModel] getAccountDetails error: " + e.getMessage());
        }
        return null;
    }

    // UC23 - Orders for account: {orderID, paymentType, amountReceived, shippingAddress}
    public ArrayList<String[]> getOrderRowsByAccount(String accountId) {
        ArrayList<String[]> rows = new ArrayList<>();
        try {
            ResultSet rs = transactionsDB.getTransactions();
            while (rs.next()) {
                String accId = rs.getString("accountID");
                if (accountId.equals(accId)) {
                    rows.add(new String[]{
                        rs.getString("orderID"),
                        rs.getString("paymentType"),
                        String.format("%.2f", rs.getDouble("amountReceived")),
                        rs.getString("shippingAddress")
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("[RPTModel] getOrderRowsByAccount error: " + e.getMessage());
        }
        return rows;
    }

    // UC24 - Account list for dropdown: {accountID, name}
    public ArrayList<String[]> getAccountList() {
        ArrayList<String[]> list = new ArrayList<>();
        try {
            ResultSet rs = accountHoldersDB.getAccounts();
            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("accountID"),
                    rs.getString("name")
                });
            }
        } catch (Exception e) {
            System.err.println("[RPTModel] getAccountList error: " + e.getMessage());
        }
        return list;
    }
}
