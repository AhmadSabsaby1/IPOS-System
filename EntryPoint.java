import cust.controller.CUSTController;
import ord.controller.ORDController;
import stock.controller.STOCKController;

public class EntryPoint {
    public static void main(String[] args) {
        CUSTController controller = new CUSTController();
        STOCKController stockController = new STOCKController();
    }
}
