package ord.view;

import custom.CTable;
import custom.TitleLabel;
import ord.controller.ORDController;
import ord.model.CartItem;
import ord.model.Item;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CatalogueView extends JPanel {
    private ORDController controller;

    //Swing Objects
    private JButton addToCartButton;
    private JButton goToCartButton;
    private JButton backButton;
    private JButton removeSearch;
    private CTable catalogueTable;
    private JLabel infoLabel;
    private TitleLabel titleLabel;

    private JTextField searchTextField;
    private JComboBox<String> fieldListComboBox;
    private JButton searchButton;

    static public String cardId(){
        return "CatalogueView";
    }

    public CatalogueView(ORDController controller) {
        this.controller = controller;

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        titleLabel = new TitleLabel("Catalogue");

        backButton = new JButton("Back to Main Menu");

        //creates the button to see the cart
        goToCartButton = new JButton("See Cart");

        //creates the button to add items to the cart
        addToCartButton = new JButton("Add To Cart");

        //a label to show information to the user, if things where added, any errors, etc.
        infoLabel = new JLabel();

        //search bar
        searchTextField = new JTextField(10);

        fieldListComboBox = new JComboBox<>(new String[]{Item.DESCRIPTION, Item.ITEM_ID});

        searchButton = new JButton("Search");

        removeSearch = new JButton("Remove Search");

        //creates the table for the catalogue and sets the labels for the columns
        catalogueTable = new CTable(Item.catalogueColumnId());
        //remember that we don't add the catalogue table to the panel, but its JScrollPane
        //add(catalogueTable.getScrollPane());

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titleLabel)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton)
                        .addGap(50)
                        .addComponent(goToCartButton)
                )
                .addGroup(layout.createSequentialGroup()
                        .addComponent(searchButton, 100, 100, 100)
                        .addComponent(searchTextField, 200, 200, 200)
                        .addComponent(fieldListComboBox, 100, 100, 100)
                        .addComponent(removeSearch)
                )
                .addComponent(catalogueTable.getScrollPane())
                .addComponent(infoLabel)
                .addComponent(addToCartButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(titleLabel)
                .addGap(30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(backButton)
                        .addComponent(goToCartButton)
                )
                .addGap(30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(searchButton)
                        .addComponent(searchTextField)
                        .addComponent(fieldListComboBox)
                        .addComponent(removeSearch)
                )
                .addComponent(catalogueTable.getScrollPane(), 100, 200, 200)
                .addComponent(infoLabel)
                .addComponent(addToCartButton)
        );

        //sets the listeners for the buttons
        addToCartButton.addActionListener(e -> addToCart());
        goToCartButton.addActionListener(e -> goToCart());
        backButton.addActionListener(e -> {
            infoLabel.setText("");
            controller.goToHubScreen();
        });

        searchButton.addActionListener(e -> search());
        removeSearch.addActionListener(e -> removeSearch());
    }

    public void populateCatalogue(ArrayList<Item> items){
        //puts every element of the item list in the table
        catalogueTable.removeTableElements();

        for (Item item : items){
            catalogueTable.addRow(item.catalogueRowData());
        }
    }

    private void goToCart(){
        if (controller.isCartEmpty()){
            infoLabel.setText("The cart is empty");
            return;
        }

        infoLabel.setText("");
        controller.goToCartScreen();
    }

    private void removeSearch(){
        if (searchTextField.getText().isEmpty())
            return;

        searchTextField.setText("");
        populateCatalogue(controller.getCatalogue());
    }

    private void search(){
        if (searchTextField.getText().isEmpty())
            return;

        populateCatalogue(controller.searchByField(fieldListComboBox.getSelectedItem().toString(), searchTextField.getText()));
    }

    private void addToCart(){
        if (catalogueTable.getSelectedRow() == -1) {
            infoLabel.setText("You must select an item to be added.");
            return;
        }

        int quantity;
        Item item = getSelectedItem();
        if (item == null) //it should never be null, but just in case
            return;

        //we check if the item the user wants to add to the cart is already in
        if (controller.cartItemExists(item.getId())){
            infoLabel.setText("Item already added to the cart");
            return;
        }

        //this creates a pop-up with a field to put the quantity in. Very quick and dirty, but it works.
        String quantityInput = JOptionPane.showInputDialog("Enter quantity");
        if (quantityInput == null || quantityInput.isEmpty()) {
            //input cancelled or left empty
            return;
        }

        //we must do a try-catch to check if the parseInt is able to transform
        // the input the user has introduced into an int. If it can't, then the
        // user introduced the input wrong
        try{
            quantity = Integer.parseInt(quantityInput);
        }catch (NumberFormatException ex){
            infoLabel.setText(quantityInput + " is not a valid quantity.");
            return;
        }

        //check if the user introduced a 0 or a negative number
        if (quantity < 1){
            infoLabel.setText("You must enter a positive quantity of items.");
            return;
        }

        //everything is ok, so we add the item to the cart
        controller.addToCart(catalogueTable.getSelectedRowColumn(0), quantity);
        infoLabel.setText(quantity + " units of " + catalogueTable.getSelectedRowColumn(1) + " added to the cart");
    }

    private Item getSelectedItem(){
        //this returns an Item created from the id of the table
        return controller.getItemByID(catalogueTable.getSelectedRowColumn(0));
    }
}
