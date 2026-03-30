package cust.view;

import cust.controller.CUSTController;
import cust.model.AccountHolder;
import cust.model.Order;
import cust.model.OrderItem;
import custom.CTable;
import custom.TitleLabel;

import javax.swing.*;
import java.util.ArrayList;

public class SeeOrdersView extends JPanel {
    private CUSTController controller;

    //Swing Objects
    private TitleLabel titleLabel;
    private JLabel infoLabel;
    private JButton backButton;
    private JButton seeOrdersButton;
    private CTable accountsTable;
    private CTable ordersTable;

    static public String cardId(){
        return "SeeOrdersView";
    }

    public SeeOrdersView(CUSTController controller) {
        this.controller = controller;

        backButton = new JButton("Back to Main Menu");
        add(new JLabel("Account Holders"));
        accountsTable = new CTable(AccountHolder.accountColumnId());
        seeOrdersButton = new JButton("See Account's Orders");
        add(new JLabel("Orders"));
        ordersTable = new CTable(Order.ordersByAccountColumnId());
        titleLabel = new TitleLabel("Orders");
        infoLabel = new JLabel();

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(backButton)
                .addComponent(accountsTable.getScrollPane())
                .addComponent(seeOrdersButton)
                .addComponent(infoLabel)
                .addComponent(ordersTable.getScrollPane())
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(40)
                .addComponent(backButton)
                .addGap(40)
                .addComponent(accountsTable.getScrollPane(), 100, 100, 100)
                .addGap(20)
                .addComponent(seeOrdersButton)
                .addComponent(infoLabel)
                .addComponent(ordersTable.getScrollPane(), 100, 200, 200)
        );

        backButton.addActionListener(e->controller.goToOrderManagerScreen());
        seeOrdersButton.addActionListener(e->populateOrders());
    }

    public void populateAccounts(){
        accountsTable.removeTableElements();

        ArrayList<AccountHolder> accountHolders = controller.getAccountHolders();
        for (AccountHolder a : accountHolders){
            accountsTable.addRow(a.accountRowData());
        }
    }

    /// //////////////// PRIVATE //////////////////
    private void populateOrders(){
        if (accountsTable.getSelectedRow() == -1){
            //TODO message
            return;
        }

        ordersTable.removeTableElements();

        ArrayList<Order> orders = controller.getOrdersByAccount(accountsTable.getSelectedRowColumn(0));
        for (Order o : orders){
            ordersTable.addRow(o.getOrderIdRowData());
            for(OrderItem i : o.getItemsOrdered()){
                ordersTable.addRow(i.getOrderedItemRowData());
            }
        }
    }
    /// ///////////////////////////////////////////
}
