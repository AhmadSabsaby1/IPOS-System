package ord.view;

import custom.TitleLabel;
import ord.controller.ORDController;

import javax.swing.*;

public class HubView extends JPanel {
    private ORDController controller;

    //Swing Objects
    private TitleLabel titleLabel;
    private JButton goToLogoutButton;
    private JButton goToCatalogueButton;
    private JButton goToPreviousOrdersButton;
    private JButton goToOrderProgressButton;

    static public String cardId(){
        return "HubView";
    }

    public HubView(ORDController controller) {
        this.controller = controller;

        titleLabel = new TitleLabel("Manage SA Orders");
        goToCatalogueButton = new JButton("See the Catalogue");
        goToPreviousOrdersButton = new JButton("See Previous Orders");
        goToOrderProgressButton = new JButton("See Order Progress");
        goToLogoutButton = new JButton("Logout");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(goToCatalogueButton)
                        .addComponent(goToPreviousOrdersButton)
                        .addComponent(goToOrderProgressButton)
                )
                .addComponent(goToLogoutButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(40)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(goToCatalogueButton, 50, 50, 50)
                        .addComponent(goToPreviousOrdersButton, 50, 50, 50)
                        .addComponent(goToOrderProgressButton, 50, 50, 50)
                )
                .addGap(50)
                .addComponent(goToLogoutButton)
        );

        goToCatalogueButton.addActionListener(e -> controller.goToCatalogueScreen());
        goToPreviousOrdersButton.addActionListener(e -> controller.goToPreviousOrdersScreen());
        goToOrderProgressButton.addActionListener(e -> controller.goToOrderProgressScreen());
    }
}
