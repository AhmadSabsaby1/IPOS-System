package rpt.view;

import custom.CTable;
import custom.TitleLabel;
import rpt.controller.RPTController;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.util.ArrayList;

/**
 * BusinessReportView - UC4 (Generate Business Report) and UC29 (Print Report).
 */
public class BusinessReportView extends JPanel {

    private RPTController controller;

    private TitleLabel  titleLabel;
    private JButton     backButton;
    private JButton     printButton;
    private JTabbedPane tabbedPane;

    private CTable turnoverTable;
    private JLabel totalTurnoverLabel;

    private CTable stockTable;

    private CTable debtTable;
    private JLabel totalDebtLabel;

    public static String cardId() { return "BusinessReportView"; }

    public BusinessReportView(RPTController controller) {
        this.controller = controller;

        titleLabel  = new TitleLabel("Business Report (UC4)");
        backButton  = new JButton("Back");
        printButton = new JButton("Print Report (UC29)");

        turnoverTable      = new CTable(new String[]{"Order ID", "Payment Type", "Amount Received (£)", "Shipping Address"});
        totalTurnoverLabel = new JLabel("Total Turnover: £0.00");
        totalTurnoverLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

        JPanel turnoverPanel = new JPanel(new BorderLayout(5, 5));
        turnoverPanel.add(turnoverTable.getScrollPane(), BorderLayout.CENTER);
        turnoverPanel.add(totalTurnoverLabel, BorderLayout.SOUTH);

        stockTable = new CTable(new String[]{"Item ID", "Description", "Package Type", "Unit", "Availability (packs)", "Stock Limit"});
        JPanel stockPanel = new JPanel(new BorderLayout());
        stockPanel.add(stockTable.getScrollPane(), BorderLayout.CENTER);

        debtTable      = new CTable(new String[]{"Account ID", "Name", "Balance (£)", "Balance Limit (£)", "Status"});
        totalDebtLabel = new JLabel("Total Aggregated Debt: £0.00");
        totalDebtLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

        JPanel debtPanel = new JPanel(new BorderLayout(5, 5));
        debtPanel.add(debtTable.getScrollPane(), BorderLayout.CENTER);
        debtPanel.add(totalDebtLabel, BorderLayout.SOUTH);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Turnover / Sales", turnoverPanel);
        tabbedPane.addTab("Stock Availability", stockPanel);
        tabbedPane.addTab("Aggregated Debt", debtPanel);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(backButton);
        btnPanel.add(printButton);

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

    public void populate(ArrayList<String[]> turnoverRows, ArrayList<String[]> stockRows,
                         ArrayList<String[]> debtRows, double totalTurnover, double totalDebt) {
        turnoverTable.removeTableElements();
        for (String[] row : turnoverRows) turnoverTable.addRow(row);
        totalTurnoverLabel.setText("Total Turnover: £" + String.format("%.2f", totalTurnover));

        stockTable.removeTableElements();
        for (String[] row : stockRows) stockTable.addRow(row);

        debtTable.removeTableElements();
        for (String[] row : debtRows) debtTable.addRow(row);
        totalDebtLabel.setText("Total Aggregated Debt: £" + String.format("%.2f", totalDebt));
    }

    private void printReport() {
        int selectedTab = tabbedPane.getSelectedIndex();
        CTable tableToPrint;
        if (selectedTab == 0) {
            tableToPrint = turnoverTable;
        } else if (selectedTab == 1) {
            tableToPrint = stockTable;
        } else {
            tableToPrint = debtTable;
        }
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
