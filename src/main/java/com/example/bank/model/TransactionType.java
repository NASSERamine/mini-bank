package com.example.bank.model;

/**
 * The kind of a transaction on an account.
 */
public enum TransactionType {
    /** Money added to the account. */
    DEPOSIT,
    /** Money taken from the account. */
    WITHDRAWAL,
    /** Interest credited by the bank. */
    INTEREST
}
