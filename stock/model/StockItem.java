package stock.model;

public class StockItem {

    private String id;
    private String desc;
    private String pkgType;
    private String unit;
    private int unitsPerPack;
    private double bulkCost;
    private double markup;
    private int qty;
    private int minQty;

    private static double vat = 0.0;

    public static String ID = "Item ID";
    public static String DESC = "Description";
    public static String PKG_TYPE = "Package Type";
    public static String UNIT = "Unit";
    public static String UNITS_PER_PACK = "Units in Pack";
    public static String BULK_COST = "Bulk Cost";
    public static String MARKUP = "Markup %";
    public static String QTY = "Availability";
    public static String MIN_QTY = "Stock Limit";

    public static String[] cols() {
        return new String[]{"Item ID", "Description", "Pkg Type", "Unit",
                "Units/Pack", "Bulk Cost £", "Retail £", "Stock", "Min Stock"};
    }

    public StockItem(String id, String desc, String pkgType, String unit,
                     int unitsPerPack, double bulkCost, double markup,
                     int qty, int minQty) {
        this.id = id;
        this.desc = desc;
        this.pkgType = pkgType;
        this.unit = unit;
        this.unitsPerPack = unitsPerPack;
        this.bulkCost = bulkCost;
        this.markup = markup;
        this.qty = qty;
        this.minQty = minQty;
    }

    public double retailPrice() {
        double withMarkup = bulkCost * (1 + markup / 100.0);
        return withMarkup * (1 + vat / 100.0);
    }

    public boolean isBelowMin() { return qty < minQty; }

    public void addQty(int n) { qty += n; }
    public void removeQty(int n) { qty -= n; }

    public void modifyField(String field, String val) {
        if (field.equals(DESC)) desc = val;
        else if (field.equals(PKG_TYPE)) pkgType = val;
        else if (field.equals(UNIT)) unit = val;
        else if (field.equals(UNITS_PER_PACK)) unitsPerPack = Integer.parseInt(val);
        else if (field.equals(BULK_COST)) bulkCost = Double.parseDouble(val);
        else if (field.equals(MARKUP)) markup = Double.parseDouble(val);
        else if (field.equals(QTY)) qty = Integer.parseInt(val);
        else if (field.equals(MIN_QTY)) minQty = Integer.parseInt(val);
    }

    public String[] rowData() {
        return new String[]{id, desc, pkgType, unit,
                String.valueOf(unitsPerPack),
                String.format("%.2f", bulkCost),
                String.format("%.2f", retailPrice()),
                String.valueOf(qty),
                String.valueOf(minQty)};
    }

    public String getId() { return id; }
    public String getDesc() { return desc; }
    public int getQty() { return qty; }
    public int getMinQty() { return minQty; }

    public static double getVat() { return vat; }
    public static void setVat(double rate) { vat = rate; }
}