package main;

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

        goToCustButton = new JButton("Cust");
        goToOrdButton = new JButton("Ord");

        add(goToCustButton);

        goToCustButton.addActionListener(e->main.goToCust());
    }
}
