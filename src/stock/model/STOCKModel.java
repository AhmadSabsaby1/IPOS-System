package stock.model;

import database.DBLocalStock;
import java.sql.ResultSet;
import java.util.ArrayList;

public class STOCKModel {

    private ArrayList<StockItem> stock;
    private DBLocalStock db;

    public STOCKModel() {
        stock = new ArrayList<>();
        try {
            db = new DBLocalStock();
            loadFromDB();
        } catch (Exception e) {
            System.out.println("DB connection failed, loading mock data: " + e.getMessage());
            loadMockData();
        }
    }

    // load stock from the real database
    private void loadFromDB() throws Exception {
        ResultSet rs = db.getStock();
        while (rs.next()) {
            stock.add(new StockItem(
                    rs.getString("itemID"),
                    rs.getString("description"),
                    rs.getString("packageType"),
                    rs.getString("unit"),
                    rs.getInt("unitsInAPack"),
                    rs.getDouble("packageCost"),
                    rs.getInt("retailMarkUpRate"),
                    rs.getInt("availability"),
                    rs.getInt("stockLimit")
            ));
        }
    }

    // fallback mock data if DB fails
    private void loadMockData() {
        stock.add(new StockItem("10000001", "Paracetamol", "Box", "Caps", 20, 0.10, 100, 121, 10));
        stock.add(new StockItem("10000002", "Aspirin", "Box", "Caps", 20, 0.50, 100, 201, 15));
        stock.add(new StockItem("10000003", "Analgin", "Box", "Caps", 10, 1.20, 100, 25, 10));
        stock.add(new StockItem("10000004", "Celebrex, caps 100 mg", "Box", "Caps", 10, 10.00, 100, 43, 10));
        stock.add(new StockItem("10000005", "Celebrex, caps 200 mg", "Box", "Caps", 10, 18.50, 100, 35, 5));
        stock.add(new StockItem("10000006", "Retin-A Tretin, 30 g", "Box", "Caps", 20, 25.00, 100, 28, 10));
        stock.add(new StockItem("10000007", "Lipitor TB, 20 mg", "Box", "Caps", 30, 15.50, 100, 10, 10));
        stock.add(new StockItem("10000008", "Claritin CR, 60g", "Box", "Caps", 20, 19.50, 100, 21, 10));
        stock.add(new StockItem("20000004", "Iodine tincture", "Bottle", "Ml", 100, 0.30, 100, 35, 10));
        stock.add(new StockItem("20000005", "Rhynol", "Bottle", "Ml", 200, 2.50, 100, 14, 15));
        stock.add(new StockItem("30000001", "Ospen", "Box", "Caps", 20, 10.50, 100, 78, 10));
        stock.add(new StockItem("30000002", "Amopen", "Box", "Caps", 30, 15.00, 100, 90, 15));
        stock.add(new StockItem("40000001", "Vitamin C", "Box", "Caps", 30, 1.20, 100, 22, 15));
        stock.add(new StockItem("40000002", "Vitamin B12", "Box", "Caps", 30, 1.30, 100, 43, 15));
    }

    public ArrayList<StockItem> getStock() { return stock; }

    public StockItem getById(String id) {
        for (StockItem item : stock)
            if (item.getId().equals(id)) return item;
        return null;
    }

    public void addItem(StockItem item) {
        try {
            db.newProduct(item.getDesc(), item.getPackageType(), item.getUnit(),
                    item.getUnitsPerPack(), item.getBulkCost(), item.getQty(),
                    item.getMinQty(), (int) item.getMarkup());
        } catch (Exception e) {
            System.out.println("Could not save to DB: " + e.getMessage());
        }
        stock.add(item);
    }

    public boolean addQty(String id, int n) {
        StockItem item = getById(id);
        if (item == null) return false;
        item.addQty(n);
        try {
            db.updateStock(id, item.getQty());
        } catch (Exception e) {
            System.out.println("Could not update DB: " + e.getMessage());
        }
        return true;
    }

    public boolean deleteItem(String id) {
        StockItem item = getById(id);
        if (item == null) return false;
        try {
            db.deleteProduct(id);
        } catch (Exception e) {
            System.out.println("Could not delete from DB: " + e.getMessage());
        }
        stock.remove(item);
        return true;
    }

    public ArrayList<StockItem> getLowStock() {
        ArrayList<StockItem> low = new ArrayList<>();
        for (StockItem item : stock)
            if (item.isBelowMin()) low.add(item);
        return low;
    }

    public boolean hasLowStock() { return !getLowStock().isEmpty(); }

    public void modifyField(String id, String field, String val) {
        StockItem item = getById(id);
        if (item != null) item.modifyField(field, val);
    }
}