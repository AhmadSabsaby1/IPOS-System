package templates.view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class MerchantSettingView extends JPanel{

    private cust.controller.CUSTController controller;

    private JTextField nameField = new JTextField(20);
    private JTextField emailField = new JTextField(20);
    private JTextArea addressArea = new JTextArea(4,20);
    private JTextField phoneField = new JTextField(20);

    private JLabel logo = new JLabel("no Logo", SwingConstants.CENTER);
    private String logoPath = "";
    private JButton browseLogoButton = new JButton("Browse Logo");

    private JButton saveButton = new JButton("Save Changes");
    private JButton backButton = new JButton("Back to Template Manager");

    public static String cardId() {
        return "MerchantSettingView";
    }

    public MerchantSettingView(cust.controller.CUSTController controller) {
        this.controller = controller;
        setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(8, 8, 8, 8);
        grid.fill = GridBagConstraints.HORIZONTAL;


        grid.gridx = 0; grid.gridy = 0; grid.gridwidth = 2;
        JLabel title = new JLabel("Edit Merchant Details");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, grid);

        ///logo
        grid.gridwidth = 1;
        grid.gridx = 0; grid.gridy = 1;
        add(new JLabel("Merchant Logo:"), grid);

       ///logo preview box
        logo.setPreferredSize(new Dimension(100, 100));
        logo.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        grid.gridx = 1;
        add(logo, grid);

        grid.gridx = 1; grid.gridy = 2;
        add(browseLogoButton, grid);

       ///fields
        grid.gridx = 0; grid.gridy = 3; add(new JLabel("Merchant Name:"), grid);
        grid.gridx = 1; add(nameField, grid);

        grid.gridx = 0; grid.gridy = 4; add(new JLabel("Merchant Email:"), grid);
        grid.gridx = 1; add(emailField, grid);

        grid.gridx = 0; grid.gridy = 5; add(new JLabel("Merchant Address:"), grid);
        grid.gridx = 1; add(new JScrollPane(addressArea), grid);

        grid.gridx = 0; grid.gridy = 6; add(new JLabel("Merchant Phone:"), grid);
        grid.gridx = 1; add(phoneField, grid);


        grid.gridx = 0; grid.gridy = 7; add(backButton, grid);
        grid.gridx = 1; add(saveButton, grid);


        String[] data = templates.controller.TemplateController.loadMerchantDetails();

        /// fills in the fields with the loaded info
        if (data.length >= 5) {
            nameField.setText(data[0]);
            emailField.setText(data[1]);
            addressArea.setText(data[2]);
            phoneField.setText(data[3]);
            this.logoPath = data[4];

            // Update the visual logo preview if a path exists
            if (!this.logoPath.isEmpty()) {
                updateLogoPreview(this.logoPath);
            }
        }


        browseLogoButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Merchant Logo");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg", "gif"));

            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                logoPath = selectedFile.getAbsolutePath();
                updateLogoPreview(logoPath);
            }
        });


        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String address = addressArea.getText();
            String phone = phoneField.getText();


            controller.saveMerchantDetails(name, email, address, phone, logoPath);
            JOptionPane.showMessageDialog(this, "Merchant Details Saved Successfully!");
        });

        backButton.addActionListener(e -> controller.goToTemplateManagerScreen());




    }

///UPDATES THE LOGO WEHN NEW IMAGE IS ADDED
    private void updateLogoPreview(String path) {
        ImageIcon image = new ImageIcon(path);
        // Scale to 100x100
        Image img = image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(img));
        logo.setText("");
    }


    public void populateTemplates(Object templates) {}
}

