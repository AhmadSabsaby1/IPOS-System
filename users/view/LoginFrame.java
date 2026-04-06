package users.view;

import ipos.ca.login.controller.LoginController;
import ipos.ca.login.model.User;
import ipos.ca.login.model.UserRole;

import javax.swing.*;
import java.awt.*;

/**
 * LoginFrame – the login screen (UC15).
 * Simple username/password form. On success it opens the appropriate
 * panel for the user's role (Admin → UserManagementFrame, others → placeholder).
 */
public class LoginFrame extends JFrame {

    private final LoginController controller = new LoginController();

    private JTextField     usernameField;
    private JPasswordField passwordField;
    private JLabel         statusLabel;

    public LoginFrame() {
        super("IPOS-CA – Login");
        buildUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 260);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Title
        JLabel title = new JLabel("InfoPharma Ordering System", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 15));
        root.add(title, BorderLayout.NORTH);

        // Form
        JPanel form = new JPanel(new GridLayout(2, 2, 8, 8));
        form.add(new JLabel("Username:"));
        usernameField = new JTextField();
        form.add(usernameField);
        form.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        form.add(passwordField);
        root.add(form, BorderLayout.CENTER);

        // Bottom – button + status
        JPanel bottom = new JPanel(new BorderLayout(5, 5));
        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> handleLogin());
        // Allow Enter key in password field to trigger login
        passwordField.addActionListener(e -> handleLogin());
        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        bottom.add(loginBtn, BorderLayout.CENTER);
        bottom.add(statusLabel, BorderLayout.SOUTH);
        root.add(bottom, BorderLayout.SOUTH);

        add(root);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        User user = controller.login(username, password);

        if (user == null) {
            statusLabel.setText("Invalid username or password.");
            passwordField.setText("");
        } else {
            statusLabel.setForeground(new Color(0, 130, 0));
            statusLabel.setText("Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
            openRoleView(user);
        }
    }

    /** Opens the correct view based on the logged-in user's role. */
    private void openRoleView(User user) {
        // Short delay so user sees the welcome message
        Timer t = new Timer(800, e -> {
            dispose(); // close login window
            if (user.getRole() == UserRole.ADMIN) {
                new UserManagementFrame();
            } else {
                new PlaceholderFrame(user);
            }
        });
        t.setRepeats(false);
        t.start();
    }
}
