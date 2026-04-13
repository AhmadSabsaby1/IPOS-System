package ord.view;

import Api.SessionManager;
import custom.TitleLabel;
import ord.controller.ORDController;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MerchantLoginView extends JPanel {
    private ORDController controller;
    private JButton loginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel infoLabel;


    public static String cardId() {
        return "MerchantLoginView";
    }
    public MerchantLoginView(ORDController controller) {
        this.controller = controller;

        setLayout(null);

        TitleLabel titleLabel = new TitleLabel("Merchant Login");
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        loginButton = new JButton("Login");
        infoLabel = new JLabel();

        JPanel mainPanel = new JPanel();
        mainPanel.setSize(380, 150);
        add(mainPanel);

        mainPanel.setLayout(new BorderLayout(5, 5));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel loginPanel = new JPanel();
        loginPanel.setBorder(BorderFactory.createEtchedBorder());
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        loginPanel.setLayout(new GridLayout(3, 2, 5, 5));
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(infoLabel);
        mainPanel.add(loginButton, BorderLayout.SOUTH);

        loginButton.addActionListener(e->login());
    }

    private void login() {
        //TODO do the actual login
        if (controller.merchantLogin(usernameField.getText(), new String(passwordField.getPassword()))){
            SessionManager.merchant_Id = "someMerchantID";
            controller.goToHubScreen();
        }else{
            infoLabel.setText("Incorrect login");
        }
    }
}
