package cust.model;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class CUSTModelTest {

    private CUSTModel model;

    @Before
    public void setUp() {
        model = new CUSTModel();
    }

    @Test
    public void testGetAccountHolders_NotNull() {
        assertNotNull(model.getAccountHolders());
    }

    @Test
    public void testGetAccountHolders_CorrectSize() {
        assertEquals(2, model.getAccountHolders().size());
    }

    @Test
    public void testGetAccountById_ValidId_ReturnsAccount() {
        AccountHolder result = model.getAccountById("AH001");
        assertNotNull(result);
        assertEquals("AH001", result.getAccountId());
    }

    @Test
    public void testGetAccountById_InvalidId_ReturnsNull() {
        assertNull(model.getAccountById("DOESNOTEXIST"));
    }

    @Test
    public void testGetAccountById_EmptyId_ReturnsNull() {
        assertNull(model.getAccountById(""));
    }

    @Test
    public void testCreateAccount_IncreasesListSize() {
        int before = model.getAccountHolders().size();
        model.createAccount(new AccountHolder(
                "Test User", "123 Test St",
                AccountHolder.CardType.DEBIT,
                "1111", "2222", "12/26",
                100.0, AccountHolder.DiscountType.FIXED, 10.0
        ));
        assertEquals(before + 1, model.getAccountHolders().size());
    }

    @Test
    public void testCreateAccount_AccountIsRetrievable() {
        model.createAccount(new AccountHolder(
                "AH999", "Test User", "123 Test St",
                AccountHolder.CardType.DEBIT,
                "1111", "2222", "12/26",
                100.0, AccountHolder.DiscountType.FIXED, 10.0
        ));
        assertNotNull(model.getAccountById("AH999"));
    }

    @Test
    public void testDeleteAccount_DecreasesListSize() {
        int before = model.getAccountHolders().size();
        model.deleteAccount("AH001");
        assertEquals(before - 1, model.getAccountHolders().size());
    }

    @Test
    public void testDeleteAccount_AccountNoLongerExists() {
        model.deleteAccount("AH001");
        assertNull(model.getAccountById("AH001"));
    }

    @Test
    public void testDeleteAccount_NonExistentId_NoException() {
        model.deleteAccount("DOESNOTEXIST");
    }

    @Test
    public void testGetCatalogue_NotNull() {
        assertNotNull(model.getCatalogue());
    }

    @Test
    public void testGetCatalogue_CorrectSize() {
        assertEquals(14, model.getCatalogue().size());
    }

    @Test
    public void testGetItemByID_ValidId_ReturnsItem() {
        LocalItem item = model.getItemByID("10000001");
        assertNotNull(item);
        assertEquals("Paracetamol", item.getDescription());
    }

    @Test
    public void testGetItemByID_InvalidId_ReturnsNull() {
        assertNull(model.getItemByID("99999999"));
    }

    @Test
    public void testAddToCart_ValidItem_ReturnsOrderItem() {
        assertNotNull(model.addToCart("10000001", 3));
    }

    @Test
    public void testAddToCart_ItemExistsInCart() {
        model.addToCart("10000001", 3);
        assertTrue(model.cartItemExists("10000001"));
    }

    @Test
    public void testAddToCart_DuplicateItem_ReturnsNull() {
        model.addToCart("10000001", 3);
        assertNull(model.addToCart("10000001", 2));
    }

    @Test(expected = NullPointerException.class)
    public void testAddToCart_InvalidItemId_ThrowsException() {
        model.addToCart("99999999", 1);
    }

    @Test
    public void testRemoveFromCart_ItemNoLongerExists() {
        model.addToCart("10000001", 3);
        model.removeFromCart("10000001");
        assertFalse(model.cartItemExists("10000001"));
    }

    @Test
    public void testCalculateGrandTotal_EmptyCart_ReturnsZero() {
        assertEquals(0.0, model.calculateGrandTotal(), 0.001);
    }

    @Test
    public void testCalculateGrandTotal_CorrectTotal() {
        model.addToCart("10000001", 10);
        assertEquals(1.00, model.calculateGrandTotal(), 0.001);
    }

    @Test
    public void testIsCartEmpty_InitiallyEmpty() {
        assertTrue(model.isCartEmpty());
    }

    @Test
    public void testIsCartEmpty_NotEmptyAfterAdding() {
        model.addToCart("10000001", 1);
        assertFalse(model.isCartEmpty());
    }

    @Test
    public void testRemoveAllCartItems_CartIsEmpty() {
        model.addToCart("10000001", 1);
        model.addToCart("10000002", 2);
        model.removeAllCartItems();
        assertTrue(model.isCartEmpty());
    }

    @Test
    public void testSearchByField_ByDescription_ReturnsResults() {
        ArrayList<LocalItem> results = model.searchByField(LocalItem.DESCRIPTION, "Paracetamol");
        assertFalse(results.isEmpty());
        assertEquals("Paracetamol", results.get(0).getDescription());
    }

    @Test
    public void testSearchByField_ByItemId_ReturnsResults() {
        assertFalse(model.searchByField(LocalItem.ITEM_ID, "10000001").isEmpty());
    }

    @Test
    public void testSearchByField_NoMatch_ReturnsEmptyList() {
        assertTrue(model.searchByField(LocalItem.DESCRIPTION, "XYZNOTEXIST").isEmpty());
    }

    @Test
    public void testSearchByField_CaseInsensitive_ReturnsResults() {
        assertFalse(model.searchByField(LocalItem.DESCRIPTION, "paracetamol").isEmpty());
    }
}