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
        SwingUtilities.invokeLater(Main::new);
        //SwingUtilities.invokeLater(CUSTController::new);
        //SwingUtilities.invokeLater(ORDController::new);
    }

    public Main(){
        Global.get().setMain(this);
        goToMainMenu();
    }

    /// /////////// SCREEN SWITCH /////////////////
    public void goToCust() {
        custController = new CUSTController();
        mainView.dispose();
    }

    public void goToOrd() {
        ordController = new ORDController();
        mainView.dispose();
    }

    public void goToMainMenu(){
        mainView = new MainView();
        mainMenuView = new MainMenuView(this);

        mainView.addCardLayout(mainMenuView, MainMenuView.cardId());
    }

    /// ////////////////////////////////////////
}
