package com.example.bank.model;

/**
 * Thrown when no account matches the given IBAN.
 */
public class AccountNotFoundException extends Exception {

    /**
     * Creates the exception.
     *
     * @param iban the IBAN that was looked up
     */
    public AccountNotFoundException(String iban) {
        super("No account with IBAN " + iban);
    }
}
