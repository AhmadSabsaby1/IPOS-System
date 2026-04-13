package ord.view;

import custom.CTable;
import custom.TitleLabel;
import ord.controller.ORDController;
import ord.model.OrderSA;

import javax.swing.*;
import java.util.ArrayList;

public class OrderProgressView extends JPanel {
    private ORDController controller;

    //Swing Objects
    private TitleLabel titleLabel;
    private JButton backButton;
    private CTable progressTable;

    static public String cardId(){
        return "OrderProgressView";
    }

    public OrderProgressView(ORDController controller) {
        this.controller = controller;

        titleLabel = new TitleLabel("See Orders Progress");
        backButton = new JButton("Back to Main Menu");
        progressTable = new CTable(OrderSA.orderProgressColumnId());

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addComponent(backButton)
                .addComponent(progressTable.getScrollPane())
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(40)
                .addComponent(backButton)
                .addGap(40)
                .addComponent(progressTable.getScrollPane(), 100, 200, 200)
        );

        backButton.addActionListener(e -> controller.goToHubScreen());
    }

    public void populateTable(ArrayList<OrderSA> orders){
        progressTable.removeTableElements();
        if (orders.isEmpty())
            return;

        double totalAmount = 0;
        int nDispatched = 0;
        int nDelivered = 0;
        int nPaid = 0;

        for (OrderSA order : orders){
            progressTable.addRow(order.getOrderProgressRowData());
            totalAmount += order.getAmountDue();
        }

        //progressTable.addRow(new String[] {"TOTAL:", Integer.toString(orders.size()), Double.toString(totalAmount), Integer.toString(nDispatched), Integer.toString(nDelivered), Integer.toString(nPaid)});
    }
}
