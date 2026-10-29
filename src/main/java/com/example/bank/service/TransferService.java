package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.model.AccountFrozenException;
import com.example.bank.model.AccountNotFoundException;
import com.example.bank.model.InsufficientFundsException;

/**
 * Moves money between two accounts of the same bank.
 */
public class TransferService {

    private final AccountService accounts;

    /**
     * Creates the service.
     *
     * @param accounts the service used to find the accounts
     */
    public TransferService(AccountService accounts) {
        this.accounts = accounts;
    }

    /**
     * Moves money from one account to another. The transfer is atomic: when it
     * fails, neither account changes.
     *
     * @param fromIban the IBAN of the account to debit
     * @param toIban the IBAN of the account to credit
     * @param amountCents the amount to move, in cents, strictly positive
     * @throws AccountNotFoundException if one of the IBANs is unknown
     * @throws InsufficientFundsException if the debited account has not enough money
     * @throws AccountFrozenException if one of the accounts is frozen
     * @throws IllegalArgumentException if both IBANs are the same account
     */
    public void transfer(String fromIban, String toIban, long amountCents)
            throws AccountNotFoundException, InsufficientFundsException, AccountFrozenException {
        Account from = accounts.find(fromIban);
        Account to = accounts.find(toIban);
        if (from == to) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }
        if (to.isFrozen()) {
            throw new AccountFrozenException(to.getIban());
        }
        from.withdraw(amountCents);
        to.deposit(amountCents);
    }
}
