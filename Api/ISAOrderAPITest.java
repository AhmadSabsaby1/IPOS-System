/* package Api;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

// testing SA's order API - Ahmad Sabsaby and Enes Shehu
// wont compile till team A sends their code
public class ISAOrderAPITest {

    private ISAOrder_Implementation saOrder;

    @Before
    public void setup() {
        saOrder = new ISAOrder_Implementation();
    }

    @Test
    public void cosymedCanPlaceOrder() {
        // cosymed is account 0000235 in the brief
        java.util.HashMap<Integer, Integer> order = new java.util.HashMap<>();
        order.put(10000001, 10); // paracetamol
        order.put(10000002, 20); // aspirin
        assertTrue(saOrder.placeOrder("0000235", order));
    }

    @Test
    public void randomAccountCantOrder() {
        java.util.HashMap<Integer, Integer> order = new java.util.HashMap<>();
        order.put(10000001, 5);
        assertFalse(saOrder.placeOrder("FAKE123", order));
    }

    @Test
    public void cantOrderNothing() {
        assertFalse(saOrder.placeOrder("0000235", new java.util.HashMap<>()));
    }

    @Test
    public void nullMerchant() {
        java.util.HashMap<Integer, Integer> order = new java.util.HashMap<>();
        order.put(10000001, 5);
        try {
            assertFalse(saOrder.placeOrder(null, order));
        } catch (NullPointerException e) {}
    }

    @Test
    public void checkIP2034Status() {
        // IP2034 is cosymed's order from 12/01/2003 in the brief
        String s = saOrder.getOrderStatus("IP2034");
        assertNotNull(s);
        assertFalse(s.isEmpty());
    }

    @Test
    public void fakeOrderGivesNull() {
        assertNull(saOrder.getOrderStatus("IP9999"));
    }

    @Test
    public void emptyStringOrderId() {
        try {
            assertNull(saOrder.getOrderStatus(""));
        } catch (Exception e) {}
    }

    @Test
    public void nullOrderId() {
        try {
            assertNull(saOrder.getOrderStatus(null));
        } catch (NullPointerException e) {}
    }
}


 */