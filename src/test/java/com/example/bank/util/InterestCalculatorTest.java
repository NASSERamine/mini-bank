package com.example.bank.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterestCalculatorTest {

    @Test
    void monthlyInterestIsRoundedDown() {
        // 1000.00 at 3% a year = 2.50 a month
        assertEquals(2_50, InterestCalculator.monthlyInterest(1000_00, 3.0));
        // 333.33 at 1% a year = 0.2777... a month, rounded down to 0.27
        assertEquals(27, InterestCalculator.monthlyInterest(333_33, 1.0));
    }

    @Test
    void zeroRateGivesNoInterest() {
        assertEquals(0, InterestCalculator.monthlyInterest(1000_00, 0.0));
    }

    @Test
    void negativeRateIsRefused() {
        assertThrows(IllegalArgumentException.class, () -> InterestCalculator.monthlyInterest(100, -1.0));
    }

    @Test
    void compoundInterestAddsMonthByMonth() {
        long oneMonth = InterestCalculator.monthlyInterest(1000_00, 12.0);
        long twoMonths = InterestCalculator.compoundInterest(1000_00, 12.0, 2);
        assertEquals(10_00, oneMonth);
        assertEquals(10_00 + 10_10, twoMonths);
        assertEquals(0, InterestCalculator.compoundInterest(1000_00, 12.0, 0));
    }
}
