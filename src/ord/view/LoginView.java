package ord.view;

import ord.controller.ORDController;

import javax.swing.*;

public class LoginView extends JPanel {
    private ORDController controller;

    //Swing Objects
    private JButton backButton;


    static public String cardId() {
        return "loginView";
    }

    public LoginView(ORDController controller) {
        this.controller = controller;

        backButton = new JButton("Back to Main Menu");
        add(backButton);



        backButton.addActionListener(e -> controller.goToHubScreen());
    }
}
