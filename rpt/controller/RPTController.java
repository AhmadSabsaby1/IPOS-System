package rpt.controller;

import cust.model.AccountHolder;
import cust.model.LocalItem;
import cust.model.Order;
import rpt.model.RPTModel;
import rpt.view.*;

import javax.swing.*;
import java.util.ArrayList;

/**
 * RPTController – wires the RPT model and views together.
 * Follows the same pattern as CUSTController and ORDController.
 *
 * UC4  – Generate Business Report
 * UC23 – Query Outstanding Balance
 * UC24 – Generate Monthly Statements
 * UC29 – Print Report (handled inside BusinessReportView and MonthlyStatementsView)
 */
public class RPTController {

    private RPTModel model;

    private RPTMainView         mainView;
    private HubView             hubView;
    private BusinessReportView  businessReportView;
    private OutstandingBalanceView outstandingBalanceView;
    private MonthlyStatementsView  monthlyStatementsView;

    public RPTController() {
        model = new RPTModel();

        mainView               = new RPTMainView();
        hubView                = new HubView(this);
        businessReportView     = new BusinessReportView(this);
        outstandingBalanceView = new OutstandingBalanceView(this);
        monthlyStatementsView  = new MonthlyStatementsView(this);

        mainView.addCardLayout(hubView,                HubView.cardId());
        mainView.addCardLayout(businessReportView,     BusinessReportView.cardId());
        mainView.addCardLayout(outstandingBalanceView, OutstandingBalanceView.cardId());
        mainView.addCardLayout(monthlyStatementsView,  MonthlyStatementsView.cardId());

        mainView.changeCardView(HubView.cardId());
    }

    // -----------------------------------------------------------------------
    // Screen navigation
    // -----------------------------------------------------------------------

    public void goToHubScreen() {
        mainView.changeCardView(HubView.cardId());
    }

    /** UC4 – loads all data and shows the business report. */
    public void goToBusinessReportScreen() {
        ArrayList<Order>         orders   = model.getAllOrders();
        ArrayList<LocalItem>     stock    = model.getStock();
        ArrayList<AccountHolder> accounts = model.getAccountHolders();
        double totalTurnover = model.calculateTotalTurnover(orders);
        double totalDebt     = model.calculateTotalDebt(accounts);

        businessReportView.populate(orders, stock, accounts, totalTurnover, totalDebt);
        mainView.changeCardView(BusinessReportView.cardId());
    }

    /** UC23 – shows the outstanding balance search screen. */
    public void goToOutstandingBalanceScreen() {
        mainView.changeCardView(OutstandingBalanceView.cardId());
    }

    /** UC24 – loads account list and shows the monthly statements screen. */
    public void goToMonthlyStatementsScreen() {
        monthlyStatementsView.populateAccounts(model.getAccountHolders());
        mainView.changeCardView(MonthlyStatementsView.cardId());
    }

    // -----------------------------------------------------------------------
    // Business logic
    // -----------------------------------------------------------------------

    /** UC23 – queries and displays the outstanding balance for an account. */
    public void queryOutstandingBalance(String accountId) {
        AccountHolder account = model.getAccountById(accountId);
        outstandingBalanceView.displayAccountDetails(account);
        if (account != null) {
            ArrayList<Order> orders = model.getOrdersByAccount(accountId);
            outstandingBalanceView.populateOrdersTable(orders);
        }
    }

    /** UC24 – generates the monthly statement for a selected account. */
    public void generateMonthlyStatement(String accountId) {
        AccountHolder account = model.getAccountById(accountId);
        if (account == null) {
            JOptionPane.showMessageDialog(null, "Account not found.");
            return;
        }
        ArrayList<Order> allOrders = model.getOrdersByAccount(accountId);

        // Filter to current month only
        java.time.LocalDate now = java.time.LocalDate.now();
        ArrayList<Order> monthOrders = new ArrayList<>();
        double totalSpend = 0;
        for (Order o : allOrders) {
            try {
                java.time.LocalDate orderDate = java.time.LocalDate.parse(o.getOrderDate());
                if (orderDate.getMonth() == now.getMonth() && orderDate.getYear() == now.getYear()) {
                    monthOrders.add(o);
                    totalSpend += o.getTotalCost();
                }
            } catch (Exception e) {
                // If date can't be parsed, include the order anyway
                monthOrders.add(o);
                totalSpend += o.getTotalCost();
            }
        }

        monthlyStatementsView.displayStatement(account, monthOrders, totalSpend);
    }

    // -----------------------------------------------------------------------
    // Entry point (for testing standalone)
    // -----------------------------------------------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RPTController::new);
    }
}
