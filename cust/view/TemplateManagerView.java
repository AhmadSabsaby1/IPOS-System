package cust.view;

import cust.controller.CUSTController;
import custom.TitleLabel;
import templates.controller.TemplateController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class TemplateManagerView extends JPanel {
    private CUSTController controller;

    /// UI Components
    private TitleLabel titleLabel;
    private JButton backButton;
    private JButton saveButton; /// To save edits
    private JTextArea templateEditor; /// Where the editing happens
    private JComboBox<String> templateSelector;
    private JButton merchantSettingsButton;

    static public String cardId(){
        return "TemplateManagerView";
    }

    public TemplateManagerView(CUSTController controller) {
        this.controller = controller;

        titleLabel = new TitleLabel("Template Manager");
        backButton = new JButton("Back to Main Menu");
        saveButton = new JButton("Save Changes");
        merchantSettingsButton = new JButton("Edit Merchant Details");

        /// Setup the editor area
        templateEditor = new JTextArea(20, 40);
        templateEditor.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(templateEditor);

        /// Selector to switch templates
        String[] options = {"invoiceTemplate", "reminderTemplate"};
        templateSelector = new JComboBox<>(options);

        setTemplateText(controller.loadTemplate((String) templateSelector.getSelectedItem()));

        /// Layout Logic
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(titleLabel);
        topPanel.add(templateSelector);
        topPanel.add(merchantSettingsButton);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(saveButton);
        bottomPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        ///Action Listeners
        backButton.addActionListener(e -> controller.goToHubScreen());
        merchantSettingsButton.addActionListener(e -> controller.goToMerchantSettings());

        /// Logic to save the edits
        saveButton.addActionListener(e -> {
            String updatedTemplate = templateEditor.getText();
            String selectedType = (String) templateSelector.getSelectedItem();
            controller.saveTemplate(selectedType, updatedTemplate);
            JOptionPane.showMessageDialog(this, "Template Saved Successfully!");
        });

        ///template selector logic
        templateSelector.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedType = (String) templateSelector.getSelectedItem();
                String content = controller.loadTemplate(selectedType);

                setTemplateText(content);
            }
        });
    }

    /// Method to load text into the editor when the screen opens
    public void setTemplateText(String text) {
        templateEditor.setText(text);
        templateEditor.setCaretPosition(0);
    }

    public void populateTemplates(Object templates) {
    }

    public void generateReminder(){
        String[] merchant = loadMerchantDetails();

    }
}