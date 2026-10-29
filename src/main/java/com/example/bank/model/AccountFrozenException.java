package com.example.bank.model;

/**
 * Thrown when an operation is attempted on a frozen account.
 */
public class AccountFrozenException extends Exception {

    /**
     * Creates the exception.
     *
     * @param iban the IBAN of the frozen account
     */
    public AccountFrozenException(String iban) {
        super("Account " + iban + " is frozen");
    }
}
