package cust.view;

import cust.controller.CUSTController;
import cust.model.AccountHolder;
import cust.model.LocalItem;
import cust.model.Order;
import cust.model.OrderItem;
import custom.CTable;
import custom.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class OrderCart extends JPanel {
    private final CUSTController controller;
    private AccountHolder accountHolder;
    private double totalCost;
    private double afterDiscount = 0.0;

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
    private JTextField orderDateTextField;

    //Payment objects
    private JComboBox<String> paymentTypeComboBox;
    private JComboBox<String> cardTypeComboBox;
    private JLabel paymentDetailsLabel;
    private JTextField creditCardTextField;
    private JTextField securityCodeTextField;
    private JTextField expiryDateTextField;
    private JTextField shippingAddressTextField;
    private JLabel creditCardLabel;
    private JLabel securityCodeLabel;
    private JLabel expiryDateLabel;
    private JLabel cardTypeLabel;
    private JLabel discountLabel;

    static public String cardId(){
        return "OrderCartView";
    }
    public OrderCart(CUSTController controller) {
        this.controller = controller;

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        titleLabel = new TitleLabel("Cart");
        backToCatalogueButton = new JButton("Back to the Catalogue");
        createOrderButton = new JButton("Create Order");
        clearCartButton = new JButton("Clear Cart");
        removeItemButton = new JButton("Remove Item");
        changeQuantityButton = new JButton("Change Quantity");
        infoLabel = new JLabel();
        totalLabel = new JLabel();
        totalLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        discountLabel = new JLabel();
        discountLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        discountLabel.setVisible(false);
        cartTable = new CTable(OrderItem.cartItemColumnId());
        accountLabel = new JLabel();
        accountLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        JLabel shippingAddressLabel = new JLabel("Shipping Address:");
        shippingAddressTextField = new JTextField();

        JLabel paymentTypeLabel = new JLabel("Payment Type:");
        paymentTypeComboBox = new JComboBox<>();
        cardTypeComboBox = new JComboBox<>(Order.CardType.getOptions());

        creditCardLabel = new JLabel("Credit Card Number:");
        securityCodeLabel = new JLabel("Security Code:");
        expiryDateLabel = new JLabel("Expiry Date:");
        cardTypeLabel = new JLabel("Card Type:");
        paymentDetailsLabel = new JLabel("Payment Details");
        paymentDetailsLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

        creditCardTextField = new JTextField();
        securityCodeTextField = new JTextField();
        expiryDateTextField = new JTextField();

        JLabel dateLabel = new JLabel("Order Date (yyyy-mm-dd):");
        orderDateTextField = new JTextField(LocalDate.now().toString());

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(accountLabel)
                .addComponent(backToCatalogueButton)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(shippingAddressLabel)
                        .addComponent(shippingAddressTextField, 300, 300, 300)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(dateLabel)
                        .addComponent(orderDateTextField, 75, 75, 75)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(paymentTypeLabel)
                        .addComponent(paymentTypeComboBox, 75, 75, 75)
                )
                .addComponent(paymentDetailsLabel)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(cardTypeLabel)
                        .addComponent(cardTypeComboBox, 75, 75, 75)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(creditCardLabel)
                        .addComponent(creditCardTextField, 150, 150, 150)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(securityCodeLabel)
                        .addComponent(securityCodeTextField, 75, 75, 75)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(expiryDateLabel)
                        .addComponent(expiryDateTextField, 75, 75, 75)
                )
                .addComponent(clearCartButton)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(changeQuantityButton)
                        .addComponent(removeItemButton)
                )
                .addComponent(infoLabel)
                .addComponent(cartTable.getScrollPane())
                .addComponent(totalLabel)
                .addComponent(discountLabel)
                .addComponent(createOrderButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(20)
                .addComponent(accountLabel)
                .addGap(20)
                .addComponent(backToCatalogueButton)
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(shippingAddressLabel)
                        .addComponent(shippingAddressTextField)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(dateLabel)
                        .addComponent(orderDateTextField)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(paymentTypeLabel)
                        .addComponent(paymentTypeComboBox)
                )
                .addGap(20)
                .addComponent(paymentDetailsLabel)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(cardTypeLabel)
                        .addComponent(cardTypeComboBox)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(creditCardLabel)
                        .addComponent(creditCardTextField)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(securityCodeLabel)
                        .addComponent(securityCodeTextField)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(expiryDateLabel)
                        .addComponent(expiryDateTextField)
                )
                .addGap(20)
                .addComponent(clearCartButton)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(changeQuantityButton)
                        .addComponent(removeItemButton)
                )
                .addComponent(infoLabel)
                .addComponent(cartTable.getScrollPane(), 100, 100, 100)
                .addComponent(totalLabel)
                .addComponent(discountLabel)
                .addComponent(createOrderButton)
        );

        //creates the listener for the button to change the view back to the catalogue
        backToCatalogueButton.addActionListener(e->controller.goToCreateOrderScreen());
        createOrderButton.addActionListener(e->createOrder());
        clearCartButton.addActionListener(e->clearCart());
        removeItemButton.addActionListener(e->removeItem());
        changeQuantityButton.addActionListener(e->changeQuantity());
        paymentTypeComboBox.addActionListener(e-> paymentTypeChanged());
    }

    private void paymentTypeChanged(){
        if (paymentTypeComboBox.getSelectedItem() == null)
            return;

        if (!paymentTypeComboBox.getSelectedItem().equals(Order.PaymentType.CARD.toString())){
            creditCardLabel.setVisible(false);
            securityCodeLabel.setVisible(false);
            expiryDateLabel.setVisible(false);
            creditCardTextField.setVisible(false);
            expiryDateTextField.setVisible(false);
            securityCodeTextField.setVisible(false);
            paymentDetailsLabel.setVisible(false);
            cardTypeLabel.setVisible(false);
            cardTypeComboBox.setVisible(false);
        }else{
            creditCardLabel.setVisible(true);
            securityCodeLabel.setVisible(true);
            expiryDateLabel.setVisible(true);
            creditCardTextField.setVisible(true);
            expiryDateTextField.setVisible(true);
            securityCodeTextField.setVisible(true);
            paymentDetailsLabel.setVisible(true);
            cardTypeLabel.setVisible(true);
            cardTypeComboBox.setVisible(true);
        }
    }

    public void populateTable(ArrayList<OrderItem> cartItems){
        infoLabel.setText("");
        totalLabel.setText("");
        discountLabel.setText("");
        afterDiscount = 0.0;
        totalCost = 0.0;

        //we remove the elements so it won't add the cart list to the table
        // each time we enter this view
        cartTable.removeTableElements();
        for(OrderItem i : cartItems){
            cartTable.addRow(i.getItemRowData());
        }

        if (!cartItems.isEmpty())
            calculateGrandTotal();
    }

    private void removeAllCartItems(){
        cartTable.removeTableElements();
        controller.removeAllCartItems();
    }

    public void fillAccountDetails(AccountHolder account) {
        accountLabel.setText("Account Name: " + account.getName());
        accountHolder = account;

        paymentTypeComboBox.removeAllItems();

        String[] options;
        if (account.isOccasional()) {
            options = Order.PaymentType.getOccasionalOptions();
        }else {
            options = Order.PaymentType.getAccountOptions();
        }

        for (String s : options){
            paymentTypeComboBox.addItem(s);
        }

        paymentTypeComboBox.repaint();
        paymentTypeComboBox.setSelectedIndex(0);

        paymentTypeChanged();
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
            removeAllCartItems();
            infoLabel.setText("");
            totalLabel.setText("");
            discountLabel.setText("");
            afterDiscount = 0.0;
            totalCost = 0.0;
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
                discountLabel.setText("");
                afterDiscount = 0.0;
                totalCost = 0.0;
                controller.goToCreateOrderScreen();
            }

            infoLabel.setText(itemName + " removed from cart");
            populateTable(controller.getCartList());
        }
    }

    private void calculateGrandTotal(){
        totalCost = controller.calculateGrandTotal();
        afterDiscount = 0.0;
        totalLabel.setText("Grand Total: £" + totalCost);
        if (accountHolder.getDiscountType().equals(AccountHolder.DiscountType.FIXED.toString())){
            discountLabel.setVisible(true);
            afterDiscount = accountHolder.calculateFixedDiscount(totalCost);
            discountLabel.setText("After Discount (" + accountHolder.getFixedDiscount() + "%): £" + afterDiscount);
        }
    }

    private void createOrder(){
        if (cartTable.isEmpty()){
            infoLabel.setText("There are no items in the cart");
            totalLabel.setText("");
            return;
        }

        if (shippingAddressTextField.getText().isEmpty()){
            infoLabel.setText("You must enter a shipping address");
            return;
        }

        if (orderDateTextField.getText().isEmpty()){
            infoLabel.setText("You must enter a order date");
            return;
        }else{
            try{
                LocalDate.parse(orderDateTextField.getText());
            } catch (Exception ex) {
                infoLabel.setText("That is not a valid order date (yyyy-mm-dd)");
                return;
            }
        }

        String creditCardNumber = creditCardTextField.getText().replaceAll("\\s+","");
        if (paymentTypeComboBox.getSelectedItem().equals(Order.PaymentType.CARD.toString())){
            if (creditCardTextField.getText().isEmpty()){
                infoLabel.setText("You must enter a credit card number");
                return;
            }else if (creditCardNumber.length() != 18 || Pattern.matches("[a-zA-Z]+", creditCardNumber)) {
                infoLabel.setText("The credit card number is not valid");
                return;
            }

            if (securityCodeTextField.getText().isEmpty()){
                infoLabel.setText("You must enter a security code");
                return;
            }else{
                try{
                    Integer.parseInt(securityCodeTextField.getText());
                } catch (Exception ex) {
                    infoLabel.setText("The security number is not valid");
                    return;
                }
            }

            if (expiryDateTextField.getText().isEmpty()){
                infoLabel.setText("You must enter an expiry date");
                return;
            }
        }

        cartTable.removeTableElements();
        infoLabel.setText("Order Created");
        totalLabel.setText("");

        boolean success = controller.createOrder(
                accountHolder,
                shippingAddressTextField.getText(),
                orderDateTextField.getText(),
                paymentTypeComboBox.getSelectedItem().toString(),
                cardTypeComboBox.getSelectedItem().toString(),
                creditCardNumber,
                securityCodeTextField.getText(),
                expiryDateTextField.getText(),
                afterDiscount > 0 ? afterDiscount : totalCost
        );

        if (success) {
            afterDiscount = 0.0;
            totalCost = 0.0;
            controller.goToOrderManagerScreen();
        }else{
            infoLabel.setText("ORDER CANCELLED: This order would exceed the balance limit of the account holder");
        }
    }

    private LocalItem getSelectedItem(){
        //this returns an Item created from the id of the table
        return controller.getItemByID(cartTable.getSelectedRowColumn(0));
    }
}
