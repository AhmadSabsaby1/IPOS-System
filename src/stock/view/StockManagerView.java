package stock.view;

import stock.controller.STOCKController;
import stock.model.StockItem;
import custom.TitleLabel;
import custom.CTable;

import javax.swing.*;
import java.util.ArrayList;

public class StockManagerView extends JPanel {

    private STOCKController controller;
    private TitleLabel title;
    private JButton backBtn;
    private JButton addItemBtn;
    private JButton addStockBtn;
    private JButton deleteBtn;
    private CTable table;

    public static String cardId() { return "StockManagerView"; }

    public StockManagerView(STOCKController controller) {
        this.controller = controller;

        title = new TitleLabel("Stock Manager");
        backBtn = new JButton("Back");
        addItemBtn = new JButton("Add New Item");
        addStockBtn = new JButton("Add Stock");
        deleteBtn = new JButton("Delete Item");
        table = new CTable(StockItem.cols());

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(title)
                .addComponent(table.getScrollPane())
                .addGroup(layout.createSequentialGroup()
                        .addComponent(backBtn)
                        .addComponent(addItemBtn)
                        .addComponent(addStockBtn)
                        .addComponent(deleteBtn)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(title)
                .addGap(10)
                .addComponent(table.getScrollPane(), 350, 350, 350)
                .addGap(10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(backBtn, 40, 40, 40)
                        .addComponent(addItemBtn, 40, 40, 40)
                        .addComponent(addStockBtn, 40, 40, 40)
                        .addComponent(deleteBtn, 40, 40, 40)
                )
        );

        backBtn.addActionListener(e -> controller.goToHub());
        addItemBtn.addActionListener(e -> controller.goToAddNewItem());
        addStockBtn.addActionListener(e -> {
            String id = table.getSelectedRowColumn(0);
            if (!id.isEmpty()) controller.goToAddStock(id);
            else JOptionPane.showMessageDialog(this, "Select an item first.");
        });
        deleteBtn.addActionListener(e -> {
            String id = table.getSelectedRowColumn(0);
            if (!id.isEmpty()) controller.deleteItem(id);
            else JOptionPane.showMessageDialog(this, "Select an item first.");
        });
    }

    public void populate(ArrayList<StockItem> items) {
        table.removeTableElements();
        for (StockItem item : items)
            table.addRow(item.rowData());
    }
}