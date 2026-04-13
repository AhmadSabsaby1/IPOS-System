package ord.view;

import custom.CTable;
import ord.controller.ORDController;
import ord.model.Order;

import javax.swing.*;
import java.util.ArrayList;

public class OrderProgressView extends JPanel {
    private ORDController controller;

    //Swing Objects
    private JButton backButton;
    private CTable progressTable;

    static public String cardID(){
        return "OrderProgressView";
    }

    public OrderProgressView(ORDController controller) {
        this.controller = controller;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        backButton = new JButton("Back to Main Menu");
        add(backButton);

        progressTable = new CTable(Order.orderProgressColumnId());
        add(progressTable.getScrollPane());

        backButton.addActionListener(e -> controller.goToHubScreen());
    }

    public void populateTable(ArrayList<Order> orders){
        progressTable.removeTableElements();
        if (orders.isEmpty())
            return;

        double totalAmount = 0;
        int nDispatched = 0;
        int nDelivered = 0;
        int nPaid = 0;

        for (Order order : orders){
            progressTable.addRow(order.getOrderProgressRowData());
            totalAmount += order.getCost();
        }

        progressTable.addRow(new String[] {"TOTAL:", Integer.toString(orders.size()), Double.toString(totalAmount), Integer.toString(nDispatched), Integer.toString(nDelivered), Integer.toString(nPaid)});
    }
}
