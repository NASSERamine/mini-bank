package com.example.bank.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A bank account. Every amount is in cents. The balance never goes below zero.
 */
public class Account {

    private final String iban;
    private final String owner;
    private long balanceCents;
    private boolean frozen;
    private final List<Transaction> history = new ArrayList<>();

    /**
     * Opens an empty account.
     *
     * @param iban the IBAN of the account
     * @param owner the full name of the owner
     */
    public Account(String iban, String owner) {
        this.iban = iban;
        this.owner = owner;
    }

    /**
     * Returns the IBAN of the account.
     *
     * @return the IBAN
     */
    public String getIban() {
        return iban;
    }

    /**
     * Returns the full name of the owner.
     *
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Returns the current balance.
     *
     * @return the balance in cents, never negative
     */
    public long getBalanceCents() {
        return balanceCents;
    }

    /**
     * Adds money to the account.
     *
     * @param amountCents the amount to add, in cents, strictly positive
     * @throws AccountFrozenException if the account is frozen
     * @throws IllegalArgumentException if the amount is zero or negative
     */
    public void deposit(long amountCents) throws AccountFrozenException {
        requireActive();
        requirePositive(amountCents);
        balanceCents += amountCents;
        history.add(new Transaction(TransactionType.DEPOSIT, amountCents, balanceCents));
    }

    /**
     * Takes money from the account. The balance is unchanged when the
     * withdrawal is refused.
     *
     * @param amountCents the amount to take, in cents, strictly positive
     * @throws InsufficientFundsException if the amount is larger than the balance
     * @throws AccountFrozenException if the account is frozen
     * @throws IllegalArgumentException if the amount is zero or negative
     */
    public void withdraw(long amountCents) throws InsufficientFundsException, AccountFrozenException {
        requireActive();
        requirePositive(amountCents);
        if (amountCents > balanceCents) {
            throw new InsufficientFundsException(amountCents - balanceCents);
        }
        balanceCents -= amountCents;
        history.add(new Transaction(TransactionType.WITHDRAWAL, amountCents, balanceCents));
    }

    /**
     * Tells whether a withdrawal of this amount would succeed.
     *
     * @param amountCents the amount to check, in cents
     * @return true when the account is not frozen and the amount does not
     *         exceed the balance
     */
    public boolean canWithdraw(long amountCents) {
        return !frozen && amountCents > 0 && amountCents <= balanceCents;
    }

    /**
     * Credits interest computed by the bank.
     *
     * @param interestCents the interest to credit, in cents; zero is ignored
     */
    public void creditInterest(long interestCents) {
        if (interestCents <= 0) {
            return;
        }
        balanceCents += interestCents;
        history.add(new Transaction(TransactionType.INTEREST, interestCents, balanceCents));
    }

    /**
     * Freezes the account. A frozen account refuses deposits and withdrawals.
     */
    public void freeze() {
        frozen = true;
    }

    /**
     * Tells whether the account is frozen.
     *
     * @return true when the account is frozen
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Returns the transactions of the account, oldest first.
     *
     * @return a read-only list of the transactions
     */
    public List<Transaction> getHistory() {
        return Collections.unmodifiableList(history);
    }

    private void requireActive() throws AccountFrozenException {
        if (frozen) {
            throw new AccountFrozenException(iban);
        }
    }

    private static void requirePositive(long amountCents) {
        if (amountCents <= 0) {
            throw new IllegalArgumentException("Amount must be positive: " + amountCents);
        }
    }
}
