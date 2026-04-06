package users;

import users.view.LoginFrame;

import javax.swing.*;

/**
 * Main – entry point for the IPOS-CA Login package.
 *this class starts the application.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
