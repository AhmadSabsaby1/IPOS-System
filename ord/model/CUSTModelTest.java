package cust.model;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

// Testing the main model class for IPOS-CA
// Written by Ahmad Sabsaby and Enes Shehu - Tester, Team B
public class CUSTModelTest {

    private CUSTModel model;

    @Before
    public void setUp() {
        model = new CUSTModel();
    }

    // --- Account Holder Tests ---

    // just checking the list loads properly on startup
    @Test
    public void accountHoldersLoadOnStartup() {
        assertNotNull(model.getAccountHolders());
    }

    // mock data has 2 accounts (Cesar and Arthur)
    @Test
    public void twoAccountsInMockData() {
        assertEquals(2, model.getAccountHolders().size());
    }

    // AH001 is Cesar, should be findable
    @Test
    public void findExistingAccount() {
        AccountHolder found = model.getAccountById("AH001");
        assertNotNull(found);
        assertEquals("AH001", found.getAccountId());
    }

    // searching for something that doesnt exist should give null not crash
    @Test
    public void searchForAccountThatDoesntExist() {
        assertNull(model.getAccountById("AH999"));
    }

    // adding a new pharmacy customer and checking they show up
    @Test
    public void addNewAccountHolder() {
        int before = model.getAccountHolders().size();
        model.createAccount(new AccountHolder(
                "Sarah Jones", "14 Church Lane, London",
                AccountHolder.CardType.CREDIT,
                "4567", "7654", "03/28",
                300.0, AccountHolder.DiscountType.FIXED, 5.0
        ));
        assertEquals(before + 1, model.getAccountHolders().size());
    }

    // deleting an account and making sure its gone
    @Test
    public void deleteAccountAndCheckItsGone() {
        model.deleteAccount("AH001");
        assertNull(model.getAccountById("AH001"));
    }

    // deleting something that doesnt exist shouldnt cause problems
    @Test
    public void deletingNonExistentAccountDoesntCrash() {
        model.deleteAccount("AH999");
    }

    // --- Catalogue Tests ---

    // catalogue should load with items
    @Test
    public void catalogueLoadsWithItems() {
        assertNotNull(model.getCatalogue());
        assertEquals(14, model.getCatalogue().size());
    }

    // paracetamol is the first item in the mock catalogue
    @Test
    public void findParacetamolById() {
        LocalItem item = model.getItemByID("10000001");
        assertNotNull(item);
        assertEquals("Paracetamol", item.getDescription());
    }

    // searching for something not in the catalogue
    @Test
    public void itemNotInCatalogueReturnsNull() {
        assertNull(model.getItemByID("99999999"));
    }

    // --- Cart Tests ---

    // cart should be empty when we first start
    @Test
    public void cartStartsEmpty() {
        assertTrue(model.isCartEmpty());
    }

    // add paracetamol to cart and check its there
    @Test
    public void addItemToCart() {
        model.addToCart("10000001", 3);
        assertTrue(model.cartItemExists("10000001"));
        assertFalse(model.isCartEmpty());
    }

    // shouldnt be able to add same item twice
    @Test
    public void cantAddSameItemTwice() {
        model.addToCart("10000001", 3);
        assertNull(model.addToCart("10000001", 2));
    }

    // adding an item that doesnt exist in catalogue
    @Test(expected = NullPointerException.class)
    public void addingFakeItemCrashes() {
        model.addToCart("99999999", 1);
    }

    // remove item from cart
    @Test
    public void removeItemFromCart() {
        model.addToCart("10000001", 3);
        model.removeFromCart("10000001");
        assertFalse(model.cartItemExists("10000001"));
    }

    // empty cart should have 0 total
    @Test
    public void emptyCartHasZeroTotal() {
        assertEquals(0.0, model.calculateGrandTotal(), 0.001);
    }

    // paracetamol costs 0.10 per pack, 10 packs = £1.00
    @Test
    public void cartTotalCalculatesCorrectly() {
        model.addToCart("10000001", 10);
        assertEquals(1.00, model.calculateGrandTotal(), 0.001);
    }

    // clear everything from cart
    @Test
    public void clearCart() {
        model.addToCart("10000001", 1);
        model.addToCart("10000002", 2);
        model.removeAllCartItems();
        assertTrue(model.isCartEmpty());
    }

    // --- Search Tests ---

    // search for paracetamol by name
    @Test
    public void searchByNameFindsParacetamol() {
        ArrayList<LocalItem> results = model.searchByField(LocalItem.DESCRIPTION, "Paracetamol");
        assertFalse(results.isEmpty());
        assertEquals("Paracetamol", results.get(0).getDescription());
    }

    // search should work even with lowercase
    @Test
    public void searchIsNotCaseSensitive() {
        assertFalse(model.searchByField(LocalItem.DESCRIPTION, "paracetamol").isEmpty());
    }

    // searching for something random should return nothing
    @Test
    public void searchWithNoMatchReturnsEmptyList() {
        assertTrue(model.searchByField(LocalItem.DESCRIPTION, "chocolate").isEmpty());
    }
}