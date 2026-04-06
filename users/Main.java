package users;

import users.view.LoginFrame;

import javax.swing.*;

/**
 * Main – entry point for the IPOS-CA Login package.
 * Run this class to start the application.
 * Credentials are loaded from the MySQL database (configured in DBParent).
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
