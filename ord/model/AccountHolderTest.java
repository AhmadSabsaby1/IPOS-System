package cust.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

// Tests for the AccountHolder class
// This class represents a pharmacy customer who has a credit account
// Written by Ahmad Sabsaby and Enes Shehu - Tester, Team B
public class AccountHolderTest {

    private AccountHolder account;

    @Before
    public void CreateNewAcc() {
        // creating a  fake pharmacy cust - Ahmad Sabsaby from London
        // he has a debit card and a fixed 10% discount
        account = new AccountHolder(
                "AH001",
                "Ahmad Sabsaby",
                "27 Sainsbury Close, London",
                AccountHolder.CardType.DEBIT,
                "1234",
                "5678",
                "12/26",
                250.0,
                AccountHolder.DiscountType.FIXED,
                10.0
        );
    }

    // when a new acc is created it should start as normal because he has not missed any payments
    // not suspended or in default
    @Test
    public void NormalStatusOfAcc() {
        assertEquals("normal", account.getStatus());
    }

    // no reminders should be sent when the account is first created
    // the cust has not missed any payments yet
    @Test
    public void NoRemindersSentForNewAcc() {
        assertEquals(AccountHolder.ReminderStatus.NO_NEED, account.getStatus1stReminder());
        assertEquals(AccountHolder.ReminderStatus.NO_NEED, account.getStatus2ndReminder());
    }

    // just checks the acc details come back correctly
    @Test
    public void SetUpDetailsCorrect() {
        assertEquals("AH001", account.getAccountId());
        assertEquals("Ahmad Sabsaby", account.getName());
        assertEquals(250.0, account.getBalance(), 0.001);
        assertEquals("debit", account.getCardType());
        assertEquals("fixed", account.getDiscountType());
    }

    // pharmacy manager updates the cust name
    @Test
    public void updateCustName() {
        account.modifyField(AccountHolder.NAME, "Ahmad Sabsaby");
        assertEquals("Ahmad Sabsaby", account.getName());
    }

    // cust pays off his debt so balance gets updated
    @Test
    public void updateCustBalance() {
        account.modifyField(AccountHolder.BALANCE, "150.0");
        assertEquals(150.0, account.getBalance(), 0.001);
    }

    // customer hasnt paid by the 15th so account gets suspended
    @Test
    public void accountGetsSuspended() {
        account.modifyField(AccountHolder.STATUS, "suspended");
        assertEquals("suspended", account.getStatus());
    }

    // customer still hasnt paid by end of month so goes to default
    @Test
    public void accountGoesIntoDefault() {
        account.modifyField(AccountHolder.STATUS, "in_default");
        assertEquals("in_default", account.getStatus());
    }

    // first reminder is now due because customer hasnt paid
    @Test
    public void firstReminderBecomeDue() {
        account.modifyField(AccountHolder.STATUS_1ST, "due");
        assertEquals(AccountHolder.ReminderStatus.DUE, account.getStatus1stReminder());
    }

    // second reminder has been sent to the customer
    @Test
    public void secondReminderSent() {
        account.modifyField(AccountHolder.STATUS_2ND, "sent");
        assertEquals(AccountHolder.ReminderStatus.SENT, account.getStatus2ndReminder());
    }

    // passing in a field name that doesnt exist shouldnt crash the system
    @Test
    public void updatingUnknownFieldDoesntCrash() {
        account.modifyField("SOMETHINGWRONG", "somevalue");
    }

    // the account row shown in the table should have id, name and status
    @Test
    public void accountRowShowsCorrectData() {
        String[] row = account.accountRowData();
        assertEquals(3, row.length);
        assertEquals("AH001", row[0]);
        assertEquals("Ahmad Sabsaby", row[1]);
        assertEquals("normal", row[2]);
    }

    // checking the discount types work correctly
    // merchants can set fixed or flexible discounts for their customers
    @Test
    public void discountTypesWorkCorrectly() {
        assertEquals(AccountHolder.DiscountType.FIXED,
                AccountHolder.DiscountType.getValue("fixed"));
        assertEquals(AccountHolder.DiscountType.FLEXIBLE,
                AccountHolder.DiscountType.getValue("flexible"));
        // passing something random should return null
        assertNull(AccountHolder.DiscountType.getValue("somethingelse"));
    }

    // checking all three account statuses are recognised
    @Test
    public void allAccountStatusesAreRecognised() {
        assertEquals(AccountHolder.AccountStatus.NORMAL,
                AccountHolder.AccountStatus.getValue("normal"));
        assertEquals(AccountHolder.AccountStatus.SUSPENDED,
                AccountHolder.AccountStatus.getValue("suspended"));
        assertEquals(AccountHolder.AccountStatus.IN_DEFAULT,
                AccountHolder.AccountStatus.getValue("in_default"));
        // invalid status should return null
        assertNull(AccountHolder.AccountStatus.getValue("randomstatus"));
    }
}