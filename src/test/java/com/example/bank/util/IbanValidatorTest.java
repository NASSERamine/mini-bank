package com.example.bank.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IbanValidatorTest {

    @Test
    void validIbansPass() {
        assertTrue(IbanValidator.isValid("DE89370400440532013000"));
        assertTrue(IbanValidator.isValid("GB82 WEST 1234 5698 7654 32"));
        assertTrue(IbanValidator.isValid("fr14 2004 1010 0505 0001 3m02 606"));
    }

    @Test
    void wrongChecksumFails() {
        assertFalse(IbanValidator.isValid("DE88370400440532013000"));
    }

    @Test
    void wrongFormatFails() {
        assertFalse(IbanValidator.isValid("1234"));
        assertFalse(IbanValidator.isValid("DEXX370400440532013000"));
    }

    @Test
    void normalizeRemovesSpacesAndUppercases() {
        assertEquals("GB82WEST12345698765432", IbanValidator.normalize("gb82 west 1234 5698 7654 32"));
    }
}
