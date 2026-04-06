package ipos.ca.login.view;

import ipos.ca.login.controller.LoginController;
import ipos.ca.login.model.User;

import javax.swing.*;
import java.awt.*;

/**
 * PlaceholderFrame – shown after Pharmacist or Manager logs in.
 * Your team-mates' packages (sales, stock, reports, etc.) will replace
 * the placeholder panels with real content.
 *
 * This frame demonstrates UC16 (Logout) and role-based routing.
 */
public class PlaceholderFrame extends JFrame {

    private final LoginController loginCtrl = new LoginController();

    public PlaceholderFrame(User user) {
        super("IPOS-CA – " + user.getRole().name());

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcome = new JLabel(
                "Logged in as: " + user.getUsername() + "  |  Role: " + user.getRole(),
                SwingConstants.CENTER);
        welcome.setFont(new Font("SansSerif", Font.BOLD, 13));

        JLabel note = new JLabel(
                "<html><center>Your role-specific panels go here.<br>"
                + "Connect your package to this frame.</center></html>",
                SwingConstants.CENTER);

        JButton logoutBtn = new JButton("Logout (UC16)");
        logoutBtn.addActionListener(e -> {
            loginCtrl.logout();
            dispose();
            new LoginFrame();
        });

        root.add(welcome, BorderLayout.NORTH);
        root.add(note, BorderLayout.CENTER);
        root.add(logoutBtn, BorderLayout.SOUTH);
        add(root);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 250);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
