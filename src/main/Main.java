package main;

import cust.controller.CUSTController;
import ord.controller.ORDController;
import rpt.controller.RPTController;
import users.model.Session;
import users.view.LoginFrame;
import users.view.UserManagementFrame;

import javax.swing.*;

public class Main {
    private MainView mainView;

    //VIEWS
    private MainMenuView mainMenuView;
    private CUSTController custController;
    private ORDController ordController;
    private LoginFrame loginFrame;
    private UserManagementFrame userManagementFrame;
    private RPTController rptController;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    public Main(){
        Global.get().setMain(this);
        goToMainMenu();
    }

    /// /////////// SCREEN SWITCH /////////////////
    public void goToMainMenu(){
        mainView = new MainView();
        mainMenuView = new MainMenuView(this);

        mainView.addCardLayout(mainMenuView, MainMenuView.cardId());
        mainMenuView.checkPerms();
    }

    public void goToCust() {
        custController = new CUSTController();
        mainView.dispose();
    }

    public void goToOrd() {
        ordController = new ORDController();
        mainView.dispose();
    }

    public void goToUsers(){
        loginFrame = new LoginFrame();
        mainView.dispose();
    }

    public void goToUserManagement() {
        userManagementFrame = new UserManagementFrame();
        mainView.dispose();
    }

    public void goToRPT() {
        rptController = new RPTController();
        mainView.dispose();
    }

    /// ////////////////////////////////////////
}
