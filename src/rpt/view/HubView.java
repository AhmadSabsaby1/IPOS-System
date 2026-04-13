package rpt.view;

import custom.TitleLabel;
import rpt.controller.RPTController;

import javax.swing.*;

/**
 * HubView – main menu for the RPT package.
 * Gives the manager access to all report functions.
 */
public class HubView extends JPanel {

    private RPTController controller;

    private TitleLabel titleLabel;
    private JButton backButton;
    private JButton businessReportButton;
    private JButton outstandingBalanceButton;
    private JButton monthlyStatementsButton;

    public static String cardId() { return "RPTHubView"; }

    public HubView(RPTController controller) {
        this.controller = controller;

        titleLabel             = new TitleLabel("RPT - Reports");
        businessReportButton   = new JButton("Generate Business Report (UC4)");
        outstandingBalanceButton = new JButton("Query Outstanding Balance (UC23)");
        monthlyStatementsButton  = new JButton("Generate Monthly Statements (UC24)");
        backButton = new JButton("Back to Main Menu");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(backButton)
                .addComponent(businessReportButton, 300, 300, 300)
                .addComponent(outstandingBalanceButton, 300, 300, 300)
                .addComponent(monthlyStatementsButton, 300, 300, 300)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(40)
                .addComponent(backButton)
                .addGap(30)
                .addComponent(businessReportButton, 50, 50, 50)
                .addGap(10)
                .addComponent(outstandingBalanceButton, 50, 50, 50)
                .addGap(10)
                .addComponent(monthlyStatementsButton, 50, 50, 50)
        );

        businessReportButton.addActionListener(e -> controller.goToBusinessReportScreen());
        outstandingBalanceButton.addActionListener(e -> controller.goToOutstandingBalanceScreen());
        monthlyStatementsButton.addActionListener(e -> controller.goToMonthlyStatementsScreen());
        backButton.addActionListener(e->controller.goToMainMenu());
    }
}
