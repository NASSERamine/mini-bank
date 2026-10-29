package com.example.bank.model;

/**
 * Thrown when a withdrawal or a transfer is larger than the available balance.
 */
public class InsufficientFundsException extends Exception {

    private final long missingCents;

    /**
     * Creates the exception.
     *
     * @param missingCents how many cents are missing to complete the operation
     */
    public InsufficientFundsException(long missingCents) {
        super("Insufficient funds: " + missingCents + " cents missing");
        this.missingCents = missingCents;
    }

    /**
     * Returns how many cents were missing.
     *
     * @return the missing amount in cents, always positive
     */
    public long getMissingCents() {
        return missingCents;
    }
}
