package cust.view;

import cust.controller.CUSTController;
import cust.model.Order;
import cust.model.OrderItem;
import custom.CTable;
import custom.TitleLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SeeAllOrdersView extends JPanel {
    private CUSTController controller;
    private ArrayList<Order> orders;

    private TitleLabel titleLabel;
    private JLabel infoLabel;
    private JLabel shippingLabel;
    private JLabel paymentTypeLabel;
    private JLabel cardTypeLabel;
    private JLabel firstFourLabel;
    private JLabel lastFourLabel;
    private JButton backButton;
    private JButton seeAllDetailsButton;
    private CTable ordersTable;

    public static String cardId(){
        return "SeeAllOrdersView";
    }
    public SeeAllOrdersView(CUSTController controller) {
        this.controller = controller;

        titleLabel = new TitleLabel("All Orders");
        infoLabel = new JLabel();
        backButton = new JButton("Back to Main Menu");
        seeAllDetailsButton = new JButton("See Additional Details");
        shippingLabel = new JLabel();
        shippingLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        paymentTypeLabel = new JLabel();
        paymentTypeLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        cardTypeLabel = new JLabel();
        cardTypeLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        firstFourLabel = new JLabel();
        firstFourLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        lastFourLabel = new JLabel();
        lastFourLabel.setFont(new Font("Tahoma", Font.BOLD, 18));

        ordersTable = new CTable(Order.ordersByAccountColumnId());

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createBevelBorder(1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(shippingLabel);
        panel.add(paymentTypeLabel);
        panel.add(cardTypeLabel);
        panel.add(firstFourLabel);
        panel.add(lastFourLabel);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(backButton)
                .addComponent(infoLabel)
                .addComponent(ordersTable.getScrollPane())
                .addComponent(seeAllDetailsButton)
                .addComponent(panel, 600, 600, 600)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(40)
                .addComponent(backButton)
                .addGap(40)
                .addComponent(infoLabel)
                .addComponent(ordersTable.getScrollPane(), 100, 200, 200)
                .addComponent(seeAllDetailsButton)
                .addComponent(panel, 130, 130, 130)
        );

        seeAllDetailsButton.addActionListener(e -> seeAllDetails());
        backButton.addActionListener(e->controller.goToOrderManagerScreen());
    }

    public void populateTable(ArrayList<Order> orders){
        ordersTable.removeTableElements();
        this.orders = orders;

        for (Order o : orders){
            ordersTable.addRow(o.getOrderIdRowData());
            for (OrderItem i : o.getItemsOrdered()){
                ordersTable.addRow(i.getOrderedItemRowData());
            }
        }
    }

    public void seeAllDetails(){
        if (ordersTable.getSelectedRowColumn(0).isEmpty()){
            infoLabel.setText("Please select an order id");
            return;
        }

        for (Order o : orders){
            if (o.getOrderID().equals(ordersTable.getSelectedRowColumn(0))){
                shippingLabel.setText("Shipping Address: " + o.getShippingAddress());
                cardTypeLabel.setText("Card Type: " + o.getCardType().toString());
                paymentTypeLabel.setText("Payment Type: " + o.getPaymentType().toString());
                firstFourLabel.setText("Card's First Four Digits: " + o.getFirstFour());
                lastFourLabel.setText("Card's Last Four Digits: " + o.getLastFour());
            }
        }
    }
}
