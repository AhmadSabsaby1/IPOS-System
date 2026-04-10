package ord.view;

import custom.CTable;
import custom.TitleLabel;
import ord.controller.ORDController;
import ord.model.Order;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PreviousOrdersView extends JPanel {
    private ORDController controller;

    //Swing Objects
    private TitleLabel titleLabel;
    private JButton backButton;
    private CTable ordersTable;
    private JLabel totalsLabel;

    static public String cardId(){
        return "PreviousOrdersView";
    }

    public PreviousOrdersView(ORDController controller) {
        this.controller = controller;

        titleLabel = new TitleLabel("See Previous Orders");
        backButton = new JButton("Back to Main Menu");
        totalsLabel = new JLabel();
        totalsLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        ordersTable = new CTable(Order.previousOrdersColumnId());

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(backButton)
                .addComponent(ordersTable.getScrollPane())
                .addComponent(totalsLabel)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(40)
                .addComponent(backButton)
                .addGap(40)
                .addComponent(ordersTable.getScrollPane(), 100, 200, 200)
                .addComponent(totalsLabel)
        );

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
