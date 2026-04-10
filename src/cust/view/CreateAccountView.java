package cust.view;

import cust.controller.CUSTController;
import cust.model.AccountHolder;

import javax.swing.*;
import java.util.ArrayList;

public class CreateAccountView extends ModifyCreateAccountView {
    private JButton createAccountButton;
    private JLabel infoLabel;

    static public String cardId(){
        return "CreateAccountView";
    }
    public CreateAccountView(CUSTController controller) {
        super(controller);

        titleLabel.setText("Create Account");
        infoLabel = new JLabel();
        createAccountButton = new JButton("Create this Account");

        balanceComp.setVisible(false);
        statusComp.setVisible(false);
        status1stComp.setVisible(false);
        status2ndComp.setVisible(false);

        showCorrectDiscounts();

        GroupLayout.ParallelGroup horizontal = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();

        addTitle(horizontal, vertical);
        addAllFields(horizontal, vertical, false);
        horizontal.addComponent(infoLabel);
        vertical.addComponent(infoLabel);
        horizontal.addComponent(createAccountButton);
        vertical.addComponent(createAccountButton);

        layout.setHorizontalGroup(horizontal);
        layout.setVerticalGroup(vertical);

        backButton.addActionListener(e->backButtonPressed());
        createAccountButton.addActionListener(e->createAccount());
    }

    @Override
    public void modifyAccountField(String field, String value){
        showCorrectDiscounts();
    }

    private void showCorrectDiscounts(){
        if (discountTypeComp.getData().equals(AccountHolder.DiscountType.FIXED.toString())){
            showFixedDiscounts();
        }else if (discountTypeComp.getData().equals(AccountHolder.DiscountType.FLEXIBLE.toString())){
            showFlexDiscounts();
        }else{
            hideAllDiscounts();
        }
    }

    private void showFixedDiscounts(){
        fixedDiscountComp.setVisible(true);
        tier1ThresholdComp.setVisible(false);
        tier2ThresholdComp.setVisible(false);
        tier1DiscountComp.setVisible(false);
        tier2DiscountComp.setVisible(false);
        tier3DiscountComp.setVisible(false);
    }

    private void showFlexDiscounts(){
        fixedDiscountComp.setVisible(false);
        tier1ThresholdComp.setVisible(true);
        tier2ThresholdComp.setVisible(true);
        tier1DiscountComp.setVisible(true);
        tier2DiscountComp.setVisible(true);
        tier3DiscountComp.setVisible(true);
    }

    private void hideAllDiscounts(){
        fixedDiscountComp.setVisible(false);
        tier1ThresholdComp.setVisible(false);
        tier2ThresholdComp.setVisible(false);
        tier1DiscountComp.setVisible(false);
        tier2DiscountComp.setVisible(false);
        tier3DiscountComp.setVisible(false);
    }

    public void removeAllData(){
        nameComp.removeData();
        addressComp.removeData();
        phoneNumberComp.removeData();
        emailComp.removeData();
        balanceComp.removeData();
        discountTypeComp.removeData();
        fixedDiscountComp.removeData();
    }

    private void backButtonPressed(){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Do you want to cancel the creation of this account and go back?", "Are you sure?", dialogButton);
        if(dialogResult == 0) {
            removeAllData();
            controller.goToAccountHolderManagerScreen();
        }
    }

    private void createAccount(){
        ArrayList<FieldComp> blankComps = new ArrayList<>();

        if (nameComp.isBlank())
            blankComps.add(nameComp);

        if (addressComp.isBlank())
            blankComps.add(addressComp);

        if (phoneNumberComp.isBlank())
            blankComps.add(phoneNumberComp);

        if (emailComp.isBlank())
            blankComps.add(emailComp);

        if (balanceLimitComp.isBlank())
            blankComps.add(balanceComp);

        if (discountTypeComp.isBlank())
            blankComps.add(discountTypeComp);
        else{
            if (discountTypeComp.getData().equals(AccountHolder.DiscountType.FIXED.toString())){
                if (fixedDiscountComp.isBlank())
                    blankComps.add(fixedDiscountComp);
                else{
                    tier1DiscountComp.setFieldData("0");
                    tier2DiscountComp.setFieldData("0");
                    tier3DiscountComp.setFieldData("0");
                    tier1ThresholdComp.setFieldData("0");
                    tier2ThresholdComp.setFieldData("0");
                }
            } else if (discountTypeComp.getData().equals(AccountHolder.DiscountType.FLEXIBLE.toString())) {
                if (tier1ThresholdComp.isBlank())
                    blankComps.add(tier1ThresholdComp);
                if (tier2ThresholdComp.isBlank())
                    blankComps.add(tier2ThresholdComp);
                if (tier1DiscountComp.isBlank())
                    blankComps.add(tier1DiscountComp);
                if (tier2DiscountComp.isBlank())
                    blankComps.add(tier2DiscountComp);
                if (tier3DiscountComp.isBlank())
                    blankComps.add(tier3DiscountComp);

                if (blankComps.isEmpty())
                    fixedDiscountComp.setFieldData("0");
            }
        }

        if (!blankComps.isEmpty()){
            infoLabel.setText("Some of the fields are blank: ");
            for (FieldComp field : blankComps){
                infoLabel.setText(infoLabel.getText() + field.getFieldName() + ", ");
            }
        }else{
            if (discountTypeComp.getData().equals(AccountHolder.DiscountType.FIXED.toString())){
                controller.createAccount(new AccountHolder(
                        nameComp.getData(),
                        addressComp.getData(),
                        phoneNumberComp.getData(),
                        emailComp.getData(),
                        Integer.parseInt(balanceLimitComp.getData()),
                        AccountHolder.DiscountType.getValue(discountTypeComp.getData()),
                        Double.parseDouble(fixedDiscountComp.getData())
                ));
            }else if (discountTypeComp.getData().equals(AccountHolder.DiscountType.FLEXIBLE.toString())){
                controller.createAccount(new AccountHolder(
                        nameComp.getData(),
                        addressComp.getData(),
                        phoneNumberComp.getData(),
                        emailComp.getData(),
                        Integer.parseInt(balanceLimitComp.getData()),
                        AccountHolder.DiscountType.getValue(discountTypeComp.getData()),
                        Integer.parseInt(tier1ThresholdComp.getData()),
                        Integer.parseInt(tier2ThresholdComp.getData()),
                        Double.parseDouble(tier1DiscountComp.getData()),
                        Double.parseDouble(tier2DiscountComp.getData()),
                        Double.parseDouble(tier3DiscountComp.getData())
                ));
            }else{
                controller.createAccount(new AccountHolder(
                        nameComp.getData(),
                        addressComp.getData(),
                        phoneNumberComp.getData(),
                        emailComp.getData(),
                        Integer.parseInt(balanceLimitComp.getData())
                ));
            }

            removeAllData();
            controller.goToAccountHolderManagerScreen();
        }
    }
}