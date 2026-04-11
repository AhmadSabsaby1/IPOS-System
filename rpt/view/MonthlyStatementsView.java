package rpt.view;

import cust.model.AccountHolder;
import cust.model.Order;
import cust.model.OrderItem;
import custom.CTable;
import custom.TitleLabel;
import rpt.controller.RPTController;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.util.ArrayList;

/**
 * MonthlyStatementsView – UC24 (Generate Monthly Statements).
 * Produces a monthly financial statement for a selected account holder.
 */
public class MonthlyStatementsView extends JPanel {

    private RPTController controller;

    private TitleLabel titleLabel;
    private JButton backButton;
    private JButton generateButton;
    private JButton printButton;

    // Account selector
    private JLabel     accountLabel;
    private JComboBox<String> accountCombo;

    // Statement area
    private JLabel nameLabel;
    private JLabel balanceLabel;
    private JLabel periodLabel;
    private CTable ordersTable;
    private JLabel totalLabel;

    private ArrayList<AccountHolder> accountHolders;

    public static String cardId() { return "MonthlyStatementsView"; }

    public MonthlyStatementsView(RPTController controller) {
        this.controller = controller;

        titleLabel     = new TitleLabel("Monthly Statements (UC24)");
        backButton     = new JButton("Back");
        generateButton = new JButton("Generate Statement");
        printButton    = new JButton("Print");
        printButton.setEnabled(false);

        accountLabel = new JLabel("Select Account Holder:");
        accountCombo = new JComboBox<>();

        nameLabel    = new JLabel("Account Holder: -");
        balanceLabel = new JLabel("Outstanding Balance: -");
        periodLabel  = new JLabel("Period: -");
        totalLabel   = new JLabel("Total Spend This Month: £0.00");
        totalLabel.setFont(new Font("Tahoma", Font.BOLD, 13));

        ordersTable = new CTable(new String[]{"Order ID", "Date", "Payment Type", "Total Cost (£)"});

        // ---- Selector panel ----
        JPanel selectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectorPanel.add(accountLabel);
        selectorPanel.add(accountCombo);
        selectorPanel.add(generateButton);

        // ---- Info panel ----
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Statement Details"));
        infoPanel.add(nameLabel);
        infoPanel.add(balanceLabel);
        infoPanel.add(periodLabel);

        // ---- Button panel ----
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(backButton);
        btnPanel.add(printButton);

        // ---- Layout ----
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
                .addComponent(infoPanel, 100, 100, 100)
                .addComponent(ordersTable.getScrollPane(), 280, 280, 280)
                .addComponent(totalLabel)
                .addComponent(btnPanel)
        );

        generateButton.addActionListener(e -> handleGenerate());
        printButton.addActionListener(e -> handlePrint());
        backButton.addActionListener(e -> controller.goToHubScreen());
    }

    /** Populates the account dropdown. */
    public void populateAccounts(ArrayList<AccountHolder> accounts) {
        this.accountHolders = accounts;
        accountCombo.removeAllItems();
        for (AccountHolder a : accounts) {
            accountCombo.addItem(a.getAccountId() + " – " + a.getName());
        }
    }

    private void handleGenerate() {
        int idx = accountCombo.getSelectedIndex();
        if (idx < 0 || accountHolders == null || accountHolders.isEmpty()) return;
        AccountHolder selected = accountHolders.get(idx);
        controller.generateMonthlyStatement(selected.getAccountId());
    }

    /** Called by controller with statement data. */
    public void displayStatement(AccountHolder account, ArrayList<Order> orders, double totalSpend) {
        nameLabel.setText("Account Holder: " + account.getName() + " (" + account.getAccountId() + ")");
        balanceLabel.setText("Outstanding Balance: £" + String.format("%.2f", account.getBalance()));

        java.time.LocalDate now = java.time.LocalDate.now();
        java.time.LocalDate firstOfMonth = now.withDayOfMonth(1);
        periodLabel.setText("Period: " + firstOfMonth + " to " + now);

        ordersTable.removeTableElements();
        for (Order o : orders) {
            ordersTable.addRow(new String[]{
                o.getOrderID(),
                o.getOrderDate(),
                o.getPaymentType().toString(),
                String.format("%.2f", o.getTotalCost())
            });
        }

        totalLabel.setText("Total Spend This Month: £" + String.format("%.2f", totalSpend));
        printButton.setEnabled(true);

        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No orders found for this account this month.");
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
