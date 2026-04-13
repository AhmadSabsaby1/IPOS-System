package cust.view;

import cust.controller.CUSTController;
import cust.model.AccountHolder;
import cust.model.AutomaticStatusChange;
import custom.CTable;
import custom.TitleLabel;

import javax.swing.*;
import java.util.ArrayList;

public class AccountManagerView extends JPanel {
    private CUSTController controller;

    //Swing Objects
    private TitleLabel titleLabel;
    private JLabel infoLabel;
    private JButton backButton;
    private JButton createAccountButton;
    private JButton seeAccountButton;
    private CTable accountTable;

    static public String cardId(){
        return "AccountHolderManagerView";
    }

    public AccountManagerView(CUSTController controller){
        this.controller = controller;

        titleLabel = new TitleLabel("Account Manager");
        infoLabel = new JLabel();
        backButton = new JButton("Back to Main Menu");
        createAccountButton = new JButton("Create and Account");
        seeAccountButton = new JButton("Manage an Account's Details");
        accountTable = new CTable(AccountHolder.accountColumnId());

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(backButton)
                .addComponent(createAccountButton)
                .addComponent(accountTable.getScrollPane())
                .addComponent(infoLabel)
                .addComponent(seeAccountButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(40)
                .addComponent(backButton)
                .addGap(40)
                .addComponent(createAccountButton)
                .addComponent(accountTable.getScrollPane(), 100, 200, 200)
                .addComponent(infoLabel)
                .addComponent(seeAccountButton)
        );

        backButton.addActionListener(e->controller.goToHubScreen());
        seeAccountButton.addActionListener(e->{
            if (accountTable.getSelectedRow() != -1) {
                infoLabel.setText("");
                controller.goToModifyAccountHolderScreen(accountTable.getSelectedRowColumn(0));
            }else
                infoLabel.setText("No account selected");
        });

        createAccountButton.addActionListener(e->controller.goToCreateAccountScreen());
    }

    public void populateAccountTable(ArrayList<AccountHolder> list){
        infoLabel.setText("");
        accountTable.removeTableElements();

        for (AccountHolder a : list){
            accountTable.addRow(a.accountRowData());
        }
    }
}
