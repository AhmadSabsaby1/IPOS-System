package rpt.view;

import cust.model.AccountHolder;
import custom.CTable;
import custom.TitleLabel;
import rpt.controller.RPTController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * OutstandingBalanceView – UC23 (Query Outstanding Balance).
 * Allows the manager or pharmacist to check the current debt of a specific customer.
 */
public class OutstandingBalanceView extends JPanel {

    private RPTController controller;

    private TitleLabel titleLabel;
    private JButton    backButton;
    private JButton    searchButton;

    private JLabel     accountIdLabel;
    private JTextField accountIdField;

    private JLabel resultLabel;
    private CTable ordersTable;

    // Summary labels
    private JLabel nameLabel;
    private JLabel balanceLabel;
    private JLabel balanceLimitLabel;
    private JLabel statusLabel;

    public static String cardId() { return "OutstandingBalanceView"; }

    public OutstandingBalanceView(RPTController controller) {
        this.controller = controller;

        titleLabel    = new TitleLabel("Query Outstanding Balance (UC23)");
        backButton    = new JButton("Back");
        searchButton  = new JButton("Search");

        accountIdLabel = new JLabel("Account ID:");
        accountIdField = new JTextField(10);

        resultLabel   = new JLabel(" ");
        resultLabel.setForeground(Color.RED);

        nameLabel         = new JLabel("Name: -");
        balanceLabel      = new JLabel("Current Balance: -");
        balanceLimitLabel = new JLabel("Balance Limit: -");
        statusLabel       = new JLabel("Status: -");

        ordersTable = new CTable(new String[]{"Order ID", "Date", "Payment Type", "Amount Received (£)", "Total Cost (£)"});

        // ---- Search panel ----
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(accountIdLabel);
        searchPanel.add(accountIdField);
        searchPanel.add(searchButton);
        searchPanel.add(resultLabel);

        // ---- Info panel ----
        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Account Details"));
        infoPanel.add(nameLabel);
        infoPanel.add(balanceLabel);
        infoPanel.add(balanceLimitLabel);
        infoPanel.add(statusLabel);

        // ---- Button panel ----
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(backButton);

        // ---- Layout ----
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(searchPanel)
                .addComponent(infoPanel)
                .addComponent(ordersTable.getScrollPane())
                .addComponent(btnPanel)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(10)
                .addComponent(searchPanel, 40, 40, 40)
                .addComponent(infoPanel, 120, 120, 120)
                .addComponent(ordersTable.getScrollPane(), 250, 250, 250)
                .addComponent(btnPanel)
        );

        searchButton.addActionListener(e -> handleSearch());
        accountIdField.addActionListener(e -> handleSearch());
        backButton.addActionListener(e -> controller.goToHubScreen());
    }

    private void handleSearch() {
        String accountId = accountIdField.getText().trim();
        if (accountId.isBlank()) {
            resultLabel.setText("Please enter an Account ID.");
            return;
        }
        resultLabel.setText(" ");
        controller.queryOutstandingBalance(accountId);
    }

    /** Called by controller with the account data. */
    public void displayAccountDetails(AccountHolder account) {
        if (account == null) {
            resultLabel.setText("Account not found.");
            nameLabel.setText("Name: -");
            balanceLabel.setText("Current Balance: -");
            balanceLimitLabel.setText("Balance Limit: -");
            statusLabel.setText("Status: -");
            ordersTable.removeTableElements();
            return;
        }
        nameLabel.setText("Name: " + account.getName());
        balanceLabel.setText("Current Balance (owed): £" + String.format("%.2f", account.getBalance()));
        balanceLimitLabel.setText("Balance Limit: £" + account.getBalanceLimit());
        statusLabel.setText("Status: " + account.getStatus().toString());
    }

    /** Populates the orders table for this account. */
    public void populateOrdersTable(ArrayList<cust.model.Order> orders) {
        ordersTable.removeTableElements();
        if (orders.isEmpty()) {
            resultLabel.setText("No orders found for this account.");
            return;
        }
        for (cust.model.Order o : orders) {
            ordersTable.addRow(new String[]{
                o.getOrderID(),
                o.getOrderDate(),
                o.getPaymentType().toString(),
                o.isPaid() ? String.format("%.2f", o.getTotalCost()) : "Pending",
                String.format("%.2f", o.getTotalCost())
            });
        }
    }
}
