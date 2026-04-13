package ord.view;

import Api.SessionManager;
import custom.TitleLabel;
import ord.controller.ORDController;
import users.model.Session;

import javax.swing.*;
import java.awt.*;

public class HubView extends JPanel {
    private ORDController controller;

    //Swing Objects
    private TitleLabel titleLabel;
    private JButton logoutButton;
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
        logoutButton = new JButton("Logout");
        backButton = new JButton("Back to the Main Menu");
        loginButton = new JButton("Login");

        //merchant details
        companyNameLabel = new JLabel();
        companyNameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        accountNumberLabel = new JLabel();
        accountNumberLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        addressLabel = new JLabel();
        addressLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        contactPhoneLabel = new JLabel();
        contactPhoneLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        creditLimitLabel = new JLabel();
        creditLimitLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        discountPlanTypeLabel = new JLabel();
        discountPlanTypeLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        fixedDiscountRateTypeLabel = new JLabel();
        fixedDiscountRateTypeLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        if (SessionManager.merchant_Id.isEmpty()){
            //no merchant logged in
            loginButton.setVisible(true);

            logoutButton.setVisible(false);
        }else{
            //merchant logged in
            logoutButton.setVisible(true);
            fillMerchantDetails();
            loginButton.setVisible(false);
        }

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
                .addComponent(logoutButton)
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
                .addComponent(logoutButton)
                .addComponent(logoutButton)
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
        logoutButton.addActionListener(e->logout());
    }

    private void fillMerchantDetails() {
        companyNameLabel.setText("Account Holder Name: ");
        accountNumberLabel.setText("Account Number: ");
        addressLabel.setText("Address: ");
        contactPhoneLabel.setText("Phone: ");
        creditLimitLabel.setText("Credit Limit: ");
        discountPlanTypeLabel.setText("Discount Plan:");
        fixedDiscountRateTypeLabel.setText("Fixed Discount: ");
    }

    private void login(){
        controller.goToLogin();
    }

    private void logout(){
        //TODO actual logout
        SessionManager.merchant_Id = "";

    }
}
