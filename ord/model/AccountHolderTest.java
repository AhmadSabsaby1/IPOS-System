package cust.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountHolderTest {

    private AccountHolder account;

    @Before
    public void setUp() {
        account = new AccountHolder(
                "AH001", "John Smith", "27 Sainsbury Close, London",
                AccountHolder.CardType.DEBIT,
                "1234", "5678", "12/26",
                250.0, AccountHolder.DiscountType.FIXED, 10.0
        );
    }

    @Test
    public void testGetAccountId_ReturnsCorrectId() {
        assertEquals("AH001", account.getAccountId());
    }

    @Test
    public void testGetName_ReturnsCorrectName() {
        assertEquals("John Smith", account.getName());
    }

    @Test
    public void testGetBalance_ReturnsCorrectBalance() {
        assertEquals(250.0, account.getBalance(), 0.001);
    }

    @Test
    public void testGetStatus_DefaultIsNormal() {
        assertEquals("normal", account.getStatus());
    }

    @Test
    public void testGetStatus1stReminder_DefaultIsNoNeed() {
        assertEquals(AccountHolder.ReminderStatus.NO_NEED, account.getStatus1stReminder());
    }

    @Test
    public void testGetStatus2ndReminder_DefaultIsNoNeed() {
        assertEquals(AccountHolder.ReminderStatus.NO_NEED, account.getStatus2ndReminder());
    }

    @Test
    public void testGetDiscountType_ReturnsCorrectType() {
        assertEquals("fixed", account.getDiscountType());
    }

    @Test
    public void testGetCardType_ReturnsCorrectType() {
        assertEquals("debit", account.getCardType());
    }

    @Test
    public void testModifyField_Name_UpdatesCorrectly() {
        account.modifyField(AccountHolder.NAME, "Jane Doe");
        assertEquals("Jane Doe", account.getName());
    }

    @Test
    public void testModifyField_Balance_UpdatesCorrectly() {
        account.modifyField(AccountHolder.BALANCE, "500.0");
        assertEquals(500.0, account.getBalance(), 0.001);
    }

    @Test
    public void testModifyField_Status_Suspended() {
        account.modifyField(AccountHolder.STATUS, "suspended");
        assertEquals("suspended", account.getStatus());
    }

    @Test
    public void testModifyField_Status_InDefault() {
        account.modifyField(AccountHolder.STATUS, "in_default");
        assertEquals("in_default", account.getStatus());
    }

    @Test
    public void testModifyField_Status1st_Due() {
        account.modifyField(AccountHolder.STATUS_1ST, "due");
        assertEquals(AccountHolder.ReminderStatus.DUE, account.getStatus1stReminder());
    }

    @Test
    public void testModifyField_Status2nd_Sent() {
        account.modifyField(AccountHolder.STATUS_2ND, "sent");
        assertEquals(AccountHolder.ReminderStatus.SENT, account.getStatus2ndReminder());
    }

    @Test
    public void testModifyField_UnknownField_NoException() {
        account.modifyField("UNKNOWNFIELD", "somevalue");
    }

    @Test
    public void testAccountRowData_ReturnsCorrectData() {
        String[] data = account.accountRowData();
        assertEquals(3, data.length);
        assertEquals("AH001", data[0]);
        assertEquals("John Smith", data[1]);
        assertEquals("normal", data[2]);
    }

    @Test
    public void testDiscountType_GetValue_Valid() {
        assertEquals(AccountHolder.DiscountType.FIXED, AccountHolder.DiscountType.getValue("fixed"));
        assertEquals(AccountHolder.DiscountType.FLEXIBLE, AccountHolder.DiscountType.getValue("flexible"));
    }

    @Test
    public void testDiscountType_GetValue_Invalid_ReturnsNull() {
        assertNull(AccountHolder.DiscountType.getValue("unknown"));
    }

    @Test
    public void testAccountStatus_GetValue_Valid() {
        assertEquals(AccountHolder.AccountStatus.NORMAL, AccountHolder.AccountStatus.getValue("normal"));
        assertEquals(AccountHolder.AccountStatus.SUSPENDED, AccountHolder.AccountStatus.getValue("suspended"));
        assertEquals(AccountHolder.AccountStatus.IN_DEFAULT, AccountHolder.AccountStatus.getValue("in_default"));
    }

    @Test
    public void testAccountStatus_GetValue_Invalid_ReturnsNull() {
        assertNull(AccountHolder.AccountStatus.getValue("unknown"));
    }
}