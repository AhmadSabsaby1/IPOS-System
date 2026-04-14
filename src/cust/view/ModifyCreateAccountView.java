package cust.view;

import cust.controller.CUSTController;
import cust.model.AccountHolder;
import custom.TitleLabel;

import javax.swing.*;
import java.awt.*;

public class ModifyCreateAccountView extends JPanel {
    protected CUSTController controller;
    protected GroupLayout layout;

    //Swing Objects
    protected JButton backButton;
    protected TitleLabel titleLabel;
    protected JButton send1stReminderButton;
    protected JButton send2ndReminderButton;

    //LABELS
    protected FieldComp idComp;
    protected FieldComp nameComp;
    protected FieldComp addressComp;
    protected FieldComp phoneNumberComp;
    protected FieldComp emailComp;
    protected FieldComp balanceComp;
    protected FieldComp balanceLimitComp;
    protected FieldComp discountTypeComp;
    protected FieldComp fixedDiscountComp;
    protected FieldComp tier1ThresholdComp;
    protected FieldComp tier2ThresholdComp;
    protected FieldComp tier1DiscountComp;
    protected FieldComp tier2DiscountComp;
    protected FieldComp tier3DiscountComp;
    protected FieldComp statusComp;
    protected FieldComp status1stComp;
    protected FieldComp status2ndComp;

    public ModifyCreateAccountView(CUSTController controller) {
        this.controller = controller;

        titleLabel = new TitleLabel("");
        backButton = new JButton("Back to Account Manager");

        idComp = new FieldComp(AccountHolder.ACCOUNT_ID).notModifiable();
        nameComp = new FieldComp(AccountHolder.NAME);
        addressComp = new FieldComp(AccountHolder.ADDRESS);
        phoneNumberComp = new FieldComp(AccountHolder.PHONE);
        emailComp = new FieldComp(AccountHolder.EMAIL);
        balanceComp = new FieldComp(AccountHolder.BALANCE).isDouble();
        balanceLimitComp = new FieldComp(AccountHolder.BALANCE_LIMIT).isInt();
        discountTypeComp = new FieldComp(AccountHolder.DISCOUNT_TYPE).isChoice(AccountHolder.DiscountType.getOptions());

        //discount fields
        fixedDiscountComp = new FieldComp(AccountHolder.FIXED_DISCOUNT).isDouble().showPercentage();
        tier1ThresholdComp = new FieldComp(AccountHolder.TIER_1_THRESHOLD).isInt().customFieldLabel("Tier 1 is < than:");
        tier2ThresholdComp = new FieldComp(AccountHolder.TIER_2_THRESHOLD).isInt().customFieldLabel("Tier 2 is <= than:");
        tier1DiscountComp = new FieldComp(AccountHolder.TIER_1_DISCOUNT).isDouble().showPercentage();
        tier2DiscountComp = new FieldComp(AccountHolder.TIER_2_DISCOUNT).isDouble().showPercentage();
        tier3DiscountComp = new FieldComp(AccountHolder.TIER_3_DISCOUNT).isDouble().showPercentage();

        //status fields
        statusComp = new FieldComp(AccountHolder.STATUS).isChoice(AccountHolder.AccountStatus.getOptions()).notModifiable();
        status1stComp = new FieldComp(AccountHolder.STATUS_1ST).isChoice(AccountHolder.ReminderStatus.getOptions()).notModifiable();
        status2ndComp = new FieldComp(AccountHolder.STATUS_2ND).isChoice(AccountHolder.ReminderStatus.getOptions()).notModifiable();
        send1stReminderButton = new JButton("Send 1st Reminder");
        send1stReminderButton.setVisible(false);
        send2ndReminderButton = new JButton("Send 2nd Reminder");
        send2ndReminderButton.setVisible(false);

        layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
    }

    protected void addTitle(GroupLayout.ParallelGroup horizontal, GroupLayout.SequentialGroup vertical) {
        horizontal
                .addComponent(titleLabel)
                .addComponent(backButton)
        ;

        vertical
                .addComponent(titleLabel)
                .addGap(40)
                .addComponent(backButton)
                .addGap(40)
        ;
    }

