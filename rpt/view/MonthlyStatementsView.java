package rpt.view;

import custom.CTable;
import custom.TitleLabel;
import rpt.controller.RPTController;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.util.ArrayList;

/**
 * MonthlyStatementsView - UC24 (Generate Monthly Statements).
 */
public class MonthlyStatementsView extends JPanel {

    private RPTController controller;

    private TitleLabel titleLabel;
    private JButton    backButton;
    private JButton    generateButton;
    private JButton    printButton;

    private JComboBox<String> accountCombo;
    private JLabel nameLabel;
    private JLabel balanceLabel;

    private CTable ordersTable;
    private JLabel totalLabel;

    private ArrayList<String[]> accountList;

    public static String cardId() { return "MonthlyStatementsView"; }

    public MonthlyStatementsView(RPTController controller) {
        this.controller = controller;

        titleLabel     = new TitleLabel("Monthly Statements (UC24)");
        backButton     = new JButton("Back");
        generateButton = new JButton("Generate Statement");
        printButton    = new JButton("Print");
        printButton.setEnabled(false);

        accountCombo = new JComboBox<>();
        nameLabel    = new JLabel("Account Holder: -");
        balanceLabel = new JLabel("Outstanding Balance: -");
        totalLabel   = new JLabel("Total Spend: £0.00");
        totalLabel.setFont(new Font("Tahoma", Font.BOLD, 13));

        ordersTable = new CTable(new String[]{"Order ID", "Payment Type", "Amount Received (£)", "Shipping Address"});

        JPanel selectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectorPanel.add(new JLabel("Account:"));
        selectorPanel.add(accountCombo);
        selectorPanel.add(generateButton);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Statement Details"));
        infoPanel.add(nameLabel);
        infoPanel.add(balanceLabel);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(backButton);
        btnPanel.add(printButton);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(selectorPanel)
                .addComponent(infoPanel)
                .addComponent(ordersTable.getScrollPane())
                .addComponent(totalLabel)
                .addComponent(btnPanel)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(10)
                .addComponent(selectorPanel, 40, 40, 40)
                .addComponent(infoPanel, 80, 80, 80)
                .addComponent(ordersTable.getScrollPane(), 300, 300, 300)
                .addComponent(totalLabel)
                .addComponent(btnPanel)
        );

        generateButton.addActionListener(e -> handleGenerate());
        printButton.addActionListener(e -> handlePrint());
        backButton.addActionListener(e -> controller.goToHubScreen());
    }

    // accountList items: {accountID, name}
    public void populateAccounts(ArrayList<String[]> accounts) {
        this.accountList = accounts;
        accountCombo.removeAllItems();
        for (String[] a : accounts) accountCombo.addItem(a[0] + " - " + a[1]);
    }

    private void handleGenerate() {
        int idx = accountCombo.getSelectedIndex();
        if (idx < 0 || accountList == null || accountList.isEmpty()) return;
        String accountId = accountList.get(idx)[0];
        controller.generateMonthlyStatement(accountId);
    }

    // details: {accountID, name, balance, balanceLimit, status}
    public void displayStatement(String[] details, ArrayList<String[]> orders, double totalSpend) {
        nameLabel.setText("Account Holder: " + details[1] + " (" + details[0] + ")");
        balanceLabel.setText("Outstanding Balance: £" + details[2]);

        ordersTable.removeTableElements();
        for (String[] row : orders) ordersTable.addRow(row);

        totalLabel.setText("Total: £" + String.format("%.2f", totalSpend));
        printButton.setEnabled(true);

        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No orders found for this account.");
        }
    }

    private void handlePrint() {
        try {
            boolean printed = ordersTable.print();
            if (printed) {
                JOptionPane.showMessageDialog(this, "Statement printed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Print cancelled.");
            }
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(this, "Printer not available: " + e.getMessage(),
                    "Print Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
