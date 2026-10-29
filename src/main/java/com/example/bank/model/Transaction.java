package com.example.bank.model;

/**
 * One movement of money on an account.
 *
 * @param type the kind of movement
 * @param amountCents the amount in cents, always positive
 * @param balanceAfterCents the balance of the account right after the movement
 */
public record Transaction(TransactionType type, long amountCents, long balanceAfterCents) {
}