    protected void addAllFields(GroupLayout.ParallelGroup horizontal, GroupLayout.SequentialGroup vertical, boolean includeId) {
        if (includeId){
            horizontal.addComponent(idComp, GroupLayout.Alignment.CENTER);
            vertical.addComponent(idComp);
            vertical.addGap(20);
        }

        horizontal
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(nameComp)
                                .addComponent(addressComp)
                                .addComponent(phoneNumberComp)
                                .addComponent(emailComp)
                                .addComponent(balanceComp)
                                .addComponent(balanceLimitComp)
                                .addComponent(discountTypeComp)
                                .addComponent(tier1DiscountComp)
                                .addComponent(tier1ThresholdComp)
                                .addComponent(tier2DiscountComp)
                                .addComponent(tier2ThresholdComp)
                                .addComponent(tier3DiscountComp)
                                .addComponent(fixedDiscountComp)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(statusComp)
                                .addComponent(status1stComp)
                                .addComponent(send1stReminderButton)
                                .addComponent(status2ndComp)
                                .addComponent(send2ndReminderButton)
                        )
                )
        ;

        vertical
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(nameComp)
                                .addComponent(addressComp)
                                .addComponent(phoneNumberComp)
                                .addComponent(emailComp)
                                .addComponent(balanceComp)
                                .addComponent(balanceLimitComp)
                                .addComponent(discountTypeComp)
                                .addComponent(tier1DiscountComp)
                                .addComponent(tier1ThresholdComp)
                                .addComponent(tier2DiscountComp)
                                .addComponent(tier2ThresholdComp)
                                .addComponent(tier3DiscountComp)
                                .addComponent(fixedDiscountComp)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(statusComp)
                                .addComponent(status1stComp)
                                .addComponent(send1stReminderButton)
                                .addComponent(status2ndComp)
                                .addComponent(send2ndReminderButton)
                        )
                )
        ;
    }

    public void modifyAccountField(String field, String value) {
    }

    protected class FieldComp extends JPanel {
        private String fieldName;
        private String data;
        private FieldType fieldType;
        private String[] choices;
        private boolean showPercentage;
        private String customFieldLabel;

        private enum FieldType {
            STRING,
            INT,
            INT_4_DIGITS,
            DOUBLE,
            CHOICE;
        }

        //Swing Objects
        private JLabel fieldLabel;
        private JLabel dataLabel;
        private JButton editButton;

        public FieldComp(String fieldName) {
            this.fieldName = fieldName;
            fieldType = FieldType.STRING;
            data = "";

            fieldLabel = new JLabel();
            dataLabel = new JLabel();

            fieldLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
            dataLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

            drawBlankField();

            editButton = new JButton("Edit");

            setMinimumSize(new Dimension(getPreferredSize().width, editButton.getPreferredSize().height));

            //FlowLayoutManager();
            //GridBagLayoutManager();
            NullLayoutManager();

            editButton.addActionListener(e -> modifyField());
        }

        private void GridBagLayoutManager() {
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            c.gridwidth = 2;
            c.gridx = 0;
            c.gridy = 0;
            add(fieldLabel, c);

            c.gridwidth = 1;
            c.gridx = 2;
            add(editButton, c);
        }

        private void NullLayoutManager(){
            setLayout(null);
            add(fieldLabel);
            add(dataLabel);
            add(editButton);

            Insets insets = getInsets();
            Dimension buttonSize = editButton.getPreferredSize();
            fieldLabel.setBounds(insets.left, insets.top,
                    fieldLabel.getPreferredSize().width + 50, buttonSize.height);

            dataLabel.setBounds(insets.left + 200, insets.top,
                    350, buttonSize.height);

            editButton.setBounds(dataLabel.getX() + dataLabel.getPreferredSize().width + 150, insets.top,
                    buttonSize.width, buttonSize.height);
        }

        private void FlowLayoutManager(){
            setLayout(new FlowLayout(FlowLayout.LEFT));
            add(fieldLabel);
            add(editButton);
        }

        private void GroupLayoutManager(){
            GroupLayout layout = new GroupLayout(this);
            setLayout(layout);

            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(false);

            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addComponent(fieldLabel)
                    .addComponent(editButton)
            );

            layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldLabel)
                    .addComponent(editButton)
            );
        }

        public boolean isBlank(){
            return data.isEmpty() || data.equals(" ");
        }

        public void drawBlankField(){
            fieldLabel.setText(fieldName + ":");
            dataLabel.setText("<blank>");
        }

        public void modifyField() {
            String newFieldInput;

            if (fieldType == FieldType.DOUBLE || fieldType == FieldType.INT_4_DIGITS || fieldType == FieldType.INT) {
                newFieldInput = modifyNumericField();
            } else if (fieldType == FieldType.CHOICE){
                newFieldInput = modifyChoiceField();
            }else {
                newFieldInput = modifyStringField();
            }

            if (newFieldInput != null) {
                setFieldData(newFieldInput);
                modifyAccountField(fieldName, newFieldInput);
            }
        }

        public void setFieldData(String data) {
            this.data = data;
            if (customFieldLabel == null) {
                fieldLabel.setText(fieldName + ":");
            }else
                fieldLabel.setText(customFieldLabel);

            if (showPercentage)
                dataLabel.setText(data + "%");
            else
                dataLabel.setText(data);
        }

        public FieldComp isDouble() {
            fieldType = FieldType.DOUBLE;
            return this;
        }

        public FieldComp isInt4Digits() {
            fieldType = FieldType.INT_4_DIGITS;
            return this;
        }

        public FieldComp isInt() {
            fieldType = FieldType.INT;
            return this;
        }

        public FieldComp notModifiable() {
            editButton.setVisible(false);
            return this;
        }

        public void setAsModifiable(){
            editButton.setVisible(true);
        }

        public FieldComp showPercentage(){
            showPercentage = true;
            return this;
        }

        public FieldComp customFieldLabel(String customFieldLabel) {
            this.customFieldLabel = customFieldLabel;
            return this;
        }

        public String getData() {
            return data;
        }

        public void removeData(){
            data = "";
            drawBlankField();
        }

        public String getFieldName(){
            return fieldName;
        }

        public FieldComp isChoice(String[] choices) {
            fieldType = FieldType.CHOICE;
            this.choices = choices;
            return this;
        }

        /// /////////// PRIVATE METHODS ////////////
        private String modifyStringField() {
            String newFieldInput = JOptionPane.showInputDialog("Enter the new " + fieldName);
            if (newFieldInput == null || newFieldInput.isEmpty()) {
                //cancelled or empty
                return null;
            }

            return newFieldInput;
        }

        private String modifyNumericField() {
            double quantity = 0;
            String quantityInput = JOptionPane.showInputDialog("Enter the new " + fieldName);

            if (quantityInput == null || quantityInput.isEmpty()) {
                //input cancelled or left empty
                return null;
            }

            try {
                if (fieldType == FieldType.DOUBLE)
                    quantity = Double.parseDouble(quantityInput);
                else if (fieldType == FieldType.INT_4_DIGITS || fieldType == FieldType.INT)
                    quantity = Integer.parseInt(quantityInput);
            } catch (NumberFormatException ex) {
                //TODO put some text to signify the quantity is not valid
                return null;
            }

            //check if the user introduced a 0 or a negative number
            if (quantity < 0) {
                //TODO put some text to signify the quantity is not valid
                return null;
            }

            String r = "";
            if (fieldType == FieldType.DOUBLE)
                r = Double.toString(quantity);
            else if (fieldType == FieldType.INT_4_DIGITS){
                r = Integer.toString(Double.valueOf(quantity).intValue());
                if (quantity < 10)
                    r = "000" + r;
                else if (quantity < 100)
                    r = "00" + r;
                else if (quantity < 1000)
                    r = "0" + r;
                else if (quantity == 0)
                    r = "0000";
            }else{
                r = Integer.toString((int)quantity);
            }

            return r;
        }


        private String modifyChoiceField() {
            String newFieldInput = (String) JOptionPane.showInputDialog(
                    this,
                    "Enter the new " + fieldName,
                    "Input",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    choices,
                    "");
            if (newFieldInput == null || newFieldInput.isEmpty()) {
                //cancelled or empty
                return null;
            }

            return newFieldInput;
        }
        /// //////////////////////////////////


    }
}
