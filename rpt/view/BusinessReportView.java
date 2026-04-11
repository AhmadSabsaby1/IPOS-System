package rpt.view;

import cust.model.AccountHolder;
import cust.model.LocalItem;
import cust.model.Order;
import custom.CTable;
import custom.TitleLabel;
import rpt.controller.RPTController;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.util.ArrayList;

/**
 * BusinessReportView – UC4 (Generate Business Report) and UC29 (Print Report).
 * Shows three tabs: Turnover (sales), Stock Availability, and Aggregated Debt.
 */
public class BusinessReportView extends JPanel {

    private RPTController controller;

    private TitleLabel titleLabel;
    private JButton backButton;
    private JButton printButton;
    private JTabbedPane tabbedPane;

    // Turnover tab
    private CTable turnoverTable;
    private JLabel totalTurnoverLabel;

    // Stock tab
    private CTable stockTable;

    // Debt tab
    private CTable debtTable;
    private JLabel totalDebtLabel;

    public static String cardId() { return "BusinessReportView"; }

    public BusinessReportView(RPTController controller) {
        this.controller = controller;

        titleLabel  = new TitleLabel("Business Report");
        backButton  = new JButton("Back");
        printButton = new JButton("Print Report (UC29)");

        // ---- Turnover tab ----
        turnoverTable = new CTable(new String[]{"Order ID", "Date", "Payment Type", "Total Cost (£)"});
        totalTurnoverLabel = new JLabel("Total Turnover: £0.00");
        totalTurnoverLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

        JPanel turnoverPanel = new JPanel(new BorderLayout(5, 5));
        turnoverPanel.add(turnoverTable.getScrollPane(), BorderLayout.CENTER);
        turnoverPanel.add(totalTurnoverLabel, BorderLayout.SOUTH);

        // ---- Stock tab ----
        stockTable = new CTable(new String[]{"Item ID", "Description", "Package Type", "Unit", "Availability (packs)", "Stock Limit"});

        JPanel stockPanel = new JPanel(new BorderLayout());
        stockPanel.add(stockTable.getScrollPane(), BorderLayout.CENTER);

        // ---- Debt tab ----
        debtTable = new CTable(new String[]{"Account ID", "Name", "Balance (£)", "Balance Limit (£)", "Status"});
        totalDebtLabel = new JLabel("Total Aggregated Debt: £0.00");
        totalDebtLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

        JPanel debtPanel = new JPanel(new BorderLayout(5, 5));
        debtPanel.add(debtTable.getScrollPane(), BorderLayout.CENTER);
        debtPanel.add(totalDebtLabel, BorderLayout.SOUTH);

        // ---- Tabs ----
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Turnover / Sales", turnoverPanel);
        tabbedPane.addTab("Stock Availability", stockPanel);
        tabbedPane.addTab("Aggregated Debt", debtPanel);

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
                .addComponent(tabbedPane)
                .addComponent(btnPanel)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(10)
                .addComponent(tabbedPane, 450, 450, 450)
                .addComponent(btnPanel)
        );

        backButton.addActionListener(e -> controller.goToHubScreen());
        printButton.addActionListener(e -> printReport());
    }

    /** Populates all three tabs with data. */
    public void populate(ArrayList<Order> orders, ArrayList<LocalItem> stock,
                         ArrayList<AccountHolder> accounts, double totalTurnover, double totalDebt) {
        // Turnover
        turnoverTable.removeTableElements();
        for (Order o : orders) {
            turnoverTable.addRow(new String[]{
                o.getOrderID(),
                o.getOrderDate(),
                o.getPaymentType().toString(),
                String.format("%.2f", o.getTotalCost())
            });
        }
        totalTurnoverLabel.setText("Total Turnover: £" + String.format("%.2f", totalTurnover));

        // Stock
        stockTable.removeTableElements();
        for (LocalItem item : stock) {
            stockTable.addRow(new String[]{
                item.getId(),
                item.getDescription(),
                item.getType(),
                item.getUnit(),
                String.valueOf(item.getAvailability()),
                String.valueOf(item.getLimit())
            });
        }

        // Debt
        debtTable.removeTableElements();
        for (AccountHolder a : accounts) {
            debtTable.addRow(new String[]{
                a.getAccountId(),
                a.getName(),
                String.format("%.2f", a.getBalance()),
                String.valueOf(a.getBalanceLimit()),
                a.getStatus().toString()
            });
        }
        totalDebtLabel.setText("Total Aggregated Debt: £" + String.format("%.2f", totalDebt));
    }

    /** UC29 – Print the currently visible tab. */
    private void printReport() {
        int selectedTab = tabbedPane.getSelectedIndex();
        CTable tableToPrint = switch (selectedTab) {
            case 0 -> turnoverTable;
            case 1 -> stockTable;
            default -> debtTable;
        };
        try {
            boolean printed = tableToPrint.print();
            if (printed) {
                JOptionPane.showMessageDialog(this, "Report printed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Print cancelled.");
            }
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(this, "Printer not available: " + e.getMessage(),
                    "Print Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
