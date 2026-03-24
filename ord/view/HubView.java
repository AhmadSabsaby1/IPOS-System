package ord.view;

import ord.controller.ORDController;

import javax.swing.*;

public class HubView extends JPanel {
    private ORDController controller;
    private JButton goToLogoutButton;
    private JButton goToCatalogueButton;
    private JButton goToPreviousOrdersButton;
    private JButton goToOrderProgressButton;

    static public String cardId(){
        return "HubView";
    }

    public HubView(ORDController controller) {
        this.controller = controller;

        goToCatalogueButton = new JButton("See the Catalogue");
        add(goToCatalogueButton);

        goToPreviousOrdersButton = new JButton("See Previous Orders");
        add(goToPreviousOrdersButton);

        goToOrderProgressButton = new JButton("See Order Progress");
        add(goToOrderProgressButton);

        goToLogoutButton = new JButton("Logout");
        add(goToLogoutButton);

        goToCatalogueButton.addActionListener(e -> controller.goToCatalogueScreen());
        goToPreviousOrdersButton.addActionListener(e -> controller.goToPreviousOrdersScreen());
        goToOrderProgressButton.addActionListener(e -> controller.goToOrderProgressScreen());
        //goToLogoutButton.addActionListener(e -> controller.goTo???());
    }


}
