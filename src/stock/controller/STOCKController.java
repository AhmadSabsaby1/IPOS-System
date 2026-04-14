package stock.controller;

import main.Global;
import stock.model.STOCKModel;
import stock.model.StockItem;
import stock.view.*;

import javax.swing.*;

public class STOCKController {

    private STOCKModel model;
    private STOCKMainView mainView;
    private HubView hubView;
    private StockManagerView stockManagerView;
    private AddStockView addStockView;
    private AddNewItemView addNewItemView;
    private LowStockReportView lowStockReportView;

    public STOCKController() {
        mainView = new STOCKMainView();
        hubView = new HubView(this);
        stockManagerView = new StockManagerView(this);
        addStockView = new AddStockView(this);
        addNewItemView = new AddNewItemView(this);
        lowStockReportView = new LowStockReportView(this);

        mainView.addCardLayout(hubView, HubView.cardId());
        mainView.addCardLayout(stockManagerView, StockManagerView.cardId());
        mainView.addCardLayout(addStockView, AddStockView.cardId());
        mainView.addCardLayout(addNewItemView, AddNewItemView.cardId());
        mainView.addCardLayout(lowStockReportView, LowStockReportView.cardId());

        model = new STOCKModel();
        goToHub();
    }

    public void goToMainMenu() {
        Global.get().goToMainMenu();
        mainView.dispose();
    }

    public void goToHub() {
        hubView.showWarning(model.hasLowStock());
        mainView.changeCardView(HubView.cardId());
    }

    public void goToStockManager() {
        stockManagerView.populate(model.getStock());
        mainView.changeCardView(StockManagerView.cardId());
    }

    public void goToAddStock(String id) {
        StockItem item = model.getById(id);
        if (item != null) {
            addStockView.fill(item);
            mainView.changeCardView(AddStockView.cardId());
        }
    }

    public void goToAddNewItem() {
        mainView.changeCardView(AddNewItemView.cardId());
    }

    public void goToLowStockReport() {
        lowStockReportView.populate(model.getLowStock());
        mainView.changeCardView(LowStockReportView.cardId());
    }

    public void addQty(String id, int n) { model.addQty(id, n); }

    public void addItem(StockItem item) { model.addItem(item); }

    public void deleteItem(String id) {
        int c = JOptionPane.showConfirmDialog(null,
                "Delete this item?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (c == JOptionPane.YES_OPTION) {
            model.deleteItem(id);
            goToStockManager();
        }
    }
}