package main;

import Api.ISAOrderAPI;
import Api.ISAOrder_Implementation;
import custom.JsonObject;
import custom.TitleLabel;
import ord.model.Item;
import users.model.UserRole;

import javax.swing.*;
import java.util.ArrayList;

public class MainMenuView extends JPanel {
    private Main main;

    //Swing Objects
    private JButton goToCustButton;
    private JButton goToOrdButton;
    private JButton goToLoguinButton;
    private JButton goToUserManagementButton;
    private JButton goToRPTButton;
    private JButton goToTemplatesButton;

    public static String cardId(){
        return "MainMenuView";
    }
    public MainMenuView(Main main) {
        this.main = main;

        JButton testButton = new JButton("Test");

        TitleLabel titleLabel = new TitleLabel("IPOS-CA: Main Menu");
        goToCustButton = new JButton("Manage Account Holders and Orders");
        goToOrdButton = new JButton("Order Stock from IPOS-SA");
        goToLoguinButton = new JButton("Log In");
        goToUserManagementButton = new JButton("Manage Users");
        goToRPTButton = new JButton("Generate Reports");
        goToTemplatesButton = new JButton("Manage Templates");

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
                .addComponent(goToRPTButton)
                .addComponent(goToTemplatesButton)
                .addComponent(testButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(40)
                .addComponent(goToLoguinButton, 75, 75, 75)
                .addComponent(goToUserManagementButton, 50, 50, 50)
                .addComponent(goToCustButton, 50, 50, 50)
                .addComponent(goToOrdButton, 50, 50, 50)
                .addComponent(goToRPTButton, 50, 50, 50)
                .addComponent(goToTemplatesButton, 50, 50, 50)
                .addComponent(testButton)
        );

        goToCustButton.addActionListener(e->main.goToCust());
        goToOrdButton.addActionListener(e->main.goToOrd());
        goToLoguinButton.addActionListener(e->main.goToUsers());
        goToUserManagementButton.addActionListener(e->main.goToUserManagement());
        goToRPTButton.addActionListener(e->main.goToRPT());
        goToTemplatesButton.addActionListener(e->main.goToTemplates());

        testButton.addActionListener(e->test());
    }

    private void test(){
        String[] a = {"{\"id\": \"10000001\", \"description\": \"Paracetamol\", \"items\":[{\"id\":\"10001\"}, {\"id\":\"10001\"}, {\"id\":\"10001\"}]}"};
        for (String s : a) {
            JsonObject o = JsonObject.parse(s);
            String[] i = JsonObject.parseArray(o.get("items"));
        }
    }

    public void checkPerms(){
        goToLoguinButton.setVisible(false);
        UserRole role = Global.get().getUserRole();

        if (role == null){
            goToLoguinButton.setVisible(true);

            goToUserManagementButton.setVisible(false);
            goToOrdButton.setVisible(false);
            goToCustButton.setVisible(false);
            goToRPTButton.setVisible(false);
            goToTemplatesButton.setVisible(false);
        } else if (role == UserRole.ADMIN){
            goToUserManagementButton.setVisible(true);
            goToOrdButton.setVisible(true);
            goToCustButton.setVisible(true);
            goToRPTButton.setVisible(true);
            goToTemplatesButton.setVisible(true);
        } else if (role == UserRole.PHARMACIST) {
            goToUserManagementButton.setVisible(false);
            goToRPTButton.setVisible(false);
            goToTemplatesButton.setVisible(false);

            goToOrdButton.setVisible(true);
            goToCustButton.setVisible(true);
        } else if (role == UserRole.MANAGER) {
            goToUserManagementButton.setVisible(false);

            goToOrdButton.setVisible(true);
            goToCustButton.setVisible(true);
            goToRPTButton.setVisible(true);
            goToTemplatesButton.setVisible(true);
        }

        //TODO remove this, debug only!!
        goToOrdButton.setVisible(true);
    }
}
