package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.unibo.bank.impl.SimpleBankAccount.*;
import static it.unibo.bank.impl.StrictBankAccount.TRANSACTION_FEE;
import static org.junit.jupiter.api.Assertions.*;

public class TestStrictBankAccount {

    private final static int INITIAL_AMOUNT = 100;
    private final static double STARTING_BALANCE = 0.0;

    // 1. Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
        mRossi = new AccountHolder("Mario", "Rossi", 1);
        bankAccount = new StrictBankAccount(mRossi, STARTING_BALANCE);
    }

    // 2. Test the initial state of the StrictBankAccount
    @Test
    public void testInitialization() {
        assertEquals(STARTING_BALANCE, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }


    // 3. Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
    @Test
    public void testManagementFees() {
        /*Si potrebbe anche verificare il numero di transazioni
        prima del deposit() e prima e dopo il chargeManagementFees() */
        final double expected = INITIAL_AMOUNT - MANAGEMENT_FEE - TRANSACTION_FEE;
        bankAccount.deposit(mRossi.getUserID(), INITIAL_AMOUNT);
        bankAccount.chargeManagementFees(mRossi.getUserID());
        assertEquals(expected, bankAccount.getBalance());
    }

    // 4. Test the withdraw of a negative value
    @Test
    public void testNegativeWithdraw() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), -1);
            fail("Expected IllegalArgumentExeption for withdrawal of a negative amount");
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot withdraw a negative amount", e.getMessage());
        }
    }

    // 5. Test withdrawing more money than it is in the account
    @Test
    public void testWithdrawingTooMuch() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), INITIAL_AMOUNT);
            fail("Expected IllegalArgumentException for insufficient balance");
        } catch(IllegalArgumentException e) {
            assertEquals("Insufficient balance", e.getMessage());
        }
    }
}
