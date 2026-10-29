package com.example.bank.util;

/**
 * Computes interest from an annual rate in percent.
 */
public final class InterestCalculator {

    private InterestCalculator() {
    }

    /**
     * Computes one month of interest. The result is rounded down to the cent.
     *
     * @param balanceCents the balance in cents
     * @param annualRatePercent the annual rate in percent, for example 3.0 for 3%
     * @return the interest for one month, in cents, rounded down
     * @throws IllegalArgumentException if the rate is negative
     */
    public static long monthlyInterest(long balanceCents, double annualRatePercent) {
        if (annualRatePercent < 0) {
            throw new IllegalArgumentException("Rate must not be negative: " + annualRatePercent);
        }
        return (long) Math.floor(balanceCents * annualRatePercent / 100.0 / 12.0);
    }

    /**
     * Computes the interest earned over several months, with monthly
     * compounding. Each month is rounded down before the next one.
     *
     * @param balanceCents the starting balance in cents
     * @param annualRatePercent the annual rate in percent
     * @param months the number of months, zero or more
     * @return the total interest earned, in cents
     * @throws IllegalArgumentException if the rate or the number of months is negative
     */
    public static long compoundInterest(long balanceCents, double annualRatePercent, int months) {
        if (months < 0) {
            throw new IllegalArgumentException("Months must not be negative: " + months);
        }
        long balance = balanceCents;
        for (int i = 0; i < months; i++) {
            balance += monthlyInterest(balance, annualRatePercent);
        }
        return balance - balanceCents;
    }
}
