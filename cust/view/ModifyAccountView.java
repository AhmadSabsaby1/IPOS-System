package cust.view;

import cust.controller.CUSTController;
import cust.model.AccountHolder;

import javax.swing.*;

public class ModifyAccountView extends ModifyCreateAccountView {
    private JButton deleteButton;

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

        addAllFields(horizontal, vertical, true);

        layout.setHorizontalGroup(horizontal);
        layout.setVerticalGroup(vertical);

        backButton.addActionListener(e->controller.goToAccountHolderManagerScreen());
        deleteButton.addActionListener(e->deleteButtonPressed());
    }

    public void fillAccountData(AccountHolder data){
        idComp.setFieldData(data.getAccountId());
        nameComp.setFieldData(data.getName());
        addressComp.setFieldData(data.getAddress());
        cardTypeComp.setFieldData(data.getCardType());
        firstFourComp.setFieldData(data.getFirstFour());
        lastFourComp.setFieldData(data.getLastFour());
        expiryDateComp.setFieldData(data.getExpiryDate());
        balanceComp.setFieldData(Double.toString(data.getBalance()));
        discountTypeComp.setFieldData(data.getDiscountType());
        discountComp.setFieldData(Double.toString(data.getDiscount()));
        statusComp.setFieldData(data.getStatus());
        status1stComp.setFieldData(data.getStatus1stReminder().toString());
        status2ndComp.setFieldData(data.getStatus2ndReminder().toString());
    }

    @Override
    public void modifyAccountField(String field, String value){
        controller.modifyAccountField(idComp.getData(), field, value);
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
