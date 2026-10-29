package com.example.bank.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account("DE89370400440532013000", "Alice Martin");
    }

    @Test
    void newAccountIsEmpty() {
        assertEquals(0, account.getBalanceCents());
        assertTrue(account.getHistory().isEmpty());
    }

    @Test
    void depositAddsToBalance() throws Exception {
        account.deposit(10_00);
        account.deposit(2_50);
        assertEquals(12_50, account.getBalanceCents());
        assertEquals(2, account.getHistory().size());
    }

    @Test
    void depositRejectsZeroAndNegative() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-5));
    }

    @Test
    void withdrawTakesFromBalance() throws Exception {
        account.deposit(100_00);
        account.withdraw(30_00);
        assertEquals(70_00, account.getBalanceCents());
    }

    @Test
    void withdrawMoreThanBalanceIsRefusedAndBalanceUnchanged() throws Exception {
        account.deposit(20_00);
        InsufficientFundsException e =
                assertThrows(InsufficientFundsException.class, () -> account.withdraw(25_00));
        assertEquals(5_00, e.getMissingCents());
        assertEquals(20_00, account.getBalanceCents());
    }

    @Test
    void balanceNeverGoesBelowZero() throws Exception {
        account.deposit(10_00);
        account.withdraw(10_00);
        assertEquals(0, account.getBalanceCents());
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(1));
    }

    @Test
    void canWithdrawMatchesWithdraw() throws Exception {
        account.deposit(50_00);
        assertTrue(account.canWithdraw(50_00));
        assertFalse(account.canWithdraw(50_01));
        assertFalse(account.canWithdraw(0));
    }

    @Test
    void frozenAccountRefusesEverything() throws Exception {
        account.deposit(10_00);
        account.freeze();
        assertTrue(account.isFrozen());
        assertFalse(account.canWithdraw(1_00));
        assertThrows(AccountFrozenException.class, () -> account.deposit(1_00));
        assertThrows(AccountFrozenException.class, () -> account.withdraw(1_00));
    }

    @Test
    void creditInterestIgnoresZero() {
        account.creditInterest(0);
        assertTrue(account.getHistory().isEmpty());
        account.creditInterest(42);
        assertEquals(42, account.getBalanceCents());
        assertEquals(TransactionType.INTEREST, account.getHistory().get(0).type());
    }

    @Test
    void historyIsReadOnly() {
        assertThrows(UnsupportedOperationException.class,
                () -> account.getHistory().add(new Transaction(TransactionType.DEPOSIT, 1, 1)));
    }
}
