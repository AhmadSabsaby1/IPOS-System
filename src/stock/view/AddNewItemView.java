package stock.view;

import stock.controller.STOCKController;
import stock.model.StockItem;
import custom.TitleLabel;

import javax.swing.*;

public class AddNewItemView extends JPanel {

    private STOCKController controller;
    private TitleLabel title;
    private JTextField idF, descF, typeF, unitF, packF, costF, markupF, qtyF, minF;
    private JButton confirmBtn, backBtn;

    // labels defined as variables to avoid freezing
    private JLabel idLbl = new JLabel("Item ID:");
    private JLabel descLbl = new JLabel("Description:");
    private JLabel typeLbl = new JLabel("Package Type:");
    private JLabel unitLbl = new JLabel("Unit:");
    private JLabel packLbl = new JLabel("Units/Pack:");
    private JLabel costLbl = new JLabel("Bulk Cost £:");
    private JLabel markupLbl = new JLabel("Markup %:");
    private JLabel qtyLbl = new JLabel("Stock Qty:");
    private JLabel minLbl = new JLabel("Min Stock:");

    public static String cardId() { return "AddNewItemView"; }

    public AddNewItemView(STOCKController controller) {
        this.controller = controller;

        title = new TitleLabel("Add New Stock Item");
        idF = new JTextField(15);
        descF = new JTextField(15);
        typeF = new JTextField(15);
        unitF = new JTextField(15);
        packF = new JTextField(15);
        costF = new JTextField(15);
        markupF = new JTextField(15);
        qtyF = new JTextField(15);
        minF = new JTextField(15);
        confirmBtn = new JButton("Add Item");
        backBtn = new JButton("Back");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(title)
                .addGroup(layout.createSequentialGroup().addComponent(idLbl).addComponent(idF))
                .addGroup(layout.createSequentialGroup().addComponent(descLbl).addComponent(descF))
                .addGroup(layout.createSequentialGroup().addComponent(typeLbl).addComponent(typeF))
                .addGroup(layout.createSequentialGroup().addComponent(unitLbl).addComponent(unitF))
                .addGroup(layout.createSequentialGroup().addComponent(packLbl).addComponent(packF))
                .addGroup(layout.createSequentialGroup().addComponent(costLbl).addComponent(costF))
                .addGroup(layout.createSequentialGroup().addComponent(markupLbl).addComponent(markupF))
                .addGroup(layout.createSequentialGroup().addComponent(qtyLbl).addComponent(qtyF))
                .addGroup(layout.createSequentialGroup().addComponent(minLbl).addComponent(minF))
                .addGroup(layout.createSequentialGroup().addComponent(backBtn).addComponent(confirmBtn))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(title).addGap(10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(idLbl).addComponent(idF))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(descLbl).addComponent(descF))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(typeLbl).addComponent(typeF))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(unitLbl).addComponent(unitF))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(packLbl).addComponent(packF))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(costLbl).addComponent(costF))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(markupLbl).addComponent(markupF))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(qtyLbl).addComponent(qtyF))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(minLbl).addComponent(minF))
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(backBtn, 40, 40, 40)
                        .addComponent(confirmBtn, 40, 40, 40))
        );

        backBtn.addActionListener(e -> controller.goToStockManager());
        confirmBtn.addActionListener(e -> submit());
    }

    private void submit() {
        try {
            StockItem item = new StockItem(
                    idF.getText().trim(), descF.getText().trim(),
                    typeF.getText().trim(), unitF.getText().trim(),
                    Integer.parseInt(packF.getText().trim()),
                    Double.parseDouble(costF.getText().trim()),
                    Double.parseDouble(markupF.getText().trim()),
                    Integer.parseInt(qtyF.getText().trim()),
                    Integer.parseInt(minF.getText().trim())
            );
            controller.addItem(item);
            JOptionPane.showMessageDialog(this, "Item added!");
            controller.goToStockManager();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Check the number fields.");
        }
    }
}