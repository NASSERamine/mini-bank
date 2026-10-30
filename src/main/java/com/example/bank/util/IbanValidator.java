package com.example.bank.util;

import java.math.BigInteger;
import java.util.Locale;

/**
 * Checks IBANs with the ISO 13616 checksum (mod 97).
 */
public final class IbanValidator {

    private static final BigInteger NINETY_SEVEN = BigInteger.valueOf(97);

    private IbanValidator() {
    }

    /**
     * Removes the spaces and puts the letters in upper case.
     *
     * @param iban the IBAN as typed by a person
     * @return the IBAN without spaces, in upper case
     */
    public static String normalize(String iban) {
        return iban.replaceAll("[\\s-]", "").toUpperCase(Locale.ROOT);
    }

    /**
     * Tells whether an IBAN has a valid format and checksum.
     *
     * @param iban the IBAN to check; spaces are ignored
     * @return true if the IBAN has a valid format and checksum; false otherwise
     */
    public static boolean isValid(String iban) {
        String s = normalize(iban);
        if (!s.matches("[A-Z]{2}[0-9]{2}[A-Z0-9]{10,30}")) {
            return false;
        }
        String rearranged = s.substring(4) + s.substring(0, 4);
        StringBuilder digits = new StringBuilder();
        for (char c : rearranged.toCharArray()) {
            digits.append(Character.getNumericValue(c));
        }
        return new BigInteger(digits.toString()).mod(NINETY_SEVEN).intValue() == 1;
    }
}
