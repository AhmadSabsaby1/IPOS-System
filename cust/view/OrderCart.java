package cust.view;

import cust.controller.CUSTController;
import cust.model.AccountHolder;
import cust.model.LocalItem;
import cust.model.OrderItem;
import custom.CTable;
import custom.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OrderCart extends JPanel {
    private CUSTController controller;
    private String accountId;

    //Swing Objects
    private TitleLabel titleLabel;
    private JLabel accountLabel;
    private JButton backToCatalogueButton;
    private JButton createOrderButton;
    private JButton clearCartButton;
    private JButton removeItemButton;
    private JButton changeQuantityButton;
    private CTable cartTable;
    private JLabel totalLabel;
    private JLabel infoLabel;

    static public String cardId(){
        return "OrderCartView";
    }
    public OrderCart(CUSTController controller) {
        this.controller = controller;

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        //creates the button to go back to the catalogue
        titleLabel = new TitleLabel("Cart");
        backToCatalogueButton = new JButton("Back to the Catalogue");
        createOrderButton = new JButton("Create Order");
        clearCartButton = new JButton("Clear Cart");
        removeItemButton = new JButton("Remove Item");
        changeQuantityButton = new JButton("Change Quantity");
        infoLabel = new JLabel();
        totalLabel = new JLabel();
        cartTable = new CTable(OrderItem.cartItemColumnId());
        accountLabel = new JLabel();
        accountLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(accountLabel)
                .addComponent(backToCatalogueButton)
                .addComponent(clearCartButton)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(changeQuantityButton)
                        .addComponent(removeItemButton)
                )
                .addComponent(cartTable.getScrollPane())
                .addComponent(totalLabel)
                .addComponent(createOrderButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(20)
                .addComponent(accountLabel)
                .addGap(20)
                .addComponent(backToCatalogueButton)
                .addGap(40)
                .addComponent(clearCartButton)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(changeQuantityButton)
                        .addComponent(removeItemButton)
                )
                .addComponent(cartTable.getScrollPane(), 100, 100, 100)
                .addComponent(totalLabel)
                .addComponent(createOrderButton)
        );

        //creates the listener for the button to change the view back to the catalogue
        backToCatalogueButton.addActionListener(e -> controller.goToCreateOrderScreen());
        createOrderButton.addActionListener(e -> createOrder());
        clearCartButton.addActionListener(e -> clearCart());
        removeItemButton.addActionListener(e -> removeItem());
        changeQuantityButton.addActionListener(e -> changeQuantity());
    }

    public void populateTable(ArrayList<OrderItem> cartItems){
        infoLabel.setText("");
        totalLabel.setText("");

        //we remove the elements so it won't add the cart list to the table
        // each time we enter this view
        cartTable.removeTableElements();
        for(OrderItem i : cartItems){
            cartTable.addRow(i.getItemRowData());
        }

        if (!cartItems.isEmpty())
            calculateGrandTotal();
    }

    public void fillAccountDetails(AccountHolder account) {
        accountLabel.setText("Account Name: " + account.getName());
        accountId = account.getAccountId();
    }

    /// ////////// PRIVATE ///////////////////////
    private void changeQuantity(){
        if (cartTable.getSelectedRow() == -1){
            infoLabel.setText("No item selected");
            return;
        }

        int quantity;
        LocalItem item = getSelectedItem();
        if (item == null) //it should never be null, but just in case
            return;

        //this creates a pop-up with a field to put the quantity in. Very quick and dirty, but it works.
        String quantityInput = JOptionPane.showInputDialog("Enter quantity");
        if (quantityInput == null || quantityInput.isEmpty()) {
            //input cancelled or left empty
            return;
        }

        //we must do a try-catch to check if the parseInt is able to transform
        // the input the user has introduced into an int. If it can't, then the
        // user introduced the input wrong
        try{
            quantity = Integer.parseInt(quantityInput);
        }catch (NumberFormatException ex){
            infoLabel.setText(quantityInput + " is not a valid quantity");
            return;
        }

        //check if the user introduced a 0 or a negative number
        if (quantity < 1){
            infoLabel.setText("You must enter a positive quantity of items");
            return;
        }

        controller.changeCartItemQuantity(item.getId(), quantity);
        populateTable(controller.getCartList());
        infoLabel.setText("Quantity of " + item.getDescription() + " updated to " + quantity);
    }

    private void clearCart(){
        if (cartTable.isEmpty())
            return;

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Do you want to remove ALL items in the cart?", "Are you sure?", dialogButton);
        if(dialogResult == 0) {
            cartTable.removeTableElements();
            controller.removeAllCartItems();
            infoLabel.setText("");
            totalLabel.setText("");
            controller.goToCreateOrderScreen();
        }
    }

    private void removeItem(){
        if(cartTable.getSelectedRow() == -1){
            infoLabel.setText("No item selected");
            return;
        }

        String itemName = cartTable.getSelectedRowColumn(1);

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Do you want to remove " + itemName + " from the cart?", "Are you sure?", dialogButton);
        if(dialogResult == 0) {
            controller.removeFromCart(cartTable.getSelectedRowColumn(0));
            if (controller.isCartEmpty()){
                infoLabel.setText("");
                totalLabel.setText("");
                controller.goToCreateOrderScreen();
            }

            infoLabel.setText(itemName + " removed from cart");
            populateTable(controller.getCartList());
        }
    }

    private void calculateGrandTotal(){
        totalLabel.setText("Grand Total: " + controller.calculateGrandTotal());
    }

    private void createOrder(){
        if (cartTable.isEmpty()){
            infoLabel.setText("There are no items in the cart");
            totalLabel.setText("");
            return;
        }

        cartTable.removeTableElements();
        infoLabel.setText("Order Created");
        totalLabel.setText("");
        controller.createOrder(accountId);
    }

    private LocalItem getSelectedItem(){
        //this returns an Item created from the id of the table
        return controller.getItemByID(cartTable.getSelectedRowColumn(0));
    }

}
