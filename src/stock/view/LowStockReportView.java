package stock.view;

import stock.controller.STOCKController;
import stock.model.StockItem;
import custom.TitleLabel;
import custom.CTable;

import javax.swing.*;
import java.util.ArrayList;

public class LowStockReportView extends JPanel {

    private STOCKController controller;
    private TitleLabel title;
    private JButton backBtn;
    private CTable table;

    public static String cardId() { return "LowStockReportView"; }

    public LowStockReportView(STOCKController controller) {
        this.controller = controller;

        title = new TitleLabel("Low Stock Report");
        backBtn = new JButton("Back");
        table = new CTable(new String[]{"Item ID", "Description", "Stock", "Min Stock"});

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(title)
                .addComponent(table.getScrollPane())
                .addComponent(backBtn)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(title)
                .addGap(10)
                .addComponent(table.getScrollPane(), 350, 350, 350)
                .addGap(10)
                .addComponent(backBtn, 40, 40, 40)
        );

        backBtn.addActionListener(e -> controller.goToHub());
    }

    public void populate(ArrayList<StockItem> items) {
        table.removeTableElements();
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All items are above minimum stock.");
            return;
        }
        for (StockItem item : items)
            table.addRow(new String[]{item.getId(), item.getDesc(),
                    String.valueOf(item.getQty()), String.valueOf(item.getMinQty())});
    }
}