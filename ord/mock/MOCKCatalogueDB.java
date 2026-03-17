package ord.mock;

import ord.model.Item;

import java.util.ArrayList;

/**
 * <strong>THIS IS A MOCK CLASS AND IT IS NOT REPRESENTATIVE OF ANY ACTUAL DB OR API CLASS</strong>
 * <p>Most of the returned values will be a <code>ResultSet</code>,a <code>JSon</code> object or
 * something like that. It <strong>WON'T</strong> be an <code>Item</code>.
 */
public class MOCKCatalogueDB {
    private ArrayList<Item> itemList;

    public MOCKCatalogueDB() {
        itemList = new ArrayList<>();
        itemList.add(new Item("10000001", "Paracetamol", "box", "caps", 20, 0.10, 10345, 300));
        itemList.add(new Item("10000002", "Aspirin", "box", "caps", 20, 0.50, 12453, 500));
        itemList.add(new Item("10000003", "Analgin", "box", "caps", 10, 1.20, 4235, 200));
        itemList.add(new Item("10000004", "Celebrex, caps 100 mg", "box", "caps", 10, 10.00, 3420, 200));
        itemList.add(new Item("10000005", "Celebrex, caps 200 mg", "box", "caps", 10, 18.50, 1450, 150));
        itemList.add(new Item("10000006", "Retin-A Tretin, 30 g", "box", "caps", 20, 25.00, 2013, 200));
        itemList.add(new Item("10000007", "Lipitor TB, 20 mg", "box", "caps", 30, 15.50, 1562, 200));
        itemList.add(new Item("10000008", "Claritin CR, 60g", "box", "caps", 20, 19.50, 2540, 200));

        itemList.add(new Item("20000004", "Iodine tincture", "bottle", "ml", 100, 0.30, 22134, 200));
        itemList.add(new Item("20000005", "Rhynol", "bottle", "ml", 200, 2.50, 1908, 300));

        itemList.add(new Item("30000001", "Ospen", "box", "caps", 20, 10.50, 809, 200));
        itemList.add(new Item("30000002", "Amopen", "box", "caps", 30, 15.00, 1340, 300));

        itemList.add(new Item("40000001", "Vitamin C", "box", "caps", 30, 1.20, 3258, 300));
        itemList.add(new Item("40000002", "Vitamin B12", "box", "caps", 30, 1.30, 2673, 300));
    }

    public ArrayList<Item> getCatalogue() {
        return itemList;
    }

    //CHANGES!!
    public Item getItemByID(String id) {
        for (Item item : itemList) {
            if (item.getId() == id){
                return item;
            }
        }

        return null;
    }
}
