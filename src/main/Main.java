package main;

import cust.controller.CUSTController;
import ord.controller.ORDController;

import javax.swing.*;

public class Main {
    private MainView mainView;


    //VIEWS
    private MainMenuView mainMenuView;
    private CUSTController custController;
    private ORDController ordController;

    public static void main(String[] args) {
        //SwingUtilities.invokeLater(Main::new);
        SwingUtilities.invokeLater(CUSTController::new);
        //SwingUtilities.invokeLater(ORDController::new);
    }

    public Main(){
        mainView = new MainView();

        mainMenuView = new MainMenuView(this);

        mainView.addCardLayout(mainMenuView, MainMenuView.cardId());
    }

    /// /////////// SCREEN SWITCH /////////////////
    public void goToCust() {
        custController = new CUSTController();
        mainView.dispose();
    }

    /// ////////////////////////////////////////
}
