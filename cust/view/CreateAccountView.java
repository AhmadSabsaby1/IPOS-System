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

    public void removeAllData(){
        nameComp.removeData();
        addressComp.removeData();
        cardTypeComp.removeData();
        firstFourComp.removeData();
        lastFourComp.removeData();
        lastFourComp.removeData();
        expiryDateComp.removeData();
        balanceComp.removeData();
        discountTypeComp.removeData();
        discountComp.removeData();
        statusComp.removeData();
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

        if (cardTypeComp.isBlank())
            blankComps.add(cardTypeComp);

        if (firstFourComp.isBlank())
            blankComps.add(firstFourComp);

        if (lastFourComp.isBlank())
            blankComps.add(lastFourComp);

        if (expiryDateComp.isBlank())
            blankComps.add(expiryDateComp);

        if (balanceComp.isBlank())
            blankComps.add(balanceComp);

        if (discountTypeComp.isBlank())
            blankComps.add(discountTypeComp);

        if (discountComp.isBlank())
            blankComps.add(discountComp);

        if (statusComp.isBlank())
            blankComps.add(statusComp);

        if (!blankComps.isEmpty()){
            infoLabel.setText("Some of the fields are blank: ");
            for (FieldComp field : blankComps){
                infoLabel.setText(infoLabel.getText() + field.getFieldName() + ", ");
            }
        }else{
            controller.createAccount(new AccountHolder(
                    nameComp.getData(),
                    addressComp.getData(),
                    cardTypeComp.getData(),
                    firstFourComp.getData(),
                    lastFourComp.getData(),
                    expiryDateComp.getData(),
                    Double.parseDouble(balanceComp.getData()),
                    discountTypeComp.getData(),
                    Double.parseDouble(discountComp.getData()),
                    statusComp.getData()
               ));
            removeAllData();
            controller.goToAccountHolderManagerScreen();
        }
    }
}