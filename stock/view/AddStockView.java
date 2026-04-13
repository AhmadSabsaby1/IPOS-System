package stock.view;

import stock.controller.STOCKController;
import stock.model.StockItem;
import custom.TitleLabel;

import javax.swing.*;

public class AddStockView extends JPanel {

    private STOCKController controller;
    private TitleLabel title;
    private JLabel itemInfo;
    private JLabel qtyLabel;
    private JTextField qtyField;
    private JButton confirmBtn;
    private JButton backBtn;
    private String currentId;

    public static String cardId() { return "AddStockView"; }

    public AddStockView(STOCKController controller) {
        this.controller = controller;

        title = new TitleLabel("Add Stock");
        itemInfo = new JLabel("Item: ");
        qtyLabel = new JLabel("Qty to add:");
        qtyField = new JTextField(10);
        confirmBtn = new JButton("Confirm");
        backBtn = new JButton("Back");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(title)
                .addComponent(itemInfo)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(qtyLabel)
                        .addComponent(qtyField)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(backBtn)
                        .addComponent(confirmBtn)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(title)
                .addGap(20)
                .addComponent(itemInfo)
                .addGap(10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(qtyLabel)
                        .addComponent(qtyField)
                )
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(backBtn, 40, 40, 40)
                        .addComponent(confirmBtn, 40, 40, 40)
                )
        );

        backBtn.addActionListener(e -> controller.goToStockManager());
        confirmBtn.addActionListener(e -> submit());
    }

    public void fill(StockItem item) {
        currentId = item.getId();
        itemInfo.setText("Item: " + item.getId() + " - " + item.getDesc()
                + "  (current stock: " + item.getQty() + ")");
        qtyField.setText("");
    }

    private void submit() {
        try {
            int n = Integer.parseInt(qtyField.getText().trim());
            if (n <= 0) { JOptionPane.showMessageDialog(this, "Qty must be > 0"); return; }
            controller.addQty(currentId, n);
            JOptionPane.showMessageDialog(this, "Stock updated!");
            controller.goToStockManager();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid number.");
        }
    }
}