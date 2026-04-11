package Api;

import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.*;

public class CaCatalogAPI_ImplementationTest {

    private CaCatalogAPI_Implementation api;

    @Before
    public void setUp() {
        api = new CaCatalogAPI_Implementation();
    }

    @Test
    public void testGetCatalogue_NotNull() {
        assertNotNull(api.getCatalogue());
    }

    @Test
    public void testGetCatalogue_CorrectSize() {
        assertEquals(14, api.getCatalogue().length);
    }

    @Test
    public void testGetCatalogue_FirstItemContainsParacetamol() {
        assertTrue(api.getCatalogue()[0].contains("Paracetamol"));
    }

    @Test
    public void testGetCatalogue_NoNullOrEmptyEntries() {
        String[] result = api.getCatalogue();
        for (int i = 0; i < result.length; i++) {
            assertNotNull(result[i]);
            assertFalse(result[i].isEmpty());
        }
    }

    @Test
    public void testGetCatalogue_EntriesContainIdAndDescription() {
        String[] result = api.getCatalogue();
        assertTrue(result[0].contains("10000001"));
        assertTrue(result[0].contains("Paracetamol"));
    }

    @Test
    public void testGetCatalogue_LastItemContainsVitaminB12() {
        String[] result = api.getCatalogue();
        assertTrue(result[result.length - 1].contains("Vitamin B12"));
    }

    @Test
    public void testSendOrderDetails_ValidOrder_ReturnsTrue() {
        HashMap<Integer, Integer> products = new HashMap<>();
        products.put(10000001, 5);
        products.put(10000002, 10);
        assertTrue(api.sendOrderDetails(products, 1001, "3 High Level Drive, Sydenham"));
    }

    @Test
    public void testSendOrderDetails_SingleProduct_ReturnsTrue() {
        HashMap<Integer, Integer> products = new HashMap<>();
        products.put(10000003, 1);
        assertTrue(api.sendOrderDetails(products, 1002, "27 Sainsbury Close, Stratford"));
    }

    @Test
    public void testSendOrderDetails_EmptyProductsMap_ReturnsTrue() {
        assertTrue(api.sendOrderDetails(new HashMap<>(), 1003, "10 Pharmacy Road, London"));
    }

    @Test
    public void testSendOrderDetails_ZeroOrderID_ReturnsTrue() {
        HashMap<Integer, Integer> products = new HashMap<>();
        products.put(10000004, 2);
        assertTrue(api.sendOrderDetails(products, 0, "5 Test Street, London"));
    }

    @Test
    public void testSendOrderDetails_NegativeOrderID_ReturnsTrue() {
        HashMap<Integer, Integer> products = new HashMap<>();
        products.put(10000005, 3);
        assertTrue(api.sendOrderDetails(products, -1, "5 Test Street, London"));
    }

    @Test
    public void testSendOrderDetails_EmptyShippingAddress_ReturnsTrue() {
        HashMap<Integer, Integer> products = new HashMap<>();
        products.put(10000001, 4);
        assertTrue(api.sendOrderDetails(products, 1004, ""));
    }

    @Test
    public void testSendOrderDetails_NullShippingAddress_ReturnsTrue() {
        HashMap<Integer, Integer> products = new HashMap<>();
        products.put(10000006, 2);
        assertTrue(api.sendOrderDetails(products, 1005, null));
    }

    @Test
    public void testSendOrderDetails_LargeOrder_ReturnsTrue() {
        HashMap<Integer, Integer> products = new HashMap<>();
        products.put(10000001, 100);
        products.put(10000002, 200);
        products.put(10000003, 150);
        products.put(10000004, 50);
        assertTrue(api.sendOrderDetails(products, 9999, "19 High St, Ashford, Kent"));
    }
}