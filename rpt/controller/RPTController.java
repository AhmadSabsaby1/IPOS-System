package rpt.controller;

import rpt.model.RPTModel;
import rpt.view.*;

import javax.swing.*;
import java.util.ArrayList;

/**
 * RPTController - wires RPT model and views together.
 * UC4  - Generate Business Report
 * UC23 - Query Outstanding Balance
 * UC24 - Generate Monthly Statements
 * UC29 - Print Report (inside views)
 */
public class RPTController {

    private RPTModel model;

    private RPTMainView            mainView;
    private HubView                hubView;
    private BusinessReportView     businessReportView;
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

    public void goToHubScreen() {
        mainView.changeCardView(HubView.cardId());
    }

    // UC4
    public void goToBusinessReportScreen() {
        ArrayList<String[]> turnoverRows = model.getTurnoverRows();
        ArrayList<String[]> stockRows    = model.getStockRows();
        ArrayList<String[]> debtRows     = model.getDebtRows();
        double totalTurnover = model.getTotalTurnover();
        double totalDebt     = model.getTotalDebt();

        businessReportView.populate(turnoverRows, stockRows, debtRows, totalTurnover, totalDebt);
        mainView.changeCardView(BusinessReportView.cardId());
    }

    // UC23
    public void goToOutstandingBalanceScreen() {
        mainView.changeCardView(OutstandingBalanceView.cardId());
    }

    public void queryOutstandingBalance(String accountId) {
        String[] details = model.getAccountDetails(accountId);
        outstandingBalanceView.displayAccountDetails(details);
        if (details != null) {
            ArrayList<String[]> orders = model.getOrderRowsByAccount(accountId);
            outstandingBalanceView.populateOrdersTable(orders);
        }
    }

    // UC24
    public void goToMonthlyStatementsScreen() {
        monthlyStatementsView.populateAccounts(model.getAccountList());
        mainView.changeCardView(MonthlyStatementsView.cardId());
    }

    public void generateMonthlyStatement(String accountId) {
        String[] details = model.getAccountDetails(accountId);
        if (details == null) {
            JOptionPane.showMessageDialog(null, "Account not found.");
            return;
        }
        ArrayList<String[]> orders = model.getOrderRowsByAccount(accountId);
        double total = 0;
        for (String[] row : orders) {
            try { total += Double.parseDouble(row[2]); } catch (Exception ignored) {}
        }
        monthlyStatementsView.displayStatement(details, orders, total);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RPTController::new);
    }
}
