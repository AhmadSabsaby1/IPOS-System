package ord.view;

import custom.CTable;
import ord.controller.ORDController;
import ord.model.CartItem;
import ord.model.Item;

import javax.swing.*;
import java.util.ArrayList;

public class CartView extends JPanel {
    private ORDController controller;

    //Swing Objects
    private JButton backToCatalogueButton;
    private JButton createOrderButton;
    private JButton clearCartButton;
    private JButton removeItemButton;
    private JButton changeQuantityButton;
    private CTable cartTable;
    private JLabel totalLabel;
    private JLabel infoLabel;

    static public String cardId(){
        return "CartView";
    }

    public CartView(ORDController controller) {
        this.controller = controller;

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        //creates the button to go back to the catalogue
        backToCatalogueButton = new JButton("Back to the Catalogue");
        createOrderButton = new JButton("Create Order");
        clearCartButton = new JButton("Clear Cart");
        removeItemButton = new JButton("Remove Item");
        changeQuantityButton = new JButton("Change Quantity");
        infoLabel = new JLabel();
        totalLabel = new JLabel();
        cartTable = new CTable(CartItem.cartItemColumndId());

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
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
                .addComponent(backToCatalogueButton)
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
        backToCatalogueButton.addActionListener(e -> controller.goToCatalogueScreen());
        createOrderButton.addActionListener(e -> createOrder());
        clearCartButton.addActionListener(e -> clearCart());
        removeItemButton.addActionListener(e -> removeItem());
        changeQuantityButton.addActionListener(e -> changeQuantity());
    }

    private void changeQuantity(){
        if (cartTable.getSelectedRow() == -1){
            infoLabel.setText("No item selected");
            return;
        }

        int quantity;
        Item item = getSelectedItem();
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
            controller.goToCatalogueScreen();
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
                controller.goToCatalogueScreen();
            }

            infoLabel.setText(itemName + " removed from cart");
            populateTable(controller.getCartList());
        }
    }

    public void populateTable(ArrayList<CartItem> cartItems){
        infoLabel.setText("");
        totalLabel.setText("");

        //we remove the elements so it won't add the cart list to the table
        // each time we enter this view
        cartTable.removeTableElements();
        for(CartItem i : cartItems){
            cartTable.addRow(i.rowData());
        }

        if (!cartItems.isEmpty())
            calculateGrandTotal();
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
        controller.createOrder();
    }

    private Item getSelectedItem(){
        //this returns an Item created from the id of the table
        return controller.getItemByID(cartTable.getSelectedRowColumn(0));
    }
}
