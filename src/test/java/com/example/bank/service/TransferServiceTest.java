package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.model.AccountFrozenException;
import com.example.bank.model.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransferServiceTest {

    private static final String ALICE = "DE89370400440532013000";
    private static final String BOB = "GB82WEST12345698765432";

    private AccountService accounts;
    private TransferService transfers;
    private Account alice;
    private Account bob;

    @BeforeEach
    void setUp() throws Exception {
        accounts = new AccountService();
        transfers = new TransferService(accounts);
        alice = accounts.open(ALICE, "Alice");
        bob = accounts.open(BOB, "Bob");
        alice.deposit(100_00);
    }

    @Test
    void transferMovesMoney() throws Exception {
        transfers.transfer(ALICE, BOB, 40_00);
        assertEquals(60_00, alice.getBalanceCents());
        assertEquals(40_00, bob.getBalanceCents());
    }

    @Test
    void failedTransferChangesNothing() {
        assertThrows(InsufficientFundsException.class, () -> transfers.transfer(ALICE, BOB, 500_00));
        assertEquals(100_00, alice.getBalanceCents());
        assertEquals(0, bob.getBalanceCents());
    }

    @Test
    void transferToFrozenAccountChangesNothing() {
        bob.freeze();
        assertThrows(AccountFrozenException.class, () -> transfers.transfer(ALICE, BOB, 10_00));
        assertEquals(100_00, alice.getBalanceCents());
    }

    @Test
    void transferToSameAccountIsRefused() {
        assertThrows(IllegalArgumentException.class, () -> transfers.transfer(ALICE, ALICE, 1_00));
    }
}
