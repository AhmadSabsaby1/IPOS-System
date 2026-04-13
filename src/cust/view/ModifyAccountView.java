package cust.view;

import cust.controller.CUSTController;
import cust.model.AccountHolder;

import javax.swing.*;

public class ModifyAccountView extends ModifyCreateAccountView {
    private JButton deleteButton;
    private AccountHolder accountHolder;

    static public String cardId(){
        return "ModifyAccountView";
    }

    public ModifyAccountView(CUSTController controller) {
        super(controller);

        titleLabel.setText("Account Details");
        deleteButton = new JButton("Delete Account");

        GroupLayout.ParallelGroup horizontal = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();

        addTitle(horizontal, vertical);

        horizontal.addComponent(deleteButton);
        vertical.addComponent(deleteButton);
        vertical.addGap(20);

        addAllFields(horizontal, vertical, true);

        layout.setHorizontalGroup(horizontal);
        layout.setVerticalGroup(vertical);

        backButton.addActionListener(e->controller.goToAccountHolderManagerScreen());
        deleteButton.addActionListener(e->deleteButtonPressed());
        send1stReminderButton.addActionListener(e->sendReminder(true));
        send2ndReminderButton.addActionListener(e->sendReminder(false));
    }

    public void sendReminder(boolean firstReminder){
        controller.sendReminder(firstReminder, accountHolder);
        if (firstReminder)
            send1stReminderButton.setVisible(false);
        else
            send2ndReminderButton.setVisible(false);
    }

    public void fillAccountData(AccountHolder data){
        accountHolder = data;

        idComp.setFieldData(data.getAccountId());
        nameComp.setFieldData(data.getName());
        addressComp.setFieldData(data.getAddress());
        phoneNumberComp.setFieldData(data.getPhoneNumber());
        emailComp.setFieldData(data.getEmail());
        balanceComp.setFieldData(Double.toString(data.getBalance()));
        balanceLimitComp.setFieldData(Double.toString(data.getBalanceLimit()));
        discountTypeComp.setFieldData(data.getDiscountType());

        fixedDiscountComp.setFieldData(Double.toString(data.getFixedDiscount()));
        tier1ThresholdComp.setFieldData(Integer.toString(data.getTier1Threshold()));
        tier2ThresholdComp.setFieldData(Integer.toString(data.getTier2Threshold()));
        tier1DiscountComp.setFieldData(Double.toString(data.getTier1Discount()));
        tier2DiscountComp.setFieldData(Double.toString(data.getTier2Discount()));
        tier3DiscountComp.setFieldData(Double.toString(data.getTier3Discount()));

        showCorrectDiscounts();

        statusComp.setFieldData(data.getStatus().toString());
        if (data.getStatus().equals(AccountHolder.AccountStatus.IN_DEFAULT))
            statusComp.setAsModifiable();

        status1stComp.setFieldData(data.getStatus1stReminder().toString());
        if (data.getStatus1stReminder().equals(AccountHolder.ReminderStatus.DUE))
            send1stReminderButton.setVisible(true);
        else
            send1stReminderButton.setVisible(false);

        status2ndComp.setFieldData(data.getStatus2ndReminder().toString());
        if (data.getStatus2ndReminder().equals(AccountHolder.ReminderStatus.DUE))
            send2ndReminderButton.setVisible(true);
        else
            send2ndReminderButton.setVisible(false);
    }

    @Override
    public void modifyAccountField(String field, String value){
        showCorrectDiscounts();
        controller.modifyAccountField(idComp.getData(), field, value);
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

    private void deleteButtonPressed(){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Do you want to delete this account?", "Are you sure?", dialogButton);
        if (dialogResult == 0) {
            controller.deleteAccount(idComp.getData());
            controller.goToAccountHolderManagerScreen();
        }
    }
}
