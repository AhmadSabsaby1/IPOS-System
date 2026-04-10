package cust.view;

import cust.controller.CUSTController;
import custom.TitleLabel;

import javax.swing.*;

public class OrderManagerView extends JPanel {
    private CUSTController controller;

    //Swing Object
    private TitleLabel titleLabel;
    private JButton backButton;
    private JButton seeOrdersButton;
    private JButton createOrderButton;

    static public String cardId(){
        return "OrderManagerView";
    }

    public OrderManagerView(CUSTController controller){
        this.controller = controller;

        titleLabel = new TitleLabel("Order Manager");
        backButton = new JButton("Back to Main Menu");
        seeOrdersButton = new JButton("See Orders");
        createOrderButton = new JButton("Create Order");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(backButton)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(seeOrdersButton)
                        .addComponent(createOrderButton)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(40)
                .addComponent(backButton)
                .addGap(40)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(seeOrdersButton, 50, 50, 50)
                        .addComponent(createOrderButton, 50, 50, 50)
                )
        );

        backButton.addActionListener(e -> controller.goToHubScreen());
        seeOrdersButton.addActionListener(e -> controller.goToSeeOrdersScreen());
        createOrderButton.addActionListener(e->controller.goToCreateOrderScreen());
    }
}
