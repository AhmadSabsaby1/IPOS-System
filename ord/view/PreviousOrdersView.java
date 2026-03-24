package ord.view;

import custom.CTable;
import ord.controller.ORDController;
import ord.model.Order;

import javax.swing.*;
import java.util.ArrayList;

public class PreviousOrdersView extends JPanel {
    private ORDController controller;

    //Swing Objects
    private JButton backButton;
    private CTable ordersTable;
    private JLabel totalsLabel;

    static public String cardId(){
        return "PreviousOrdersView";
    }

    public PreviousOrdersView(ORDController controller) {
        this.controller = controller;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        backButton = new JButton("Back to Main Menu");
        add(backButton);

        totalsLabel = new JLabel();
        add(totalsLabel);

        ordersTable = new CTable(Order.previousOrdersColumnId());
        add(ordersTable.getScrollPane());

        backButton.addActionListener(e -> controller.goToHubScreen());
    }

    public void populateOrdersTable(ArrayList<Order> orders) {
        ordersTable.removeTableElements();

        for (Order order : orders) {
            ordersTable.addRow(order.getPrevOrdersRowData());
            for (var i = 0; i < order.getItemsOrdered().size(); i++) {
                ordersTable.addRow(order.getPrevOrderItemRowData(i));
            }
        }
    }
}
