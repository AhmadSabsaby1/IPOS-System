package main;

import custom.TitleLabel;
import users.model.Session;
import users.model.UserRole;

import javax.swing.*;

public class MainMenuView extends JPanel {
    private Main main;

    //Swing Objects
    private JButton goToCustButton;
    private JButton goToOrdButton;
    private JButton goToLoguinButton;
    private JButton goToUserManagementButton;

    public static String cardId(){
        return "MainMenuView";
    }
    public MainMenuView(Main main) {
        this.main = main;

        TitleLabel titleLabel = new TitleLabel("IPOS-CA: Main Menu");
        goToCustButton = new JButton("Manage Account Holders and Orders");
        goToOrdButton = new JButton("Order from IPOS-SA");
        goToLoguinButton = new JButton("Log In");
        goToUserManagementButton = new JButton("Manage Users");

        checkPerms();

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(goToLoguinButton)
                .addComponent(goToUserManagementButton)
                .addComponent(goToCustButton)
                .addComponent(goToOrdButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(40)
                .addComponent(goToLoguinButton, 50, 50, 50)
                .addComponent(goToUserManagementButton, 50, 50, 50)
                .addComponent(goToCustButton, 50, 50, 50)
                .addComponent(goToOrdButton, 50, 50, 50)
        );

        goToCustButton.addActionListener(e->main.goToCust());
        goToOrdButton.addActionListener(e->main.goToOrd());
        goToLoguinButton.addActionListener(e->main.goToUsers());
        goToUserManagementButton.addActionListener(e->main.goToUserManagement());
    }

    public void checkPerms(){
        goToLoguinButton.setVisible(false);
        UserRole role = Global.get().getUserRole();

        if (role == null){
            goToLoguinButton.setVisible(true);

            goToUserManagementButton.setVisible(false);
            goToOrdButton.setVisible(false);
            goToCustButton.setVisible(false);
        } else if (role == UserRole.ADMIN){
            goToUserManagementButton.setVisible(true);
            goToOrdButton.setVisible(true);
            goToCustButton.setVisible(true);
        } else if (role == UserRole.PHARMACIST) {
            goToUserManagementButton.setVisible(false);

            goToOrdButton.setVisible(true);
            goToCustButton.setVisible(true);
        } else if (role == UserRole.MANAGER) {
            goToUserManagementButton.setVisible(false);

            goToOrdButton.setVisible(true);
            goToCustButton.setVisible(true);
        }
    }
}
