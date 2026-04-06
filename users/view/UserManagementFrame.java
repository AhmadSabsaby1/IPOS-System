package users.view;

import users.controller.LoginController;
import users.controller.UserAdminController;
import users.model.User;
import users.model.UserRole;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * UserManagementFrame – Admin panel (UC1, UC2, UC10, UC16).
 * Uses the real DB via UserAdminController.
 * Username is the PK — no numeric IDs.
 */
public class UserManagementFrame extends JFrame {

    private final UserAdminController adminCtrl = new UserAdminController();
    private final LoginController     loginCtrl = new LoginController();

    private JTable            userTable;
    private DefaultTableModel tableModel;

    public UserManagementFrame() {
        super("IPOS-CA – User Management (Admin)");
        buildUI();
        loadUsers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 380);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---- Title bar ----
        JLabel title = new JLabel("User Management", SwingConstants.LEFT);
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            loginCtrl.logout();
            dispose();
            new LoginFrame();
        });
        JPanel top = new JPanel(new BorderLayout());
        top.add(title, BorderLayout.WEST);
        top.add(logoutBtn, BorderLayout.EAST);
        root.add(top, BorderLayout.NORTH);

        // ---- JTable (CTable) – columns match DB schema: username, password, role ----
        String[] cols = {"Username", "Password", "Role"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.getColumnModel().getColumn(2).setMaxWidth(120);
        root.add(new JScrollPane(userTable), BorderLayout.CENTER);

        // ---- Buttons ----
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));

        JButton createBtn = new JButton("Create User (UC1)");
        createBtn.addActionListener(e -> showCreateDialog());

        JButton deleteBtn = new JButton("Delete User (UC2)");
        deleteBtn.addActionListener(e -> handleDelete());

        JButton roleBtn = new JButton("Assign Role (UC10)");
        roleBtn.addActionListener(e -> handleAssignRole());

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadUsers());

        btnPanel.add(createBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(roleBtn);
        btnPanel.add(refreshBtn);
        root.add(btnPanel, BorderLayout.SOUTH);

        add(root);
    }

    // -----------------------------------------------------------------------
    // Table loading
    // -----------------------------------------------------------------------

    private void loadUsers() {
        tableModel.setRowCount(0);
        List<User> users = adminCtrl.getAllUsers();
        for (User u : users) {
            tableModel.addRow(new Object[]{
                u.getUsername(),
                u.getPassword(),
                u.getRole().name()
            });
        }
    }

    // -----------------------------------------------------------------------
    // UC1 – Create user dialog
    // -----------------------------------------------------------------------

    private void showCreateDialog() {
        JTextField     usernameF = new JTextField(15);
        JPasswordField passF     = new JPasswordField(15);
        JComboBox<UserRole> roleBox = new JComboBox<>(UserRole.values());

        JPanel p = new JPanel(new GridLayout(3, 2, 6, 6));
        p.add(new JLabel("Username:")); p.add(usernameF);
        p.add(new JLabel("Password:")); p.add(passF);
        p.add(new JLabel("Role:"));     p.add(roleBox);

        int result = JOptionPane.showConfirmDialog(this, p,
                "Create New User (UC1)", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) return;

        String username = usernameF.getText().trim();
        String password = new String(passF.getPassword());
        UserRole role   = (UserRole) roleBox.getSelectedItem();

        boolean ok = adminCtrl.createUser(username, password, role);
        if (!ok) {
            JOptionPane.showMessageDialog(this,
                    "Failed: username may already exist or input was invalid.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "User '" + username + "' created successfully.");
            loadUsers();
        }
    }

    // -----------------------------------------------------------------------
    // UC2 – Delete user
    // -----------------------------------------------------------------------

    private void handleDelete() {
        int row = userTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a user first."); return; }

        String username = (String) tableModel.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete account for: " + username + "?",
                "Confirm Delete (UC2)", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        boolean ok = adminCtrl.deleteUser(username);
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Delete failed.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "User deleted successfully.");
            loadUsers();
        }
    }

    // -----------------------------------------------------------------------
    // UC10 – Assign role
    // -----------------------------------------------------------------------

    private void handleAssignRole() {
        int row = userTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a user first."); return; }

        String username    = (String) tableModel.getValueAt(row, 0);
        String currentRole = (String) tableModel.getValueAt(row, 2);

        JComboBox<UserRole> roleBox = new JComboBox<>(UserRole.values());
        roleBox.setSelectedItem(UserRole.valueOf(currentRole));

        JPanel p = new JPanel();
        p.add(new JLabel("New role for " + username + ":"));
        p.add(roleBox);

        int result = JOptionPane.showConfirmDialog(this, p,
                "Assign Role (UC10)", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) return;

        UserRole newRole = (UserRole) roleBox.getSelectedItem();
        boolean ok = adminCtrl.assignRole(username, newRole);
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Assign role failed.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Role updated to " + newRole + ".");
            loadUsers();
        }
    }
}
