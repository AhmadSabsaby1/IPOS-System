package main;

import users.model.Session;
import users.model.UserRole;

public class Global {
    private static final Global singleton = new Global();
    private Main main;
    private Global(){}

    public static Global get(){
        return singleton;
    }

    public void setMain(Main main){
        this.main = main;
    }

    public void goToMainMenu(){
        main.goToMainMenu();
    }

    public UserRole getUserRole() {
        return Session.getInstance().getUserRole();
    }
}
