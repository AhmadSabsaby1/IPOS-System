package ord.view;

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

        JPanel mainPanel = new JPanel();
        mainPanel.setSize(380, 150);
        add(mainPanel);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel loginPanel = new JPanel();
        loginPanel.setBorder(BorderFactory.createEtchedBorder());
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        loginPanel.setLayout(new GridLayout(2, 2));
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        mainPanel.add(loginButton, BorderLayout.SOUTH);

        loginButton.addActionListener(e->login());
    }

    private void login() {
        //TODO do the actual login
        controller.merchantLogin(usernameField.getText(), Arrays.toString(passwordField.getPassword()));
        controller.goToHubScreen();
    }
}
