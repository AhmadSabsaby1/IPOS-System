package ord.view;

import Api.ISAOrderAPI;
import Api.SessionManager;
import custom.JsonObject;
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

    private JLabel tier1DiscountLabel;
    private JLabel tier2DiscountLabel;
    private JLabel tier3DiscountLabel;

    private JLabel outstandingBalanceLabel;


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
        fixedDiscountRateTypeLabel.setVisible(false);

        tier1DiscountLabel = new JLabel();
        tier1DiscountLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        tier2DiscountLabel = new JLabel();
        tier2DiscountLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        tier3DiscountLabel = new JLabel();
        tier3DiscountLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        outstandingBalanceLabel = new JLabel();
        outstandingBalanceLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        checkLogin();

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
                .addComponent(outstandingBalanceLabel)
                .addComponent(discountPlanTypeLabel)
                .addComponent(fixedDiscountRateTypeLabel)
                .addComponent(tier1DiscountLabel)
                .addComponent(tier2DiscountLabel)
                .addComponent(tier3DiscountLabel)
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
                .addComponent(outstandingBalanceLabel)
                .addComponent(discountPlanTypeLabel)
                .addComponent(fixedDiscountRateTypeLabel)
                .addComponent(tier1DiscountLabel)
                .addComponent(tier2DiscountLabel)
                .addComponent(tier3DiscountLabel)
        );

        goToCatalogueButton.addActionListener(e -> controller.goToCatalogueScreen());
        goToPreviousOrdersButton.addActionListener(e -> controller.goToPreviousOrdersScreen());
        goToOrderProgressButton.addActionListener(e -> controller.goToOrderProgressScreen());
        backButton.addActionListener(e->controller.goToMainMenu());
        loginButton.addActionListener(e->login());
        logoutButton.addActionListener(e->logout());
    }

    private void fillMerchantDetails() {
        companyNameLabel.setText("Account Holder Name: " + SessionManager.company_name);
        accountNumberLabel.setText("Account Number: " + SessionManager.account_number);
        addressLabel.setText("Address: " + SessionManager.address);
        contactPhoneLabel.setText("Phone: " + SessionManager.contact_phone);
        creditLimitLabel.setText("Credit Limit: " + SessionManager.credit_limit);
        discountPlanTypeLabel.setText("Discount Plan: " + SessionManager.discount_plan_type);
        //fixedDiscountRateTypeLabel.setText("Fixed Discount: " + SessionManager.fixed_discount_rate);

        JsonObject o = JsonObject.parse(controller.queryBalance());
        if (o != null)
            outstandingBalanceLabel.setText("Outstanding Balance: " + o.get("outstanding_balance"));
        else
            outstandingBalanceLabel.setText("Outstanding Balance: 0.0");

        //tier1DiscountLabel.setText("Discount for < £" + SessionManager.tier_1_threshold + " is " + SessionManager.tier_1_discount + "%");
        tier1DiscountLabel.setText("Discount for < £1000 is 0%");
        //tier2DiscountLabel.setText("Discount between £" + SessionManager.tier_1_threshold + " - £" + SessionManager.tier_2_threshold + " is " + SessionManager.tier_2_discount + "%");
        tier2DiscountLabel.setText("Discount between £1000 - £2000 is 1%");
        //tier3DiscountLabel.setText("Discount of £" + SessionManager.tier_2_threshold + "+" + " is " + SessionManager.tier_3_discount + "%");
        tier3DiscountLabel.setText("Discount of £2000+ is 2%");
    }

    private void removeMerchantDetails() {
        companyNameLabel.setText("");
        accountNumberLabel.setText("");
        addressLabel.setText("");
        contactPhoneLabel.setText("");
        creditLimitLabel.setText("");
        outstandingBalanceLabel.setText("");
        discountPlanTypeLabel.setText("");
        //fixedDiscountRateTypeLabel.setText("");
        tier1DiscountLabel.setText("");
        tier2DiscountLabel.setText("");
        tier3DiscountLabel.setText("");
    }

    private void login(){
        controller.goToLogin();
    }

    private void logout(){
        SessionManager.merchant_Id = "";
        checkLogin();
        removeMerchantDetails();
    }

    public void checkLogin() {
        if (SessionManager.merchant_Id.isEmpty()){
            //no merchant logged in
            loginButton.setVisible(true);
            removeMerchantDetails();

            goToOrderProgressButton.setVisible(false);
            goToCatalogueButton.setVisible(false);
            goToPreviousOrdersButton.setVisible(false);
            logoutButton.setVisible(false);
        }else{
            //merchant logged in
            logoutButton.setVisible(true);
            goToOrderProgressButton.setVisible(true);
            goToCatalogueButton.setVisible(true);
            goToPreviousOrdersButton.setVisible(true);
            fillMerchantDetails();

            loginButton.setVisible(false);
        }
    }
}
