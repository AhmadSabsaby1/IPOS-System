package cust.view;

import cust.controller.CUSTController;
import custom.TitleLabel;

import javax.swing.*;

public class HubView extends JPanel {
    private CUSTController controller;

    //Swing Objects
    private TitleLabel titleLabel;
    private JButton accountButton;
    private JButton ordersButton;
    private JButton templatesButton;

    static public String cardId(){
        return "HubView";
    }

    public HubView(CUSTController controller){
        this.controller = controller;

        titleLabel = new TitleLabel("CUST - Manage Account Holders and Orders");
        accountButton = new JButton("Manage Account Holders");
        ordersButton = new JButton("Manage Orders");
        templatesButton = new JButton("Manage Templates");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(accountButton)
                        .addComponent(ordersButton)
                        .addComponent(templatesButton)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(40)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(accountButton, 50, 50, 50)
                        .addComponent(ordersButton, 50, 50, 50)
                        .addComponent(templatesButton, 50, 50, 50)
                )
        );

        accountButton.addActionListener(e->controller.goToAccountHolderManagerScreen());
        ordersButton.addActionListener(e->controller.goToOrderManagerScreen());
        templatesButton.addActionListener(e->controller.goToTemplateManagerScreen());
    }
}
