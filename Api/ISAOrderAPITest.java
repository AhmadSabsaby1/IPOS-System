package Api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit Test Cases for ISAOrderAPI (Required Interface)
 * Team B - IPOS-CA (Pentasolutions)
 * Tester: Ahmad Sabsaby
 *
 * NOTE: These are test cases for a REQUIRED interface - i.e. an interface
 * provided by Team A (IPOS-SA) that our subsystem (IPOS-CA) depends on.
 * These tests are NOT expected to pass or be executed at this stage,
 * as the implementation belongs to another team.
 * They are written to demonstrate what IPOS-CA expects from IPOS-SA.
 *
 * The two methods tested are:
 *   1. placeOrder(String merchantId, java.util.HashMap<Integer, Integer> items) -> boolean
 *   2. getOrderStatus(String orderId) -> String
 */
public class ISAOrderAPITest {

    private ISAOrder_Implementation isaOrder;

    @Before
    public void setUp() {
        isaOrder = new ISAOrder_Implementation();
    }

    @Test
    public void testPlaceOrder_ValidOrder_ReturnsTrue() {
        java.util.HashMap<Integer, Integer> items = new java.util.HashMap<>();
        items.put(10000001, 5);
        items.put(10000002, 10);
        assertTrue(isaOrder.placeOrder("MERCHANT_001", items));
    }

    @Test
    public void testPlaceOrder_InvalidMerchantId_ReturnsFalse() {
        java.util.HashMap<Integer, Integer> items = new java.util.HashMap<>();
        items.put(10000001, 5);
        assertFalse(isaOrder.placeOrder("INVALID_MERCHANT", items));
    }

    @Test
    public void testPlaceOrder_EmptyItemsMap_ReturnsFalse() {
        java.util.HashMap<Integer, Integer> items = new java.util.HashMap<>();
        assertFalse(isaOrder.placeOrder("MERCHANT_001", items));
    }

    @Test
    public void testPlaceOrder_NullMerchantId_ReturnsFalseOrThrows() {
        java.util.HashMap<Integer, Integer> items = new java.util.HashMap<>();
        items.put(10000001, 2);
        try {
            assertFalse(isaOrder.placeOrder(null, items));
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException || e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testGetOrderStatus_ValidOrderId_ReturnsStatus() {
        String status = isaOrder.getOrderStatus("IP2034");
        assertNotNull(status);
        assertFalse(status.isEmpty());
    }

    @Test
    public void testGetOrderStatus_InvalidOrderId_ReturnsNull() {
        assertNull(isaOrder.getOrderStatus("DOESNOTEXIST"));
    }

    @Test
    public void testGetOrderStatus_EmptyOrderId_ReturnsNullOrThrows() {
        try {
            assertNull(isaOrder.getOrderStatus(""));
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException || e instanceof NullPointerException);
        }
    }

    @Test
    public void testGetOrderStatus_NullOrderId_ReturnsNullOrThrows() {
        try {
            assertNull(isaOrder.getOrderStatus(null));
        } catch (NullPointerException e) {
            // Acceptable behaviour
        }
    }
}