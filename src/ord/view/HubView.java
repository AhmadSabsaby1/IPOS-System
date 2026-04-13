package ord.view;

import custom.TitleLabel;
import ord.controller.ORDController;

import javax.swing.*;

public class HubView extends JPanel {
    private ORDController controller;

    //Swing Objects
    private TitleLabel titleLabel;
    private JButton goToLogoutButton;
    private JButton loginButton;
    private JButton backButton;
    private JButton goToCatalogueButton;
    private JButton goToPreviousOrdersButton;
    private JButton goToOrderProgressButton;

    //merchant details
    private JLabel companyNameLabel;
    private JLabel accountNumberLabel;
    private JLabel addressLabel;
    private JLabel contactPhoneLabel;
    private JLabel creditLimitLabel;
    private JLabel discountPlanTypeLabel;
    private JLabel fixedDiscountRateTypeLabel;


    static public String cardId(){
        return "HubView";
    }

    public HubView(ORDController controller) {
        this.controller = controller;

        titleLabel = new TitleLabel("IPOS-CA-ORD: Manage SA Orders");
        goToCatalogueButton = new JButton("Buy From Catalogue");
        goToPreviousOrdersButton = new JButton("See Previous Orders");
        goToOrderProgressButton = new JButton("See Order Progress");
        goToLogoutButton = new JButton("Logout");
        backButton = new JButton("Back to the Main Menu");
        loginButton = new JButton("Login");

        //merchant details
        companyNameLabel = new JLabel();
        accountNumberLabel = new JLabel();
        addressLabel = new JLabel();
        contactPhoneLabel = new JLabel();
        creditLimitLabel = new JLabel();
        discountPlanTypeLabel = new JLabel();
        fixedDiscountRateTypeLabel = new JLabel();

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(backButton)
                .addComponent(loginButton)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(goToCatalogueButton)
                        .addComponent(goToPreviousOrdersButton)
                        .addComponent(goToOrderProgressButton)
                )
                .addComponent(goToLogoutButton)
                .addComponent(companyNameLabel)
                .addComponent(accountNumberLabel)
                .addComponent(addressLabel)
                .addComponent(contactPhoneLabel)
                .addComponent(creditLimitLabel)
                .addComponent(discountPlanTypeLabel)
                .addComponent(fixedDiscountRateTypeLabel)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(40)
                .addComponent(backButton)
                .addGap(40)
                .addComponent(loginButton)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(goToCatalogueButton, 50, 50, 50)
                        .addComponent(goToPreviousOrdersButton, 50, 50, 50)
                        .addComponent(goToOrderProgressButton, 50, 50, 50)
                )
                .addGap(50)
                .addComponent(goToLogoutButton)
                .addComponent(goToLogoutButton)
                .addComponent(companyNameLabel)
                .addComponent(accountNumberLabel)
                .addComponent(addressLabel)
                .addComponent(contactPhoneLabel)
                .addComponent(creditLimitLabel)
                .addComponent(discountPlanTypeLabel)
                .addComponent(fixedDiscountRateTypeLabel)
        );

        goToCatalogueButton.addActionListener(e -> controller.goToCatalogueScreen());
        goToPreviousOrdersButton.addActionListener(e -> controller.goToPreviousOrdersScreen());
        goToOrderProgressButton.addActionListener(e -> controller.goToOrderProgressScreen());
        backButton.addActionListener(e->controller.goToMainMenu());
        loginButton.addActionListener(e->login());
        goToLogoutButton.addActionListener(e->logout());
    }

    private void login(){
        controller.goToLogin();
    }

    private void logout(){

    }
}
