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

    //LABELS
    protected FieldComp idComp;
    protected FieldComp nameComp;
    protected FieldComp addressComp;
    protected FieldComp cardTypeComp;
    protected FieldComp firstFourComp;
    protected FieldComp lastFourComp;
    protected FieldComp expiryDateComp;
    protected FieldComp balanceComp;
    protected FieldComp discountTypeComp;
    protected FieldComp discountComp;
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
        cardTypeComp = new FieldComp(AccountHolder.CARD_TYPE).isChoice(AccountHolder.CardType.getOptions());
        firstFourComp = new FieldComp(AccountHolder.FIRST_DIGITS).isInt4Digits();
        lastFourComp = new FieldComp(AccountHolder.LAST_DIGITS).isInt4Digits();
        expiryDateComp = new FieldComp(AccountHolder.EXPIRY_DATE);
        balanceComp = new FieldComp(AccountHolder.BALANCE).isDouble();
        discountTypeComp = new FieldComp(AccountHolder.DISCOUNT_TYPE).isChoice(AccountHolder.DiscountType.getOptions());
        discountComp = new FieldComp(AccountHolder.DISCOUNT).isDouble();
        statusComp = new FieldComp(AccountHolder.STATUS).isChoice(AccountHolder.AccountStatus.getOptions());

        status1stComp = new FieldComp(AccountHolder.STATUS_1ST).isChoice(AccountHolder.ReminderStatus.getOptions()).notModifiable();
        status2ndComp = new FieldComp(AccountHolder.STATUS_2ND).isChoice(AccountHolder.ReminderStatus.getOptions()).notModifiable();

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
            horizontal.addComponent(idComp);
            vertical.addComponent(idComp);
        }

        horizontal
                .addComponent(nameComp)
                .addComponent(addressComp)
                .addComponent(cardTypeComp)
                .addComponent(firstFourComp)
                .addComponent(lastFourComp)
                .addComponent(expiryDateComp)
                .addComponent(balanceComp)
                .addComponent(discountTypeComp)
                .addComponent(discountComp)
                .addComponent(statusComp)
                .addComponent(status1stComp)
                .addComponent(status2ndComp)
        ;

        vertical
                .addComponent(nameComp)
                .addComponent(addressComp)
                .addComponent(cardTypeComp)
                .addComponent(firstFourComp)
                .addComponent(lastFourComp)
                .addComponent(expiryDateComp)
                .addComponent(balanceComp)
                .addComponent(discountTypeComp)
                .addComponent(discountComp)
                .addComponent(statusComp)
                .addComponent(status1stComp)
                .addComponent(status2ndComp)
        ;
    }

    public void modifyAccountField(String field, String value) {
    }

    protected static class FieldComp extends JPanel {
        private String fieldName;
        private String data;
        private FieldType fieldType;
        private String[] choices;

        private static enum FieldType {
            STRING,
            INT_4_DIGITS,
            DOUBLE,
            CHOICE;
        }

        //Swing Objects
        private JLabel fieldLabel;
        private JButton editButton;

        public FieldComp(String fieldName) {
            this.fieldName = fieldName;
            fieldType = FieldType.STRING;
            data = "";

            fieldLabel = new JLabel();
            fieldLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
            blankField();
            editButton = new JButton("Edit");

            GroupLayout layout = new GroupLayout(this);
            setLayout(layout);

            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(false);

            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addComponent(fieldLabel)
                    .addComponent(editButton)
            );

            layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(fieldLabel)
                    .addComponent(editButton)
            );

            editButton.addActionListener(e -> modifyField());
        }

        public boolean isBlank(){
            return data.isEmpty() || data.equals(" ");
        }

        public void blankField(){
            fieldLabel.setText(fieldName + ": <blank>");
        }

        public void modifyField() {
            String newFieldInput;

            if (fieldType == FieldType.DOUBLE || fieldType == FieldType.INT_4_DIGITS) {
                newFieldInput = modifyNumericField();
            } else if (fieldType == FieldType.CHOICE){
                newFieldInput = modifyChoiceField();
            }else {
                newFieldInput = modifyStringField();
            }

            if (newFieldInput != null) {
                setFieldData(newFieldInput);
            }
        }

        public void setFieldData(String data) {
            this.data = data;
            fieldLabel.setText(fieldName + ": " + data);
        }

        public FieldComp isDouble() {
            fieldType = FieldType.DOUBLE;
            return this;
        }

        public FieldComp isInt4Digits() {
            fieldType = FieldType.INT_4_DIGITS;
            return this;
        }

        public FieldComp notModifiable() {
            editButton.setVisible(false);
            return this;
        }

        public String getData() {
            return data;
        }

        public void removeData(){
            data = "";
            blankField();
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
                else if (fieldType == FieldType.INT_4_DIGITS)
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
            else{
                r = Integer.toString(Double.valueOf(quantity).intValue());
                if (quantity < 10)
                    r = "000" + r;
                else if (quantity < 100)
                    r = "00" + r;
                else if (quantity < 1000)
                    r = "0" + r;
                else if (quantity == 0)
                    r = "0000";
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
