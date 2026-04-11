package cust.view;

import cust.controller.CUSTController;
import custom.TitleLabel;

import javax.swing.*;

public class OrderManagerView extends JPanel {
    private CUSTController controller;

    //Swing Object
    private TitleLabel titleLabel;
    private JButton backButton;
    private JButton manageAccountOrdersButton;
    private JButton createOrderButton;
    private JButton seeAllOrdersButton;

    static public String cardId(){
        return "OrderManagerView";
    }

    public OrderManagerView(CUSTController controller){
        this.controller = controller;

        titleLabel = new TitleLabel("Order Manager");
        backButton = new JButton("Back to Main Menu");
        manageAccountOrdersButton = new JButton("Manage Account Holder's Orders");
        seeAllOrdersButton = new JButton("See All Orders");
        createOrderButton = new JButton("Create an Order");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(backButton)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(seeAllOrdersButton)
                        .addComponent(manageAccountOrdersButton)
                        .addComponent(createOrderButton)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(40)
                .addComponent(backButton)
                .addGap(40)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(seeAllOrdersButton, 50, 50, 50)
                        .addComponent(manageAccountOrdersButton, 50, 50, 50)
                        .addComponent(createOrderButton, 50, 50, 50)
                )
        );

        backButton.addActionListener(e -> controller.goToHubScreen());
        manageAccountOrdersButton.addActionListener(e -> controller.goToManageAccountOrdersScreen());
        createOrderButton.addActionListener(e->controller.goToCreateOrderScreen());
        seeAllOrdersButton.addActionListener(e->controller.goToSeeAllOrdersScreen());
    }
}
