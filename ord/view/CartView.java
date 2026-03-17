package ord.view;

import custom.CTable;
import ord.controller.ORDController;
import ord.model.CartItem;
import ord.model.Item;

import javax.swing.*;
import java.util.ArrayList;

public class CartView extends JPanel {
    private ORDController controller;

    //Swing Objects
    private JButton seeCatalogueButton;
    private CTable cartTable;
    private JLabel totalLabel;

    static public String cardId(){
        return "CartView";
    }

    public CartView(ORDController controller) {
        this.controller = controller;

        //sets the layout as a box layout. See https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //creates the button to go back to the catalogue
        seeCatalogueButton = new JButton("Back to the Catalogue");
        add(seeCatalogueButton);

        //creates the label showing the total cost of the order
        totalLabel = new JLabel();
        add(totalLabel);

        //creates the table that shows the items in the cart
        cartTable = new CTable(CartItem.cartItemColumndId());
        add(cartTable.getScrollPane());

        //creates the listener for the button to change the view back to the catalogue
        seeCatalogueButton.addActionListener(e -> controller.changeScreenCatalogue());
    }

    public void populateTable(ArrayList<CartItem> cartItems){
        double grandTotal = 0;

        //we remove the elements so it won't add the cart list to the table
        // each time we enter this view
        cartTable.removeTableElements();
        for(CartItem i : cartItems){
            cartTable.addRow(i.rowData());
            grandTotal += i.getTotal();
        }

        totalLabel.setText("Grand Total: " + grandTotal);
    }
}
