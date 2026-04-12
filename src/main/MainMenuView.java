package main;

import custom.TitleLabel;

import javax.swing.*;

public class MainMenuView extends JPanel {
    private Main main;

    //Swing Objects
    private JButton goToCustButton;
    private JButton goToOrdButton;

    public static String cardId(){
        return "MainMenuView";
    }
    public MainMenuView(Main main) {
        this.main = main;

        TitleLabel titleLabel = new TitleLabel("IPOS-CA: Main Menu");
        goToCustButton = new JButton("Manage Account Holders and Orders");
        goToOrdButton = new JButton("Order from IPOS-SA");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(goToCustButton)
                .addComponent(goToOrdButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(40)
                .addComponent(goToCustButton)
                .addComponent(goToOrdButton)
        );

        goToCustButton.addActionListener(e->main.goToCust());
        goToOrdButton.addActionListener(e->main.goToOrd());
    }
}
