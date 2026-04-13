package Api;

import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.*;

// Tests for the CA Catalogue API
// This is the interface our subsystem provides to the other teams
// PU uses this to get stock levels and send us completed online orders
// Written by Ahmad Sabsaby and Enes Shehu - Tester, Team B
public class CaCatalogAPI_ImplementationTest {

    private CaCatalogAPI_Implementation api;

    @Before
    public void setUp() {
        // the mock DB loads 14 pharmaceutical items from the catalogue
        api = new CaCatalogAPI_Implementation();
    }

    // --- getCatalogue() tests ---

    // catalogue should load all 14 items from the mock database
    @Test
    public void catalogueLoadsAllItems() {
        String[] result = api.getCatalogue();
        assertNotNull(result);
        assertEquals(14, result.length);
    }

    // paracetamol is the first item in the catalogue
    // checking the data is actually being read from the DB correctly
    @Test
    public void firstItemIsParacetamol() {
        String first = api.getCatalogue()[0];
        assertTrue(first.contains("Paracetamol"));
        assertTrue(first.contains("10000001"));
    }

    // vitamin B12 is the last item in the catalogue
    @Test
    public void lastItemIsVitaminB12() {
        String[] result = api.getCatalogue();
        assertTrue(result[result.length - 1].contains("Vitamin B12"));
    }

    // every item in the catalogue should have some data
    // we dont want blank rows showing up in the system
    @Test
    public void noBlankItemsInCatalogue() {
        for (String item : api.getCatalogue()) {
            assertNotNull(item);
            assertFalse(item.isEmpty());
        }
    }

    // --- sendOrderDetails() tests ---

    // a normal online order from PU coming through to CA
    // customer bought paracetamol and aspirin from the online portal
    @Test
    public void normalOnlineOrderGoesThrough() {
        HashMap<Integer, Integer> order = new HashMap<>();
        order.put(10000001, 5);  // 5 packs of paracetamol
        order.put(10000002, 3);  // 3 packs of aspirin
        assertTrue(api.sendOrderDetails(order, 1001,
                "27 Sainsbury Close, Stratford, EJ6 5TJ"));
    }

    // customer only bought one item online
    @Test
    public void singleItemOrderGoesThrough() {
        HashMap<Integer, Integer> order = new HashMap<>();
        order.put(10000003, 1); // just one pack of analgin
        assertTrue(api.sendOrderDetails(order, 1002,
                "3 High Level Drive, Sydenham, SE26 3ET"));
    }

    // big order with lots of different medicines
    @Test
    public void largeOrderWithManyItemsGoesThrough() {
        HashMap<Integer, Integer> order = new HashMap<>();
        order.put(10000001, 100);
        order.put(10000002, 50);
        order.put(10000003, 75);
        order.put(10000004, 30);
        assertTrue(api.sendOrderDetails(order, 1003,
                "19 High St, Ashford, Kent"));
    }

    // what happens if PU sends an order with no items
    // currently the system accepts it - no validation in place yet
    @Test
    public void emptyOrderStillReturnsTrue() {
        // no items in the order - system does not validate this yet
        assertTrue(api.sendOrderDetails(new HashMap<>(), 1004,
                "10 Pharmacy Road, London, N1 2AB"));
    }

    // what if PU sends a null address
    // should not crash the system
    @Test
    public void nullAddressDoesntCrash() {
        HashMap<Integer, Integer> order = new HashMap<>();
        order.put(10000001, 2);
        assertTrue(api.sendOrderDetails(order, 1005, null));
    }

    // what if PU sends an empty address string
    @Test
    public void emptyAddressDoesntCrash() {
        HashMap<Integer, Integer> order = new HashMap<>();
        order.put(10000001, 2);
        assertTrue(api.sendOrderDetails(order, 1006, ""));
    }

    // negative order ID - should not happen in real use
    // but system handles it without crashing
    @Test
    public void negativeOrderIdDoesntCrash() {
        HashMap<Integer, Integer> order = new HashMap<>();
        order.put(10000002, 5);
        assertTrue(api.sendOrderDetails(order, -1,
                "5 Test Street, London, EC1A 1BB"));
    }
}