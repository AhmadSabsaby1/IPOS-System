package cust.view;

import cust.controller.CUSTController;
import custom.TitleLabel;

import javax.swing.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;

public class HubView extends JPanel {
    private CUSTController controller;

    //Swing Objects
    private JButton backButton;
    private TitleLabel titleLabel;
    private JButton accountButton;
    private JButton ordersButton;

    static public String cardId(){
        return "HubView";
    }

    public HubView(CUSTController controller){
        this.controller = controller;

        titleLabel = new TitleLabel("CUST - Manage Account Holders and Orders");
        accountButton = new JButton("Manage Account Holders");
        ordersButton = new JButton("Manage Orders");
        backButton = new JButton("Test");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(backButton)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(accountButton)
                        .addComponent(ordersButton)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(20)
                .addComponent(backButton)
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(accountButton, 50, 50, 50)
                        .addComponent(ordersButton, 50, 50, 50)
                )
        );

        accountButton.addActionListener(e->controller.goToAccountHolderManagerScreen());
        ordersButton.addActionListener(e->controller.goToOrderManagerScreen());

        backButton.addActionListener(e->test());
        backButton.setVisible(false);
    }

    private void test(){
        Month currentMonth = LocalDate.now().getMonth();
        Month lastMonth = currentMonth.minus(1);
        Month nextMonth = currentMonth.plus(1);

        int currentDay = LocalDate.now().getDayOfMonth();

        LocalDate t1 = LocalDate.parse("2026-01-01");
        LocalDate t2 = LocalDate.parse("2026-02-01");
        LocalDate t3 = LocalDate.parse("2026-03-01");
        LocalDate t4 = LocalDate.parse("2026-04-01");

        ArrayList<LocalDate> dates = new ArrayList<>();
        dates.add(t1);
        dates.add(t2);
        dates.add(t3);
        dates.add(t4);

        for (LocalDate d : dates){
            System.out.println(d.toString() + " is Current: " + (d.getMonth() == currentMonth) + " - is Last: " + (d.getMonth() == lastMonth));
        }
    }
}
