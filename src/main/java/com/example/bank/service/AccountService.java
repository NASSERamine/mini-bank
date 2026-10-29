package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.model.AccountNotFoundException;
import com.example.bank.util.IbanValidator;

import java.util.HashMap;
import java.util.Map;

/**
 * Opens accounts and finds them by IBAN.
 */
public class AccountService {

    private final Map<String, Account> accounts = new HashMap<>();

    /**
     * Opens a new empty account.
     *
     * @param iban the IBAN of the new account; spaces are ignored
     * @param owner the full name of the owner
     * @return the new account
     * @throws IllegalArgumentException if the IBAN is invalid or already used
     */
    public Account open(String iban, String owner) {
        String normalized = IbanValidator.normalize(iban);
        if (!IbanValidator.isValid(normalized)) {
            throw new IllegalArgumentException("Invalid IBAN: " + iban);
        }
        if (accounts.containsKey(normalized)) {
            throw new IllegalArgumentException("IBAN already used: " + iban);
        }
        Account account = new Account(normalized, owner);
        accounts.put(normalized, account);
        return account;
    }

    /**
     * Finds an account by its IBAN.
     *
     * @param iban the IBAN to look up; spaces are ignored
     * @return the matching account
     * @throws AccountNotFoundException if no account has this IBAN
     */
    public Account find(String iban) throws AccountNotFoundException {
        Account account = accounts.get(IbanValidator.normalize(iban));
        if (account == null) {
            throw new AccountNotFoundException(iban);
        }
        return account;
    }

    /**
     * Returns how many accounts are open.
     *
     * @return the number of accounts
     */
    public int count() {
        return accounts.size();
    }
}
