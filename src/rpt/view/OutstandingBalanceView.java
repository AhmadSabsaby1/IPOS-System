package rpt.view;

import custom.CTable;
import custom.TitleLabel;
import rpt.controller.RPTController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * OutstandingBalanceView - UC23 (Query Outstanding Balance).
 */
public class OutstandingBalanceView extends JPanel {

    private RPTController controller;

    private TitleLabel titleLabel;
    private JButton    backButton;
    private JButton    searchButton;
    private JTextField accountIdField;
    private JLabel     resultLabel;

    private JLabel nameLabel;
    private JLabel balanceLabel;
    private JLabel balanceLimitLabel;
    private JLabel statusLabel;

    private CTable ordersTable;

    public static String cardId() { return "OutstandingBalanceView"; }

    public OutstandingBalanceView(RPTController controller) {
        this.controller = controller;

        titleLabel    = new TitleLabel("Query Outstanding Balance (UC23)");
        backButton    = new JButton("Back");
        searchButton  = new JButton("Search");
        accountIdField = new JTextField(10);
        resultLabel   = new JLabel(" ");
        resultLabel.setForeground(Color.RED);

        nameLabel         = new JLabel("Name: -");
        balanceLabel      = new JLabel("Current Balance: -");
        balanceLimitLabel = new JLabel("Balance Limit: -");
        statusLabel       = new JLabel("Status: -");

        ordersTable = new CTable(new String[]{"Order ID", "Payment Type", "Amount Received (£)", "Shipping Address"});

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Account ID:"));
        searchPanel.add(accountIdField);
        searchPanel.add(searchButton);
        searchPanel.add(resultLabel);

        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Account Details"));
        infoPanel.add(nameLabel);
        infoPanel.add(balanceLabel);
        infoPanel.add(balanceLimitLabel);
        infoPanel.add(statusLabel);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(backButton);

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
        if (accountId.isEmpty()) {
            resultLabel.setText("Please enter an Account ID.");
            return;
        }
        resultLabel.setText(" ");
        controller.queryOutstandingBalance(accountId);
    }

    // details: {accountID, name, balance, balanceLimit, status}
    public void displayAccountDetails(String[] details) {
        if (details == null) {
            resultLabel.setText("Account not found.");
            nameLabel.setText("Name: -");
            balanceLabel.setText("Current Balance: -");
            balanceLimitLabel.setText("Balance Limit: -");
            statusLabel.setText("Status: -");
            ordersTable.removeTableElements();
            return;
        }
        nameLabel.setText("Name: " + details[1]);
        balanceLabel.setText("Current Balance (owed): £" + details[2]);
        balanceLimitLabel.setText("Balance Limit: £" + details[3]);
        statusLabel.setText("Status: " + details[4]);
    }

    public void populateOrdersTable(ArrayList<String[]> orders) {
        ordersTable.removeTableElements();
        if (orders.isEmpty()) {
            resultLabel.setText("No orders found for this account.");
            return;
        }
        for (String[] row : orders) ordersTable.addRow(row);
    }
}
