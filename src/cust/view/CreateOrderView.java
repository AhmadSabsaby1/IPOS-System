package cust.view;

import cust.controller.CUSTController;
import cust.model.AccountHolder;
import cust.model.LocalItem;
import custom.CTable;
import custom.TitleLabel;

import javax.swing.*;
import java.util.ArrayList;

public class CreateOrderView extends JPanel {
    private CUSTController controller;
    private int accountIndex = -1;

    //Swing Objects
    private JButton addToCartButton;
    private JButton goToCartButton;
    private JButton backButton;
    private JButton removeSearch;
    private CTable catalogueTable;
    private CTable accountTable;
    private JLabel infoLabel;
    private TitleLabel titleLabel;

    private JTextField searchTextField;
    private JComboBox<String> fieldListComboBox;
    private JButton searchButton;

    static public String cardId(){
        return "CreateOrderView";
    }

    public CreateOrderView(CUSTController controller) {
        this.controller = controller;

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        titleLabel = new TitleLabel("Create an Order");
        backButton = new JButton("Back to Main Menu");
        goToCartButton = new JButton("See Cart");
        addToCartButton = new JButton("Add To Cart");
        infoLabel = new JLabel();


        //search bar
        searchTextField = new JTextField(10);
        fieldListComboBox = new JComboBox<>(new String[]{LocalItem.DESCRIPTION, LocalItem.ITEM_ID});
        searchButton = new JButton("Search");
        removeSearch = new JButton("Remove Search");

        catalogueTable = new CTable(LocalItem.catalogueColumnId());
        accountTable = new CTable(AccountHolder.accountColumnId());

        JLabel selectAccountLabel = new JLabel("Select an Account");

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton)
                        .addGap(50)
                        .addComponent(goToCartButton)
                )
                .addComponent(selectAccountLabel)
                .addComponent(accountTable.getScrollPane())
                .addGroup(layout.createSequentialGroup()
                        .addComponent(searchButton, 100, 100, 100)
                        .addComponent(searchTextField, 200, 200, 200)
                        .addComponent(fieldListComboBox, 100, 100, 100)
                        .addComponent(removeSearch)
                )
                .addComponent(catalogueTable.getScrollPane())
                .addComponent(infoLabel)
                .addComponent(addToCartButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(backButton)
                        .addComponent(goToCartButton)
                )
                .addGap(30)
                .addComponent(selectAccountLabel)
                .addComponent(accountTable.getScrollPane(), 100, 100, 100)
                .addGap(30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(searchButton)
                        .addComponent(searchTextField)
                        .addComponent(fieldListComboBox)
                        .addComponent(removeSearch)
                )
                .addComponent(catalogueTable.getScrollPane(), 100, 200, 200)
                .addComponent(infoLabel)
                .addComponent(addToCartButton)
        );

        //sets the listeners for the buttons
        addToCartButton.addActionListener(e -> addToCart());
        goToCartButton.addActionListener(e -> goToCart());
        backButton.addActionListener(e -> {
            infoLabel.setText("");
            accountIndex = -1;
            controller.removeAllCartItems();
            controller.goToOrderManagerScreen();
        });

        searchButton.addActionListener(e -> search());
        removeSearch.addActionListener(e -> removeSearch());
    }

    public void populateCatalogue(ArrayList<LocalItem> items){
        //puts every element of the item list in the table
        catalogueTable.removeTableElements();

        for (LocalItem item : items){
            catalogueTable.addRow(item.catalogueRowData());
        }
    }

    public void populateAccounts(ArrayList<AccountHolder> accounts){
        accountTable.removeTableElements();

        accountTable.addRow(AccountHolder.occasionalRowData());

        for (AccountHolder a : accounts){
            accountTable.addRow(a.accountRowData());
        }

        if (accountIndex != -1){
            accountTable.setRowSelectionInterval(accountIndex, accountIndex);
        }
    }

    public void setAccountIndex(int index){
        accountIndex = index;
    }

    /// ///////////// PRIVATES /////////////////

    private void goToCart(){
        if (accountTable.getSelectedRow() == -1){
            infoLabel.setText("You must select an account holder");
            return;
        }else if (controller.isCartEmpty()){
            infoLabel.setText("The cart is empty");
            return;
        }

        if (!accountTable.getSelectedRowColumn(2).equals(AccountHolder.AccountStatus.NORMAL.toString()) && !accountTable.getSelectedRowColumn(2).isEmpty()) {
            infoLabel.setText("The selected account is currently " + accountTable.getSelectedRowColumn(2) + ". No orders can be made");
            return;
        }

        if (accountIndex != accountTable.getSelectedRow()){
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "The items where added to a different account. Do you want to remove ALL items currently in the cart?", "Are you sure?", dialogButton);
            if(dialogResult == 0) {
                controller.removeAllCartItems();
                infoLabel.setText("All items in the cart removed");
            }
            return;
        }

        infoLabel.setText("");
        accountIndex = accountTable.getSelectedRow();

        controller.goToCartScreen(accountTable.getSelectedRowColumn(0));
    }

    private void removeSearch(){
        if (searchTextField.getText().isEmpty())
            return;

        searchTextField.setText("");
        populateCatalogue(controller.getCatalogue());
    }

    private void search(){
        if (searchTextField.getText().isEmpty())
            return;

        populateCatalogue(controller.searchByField(fieldListComboBox.getSelectedItem().toString(), searchTextField.getText()));
    }

    private void addToCart(){
        if (!accountTable.getSelectedRowColumn(2).equals(AccountHolder.AccountStatus.NORMAL.toString()) && !accountTable.getSelectedRowColumn(2).isEmpty()) {
            infoLabel.setText("The selected account is currently " + accountTable.getSelectedRowColumn(2) + ". No orders can be made");
            return;
        }

        if (catalogueTable.getSelectedRow() == -1) {
            infoLabel.setText("You must select an item to be added.");
            return;
        }

        LocalItem item = getSelectedItem();
        if (item == null) //it should never be null, but just in case
            return;

        //we check if the item the user wants to add to the cart is already in
        if (controller.cartItemExists(item.getId())){
            infoLabel.setText("Item already added to the cart");
            return;
        }

        //this creates a pop-up with a field to put the quantity in. Very quick and dirty, but it works.
        String quantityInput = JOptionPane.showInputDialog("Enter quantity");
        if (quantityInput == null || quantityInput.isEmpty()) {
            //input cancelled or left empty
            return;
        }

        int quantity;
        //we must do a try-catch to check if the parseInt is able to transform
        // the input the user has introduced into an int. If it can't, then the
        // user introduced the input wrong
        try{
            quantity = Integer.parseInt(quantityInput);
        }catch (NumberFormatException ex){
            infoLabel.setText(quantityInput + " is not a valid quantity.");
            return;
        }

        //check if the user introduced a 0 or a negative number
        if (quantity < 1){
            infoLabel.setText("You must enter a positive quantity of items.");
            return;
        }

        if(accountIndex == -1){
            accountIndex = accountTable.getSelectedRow();
        }else if (accountIndex != accountTable.getSelectedRow()){
            controller.removeAllCartItems();
            accountIndex = accountTable.getSelectedRow();
        }

        //everything is ok, so we add the item to the cart
        controller.addToCart(catalogueTable.getSelectedRowColumn(0), quantity);
        infoLabel.setText(quantity + " units of " + catalogueTable.getSelectedRowColumn(1) + " added to the cart");
    }

    private LocalItem getSelectedItem(){
        //this returns an Item created from the id of the table
        return controller.getItemByID(catalogueTable.getSelectedRowColumn(0));
    }
}
