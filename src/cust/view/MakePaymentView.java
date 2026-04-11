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

public class MakePaymentView extends JPanel {
    private CUSTController controller;
    private AccountHolder accountHolder;
    private Order order;
    private double total = 0.0;

    //Swing Objects
    private TitleLabel titleLabel;
    private JLabel accountLabel;
    private JLabel orderLabel;
    private JButton backToOrdersButton;
    private JButton finishPaymentButton;
    private CTable cartTable;
    private JLabel totalLabel;
    private JLabel infoLabel;
    private JLabel orderDateLabel;

    //Payment objects
    private JComboBox<String> cardTypeComboBox;
    private JLabel paymentDetailsLabel;
    private JTextField creditCardTextField;
    private JTextField securityCodeTextField;
    private JTextField expiryDateTextField;
    private JLabel shipAddressDataLabel;
    private JLabel creditCardLabel;
    private JLabel securityCodeLabel;
    private JLabel expiryDateLabel;
    private JLabel cardTypeLabel;

    static public String cardId(){
        return "MakePaymentView";
    }
    public MakePaymentView(CUSTController controller) {
        this.controller = controller;

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        titleLabel = new TitleLabel("Cart");
        backToOrdersButton = new JButton("Back to Order Manager");
        finishPaymentButton = new JButton("Finish Payment");
        infoLabel = new JLabel();
        totalLabel = new JLabel();
        totalLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        cartTable = new CTable(OrderItem.cartItemColumnId());
        accountLabel = new JLabel();
        accountLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        orderLabel = new JLabel();
        orderLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        JLabel shippingAddressLabel = new JLabel("Shipping Address:");
        shipAddressDataLabel = new JLabel();

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

        JLabel dateLabel = new JLabel("Order Date:");
        orderDateLabel = new JLabel();

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(accountLabel)
                .addComponent(backToOrdersButton)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(shippingAddressLabel)
                        .addComponent(shipAddressDataLabel, 300, 300, 300)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(dateLabel)
                        .addComponent(orderDateLabel, 75, 75, 75)
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
                .addComponent(infoLabel)
                .addComponent(cartTable.getScrollPane())
                .addComponent(totalLabel)
                .addComponent(finishPaymentButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(20)
                .addComponent(accountLabel)
                .addGap(20)
                .addComponent(backToOrdersButton)
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(shippingAddressLabel)
                        .addComponent(shipAddressDataLabel)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(dateLabel)
                        .addComponent(orderDateLabel)
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
                .addComponent(infoLabel)
                .addComponent(cartTable.getScrollPane(), 100, 100, 100)
                .addComponent(totalLabel)
                .addComponent(finishPaymentButton)
        );

        //creates the listener for the button to change the view back to the catalogue
        backToOrdersButton.addActionListener(e->controller.goToManageAccountOrdersScreen());
        finishPaymentButton.addActionListener(e-> finishPayment());
    }

    public void populateTable(ArrayList<OrderItem> cartItems){
        infoLabel.setText("");
        totalLabel.setText("");

        cartTable.removeTableElements();
        for(OrderItem i : cartItems){
            cartTable.addRow(i.getItemRowData());
            total += i.getTotal();
        }

        showGrandTotal();
    }

    public void fillOrderDetails(AccountHolder account, Order order) {
        accountLabel.setText("Account Name: " + account.getName());
        accountHolder = account;
        shipAddressDataLabel.setText(order.getShippingAddress());
        orderDateLabel.setText(order.getOrderDate());
        this.order = order;
    }

    private void finishPayment(){
        if (shipAddressDataLabel.getText().isEmpty()){
            infoLabel.setText("You must enter a shipping address");
            return;
        }

        if (orderDateLabel.getText().isEmpty()){
            infoLabel.setText("You must enter a order date");
            return;
        }else{
            try{
                LocalDate.parse(orderDateLabel.getText());
            } catch (Exception ex) {
                infoLabel.setText("That is not a valid order date (yyyy-mm-dd)");
                return;
            }
        }

        String creditCardNumber = creditCardTextField.getText().replaceAll("\\s+","");
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

        cartTable.removeTableElements();
        infoLabel.setText("Order Created");
        totalLabel.setText("");
        controller.finishPayment(
                accountHolder,
                order.getOrderID(),
                order.getTotalCost(),
                cardTypeComboBox.getSelectedItem().toString(),
                creditCardNumber,
                securityCodeTextField.getText(),
                expiryDateTextField.getText()
        );

        controller.goToOrderManagerScreen();
    }

    private void showGrandTotal(){
        totalLabel.setText("Grand Total: £" + order.getTotalCost());
    }

    private LocalItem getSelectedItem(){
        //this returns an Item created from the id of the table
        return controller.getItemByID(cartTable.getSelectedRowColumn(0));
    }
}
