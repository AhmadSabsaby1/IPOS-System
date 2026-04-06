package cust.view;

import cust.controller.CUSTController;
import cust.model.AccountHolder;
import custom.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.time.LocalDate;

import static templates.controller.TemplateController.loadMerchantDetails;
import static templates.controller.TemplateController.loadTemplate;

public class TemplateManagerView extends JPanel {
    private CUSTController controller;

    /// UI Components
    private TitleLabel titleLabel;
    private JButton backButton;
    private JButton saveButton;
    private JTextArea templateEditor;
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
///customer id
/// purchase date
/// order number
/// Amount spent
/// invoice number
/// merchant name
/// customer name
/// manager name
/// current date
/// phone number
/// merchant adress


///REMINDER METHOD
    public void generateReminder(AccountHolder customer, String invoiceNo, String orderNo, double amount, String purchaseDate){
        String template = loadTemplate("reminderTemplate");


        String[] merchant = loadMerchantDetails();
        String merchantName = merchant[0];
        String merchantEmail = merchant[1];
        String merchantAddress =merchant[2];
        String merchantPhone = merchant[3];



        String reminder = template.replace("[MERCHANT_NAME]",merchantName)
                .replace("[MERCHANT_ADDRESS]", merchantAddress)
                .replace("[PHONE]", merchantPhone)
                .replace("[CURRENT_DATE]", LocalDate.now().toString())
                .replace("[INVOICE_NO]", invoiceNo)
                .replace("[ACCOUNT_ID]",customer.getAccountId())
                .replace("[AMOUNT]",String.format(String.valueOf(amount)))
                .replace("[CUSTOMER_NAME]", customer.getName())
                .replace("[PURCHASE_DATE]", purchaseDate)
                .replace("[MERCHANT_EMAIL]", merchantEmail)
                .replace("[ORDER_NUMBER]",orderNo);

        saveGeneratedReminder(customer.getAccountId(), reminder);
    }


    public void saveGeneratedReminder(String customerId, String reminder){
        String  PATH = "templates/mocks/";
        File file = new File(PATH,customerId +"txt");



    }

}