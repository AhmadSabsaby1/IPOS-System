package stock.view;

import stock.controller.STOCKController;
import custom.TitleLabel;

import javax.swing.*;
import java.awt.*;

public class HubView extends JPanel {

    private STOCKController controller;
    private TitleLabel title;
    private JButton manageBtn;
    private JButton lowStockBtn;
    private JButton backButton;
    private JLabel warning;

    public static String cardId() { return "HubView"; }

    public HubView(STOCKController controller) {
        this.controller = controller;

        title = new TitleLabel("STOCK - Manage Local Stock");
        manageBtn = new JButton("Manage Stock");
        lowStockBtn = new JButton("Low Stock Report");
        backButton = new JButton("Back to Main menu");
        warning = new JLabel("");
        warning.setForeground(Color.RED);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(title)
                .addComponent(backButton)
                .addComponent(warning)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(manageBtn)
                        .addComponent(lowStockBtn)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(title)
                .addGap(40)
                .addComponent(backButton)
                .addGap(20)
                .addComponent(warning)
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(manageBtn, 50, 50, 50)
                        .addComponent(lowStockBtn, 50, 50, 50)
                )
        );

        manageBtn.addActionListener(e -> controller.goToStockManager());
        lowStockBtn.addActionListener(e -> controller.goToLowStockReport());
        backButton.addActionListener(e->controller.goToMainMenu());
    }

    public void showWarning(boolean hasLow) {
        warning.setText(hasLow ? "Warning: some items are below minimum stock!" : "");
    }
}