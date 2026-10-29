package com.example.bank.service;

import com.example.bank.model.Account;
import com.example.bank.model.AccountNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private final AccountService service = new AccountService();

    @Test
    void openNormalizesTheIban() throws Exception {
        Account account = service.open("de89 3704 0044 0532 0130 00", "Alice");
        assertEquals("DE89370400440532013000", account.getIban());
        assertSame(account, service.find("DE89 3704 0044 0532 0130 00"));
    }

    @Test
    void openRejectsInvalidIban() {
        assertThrows(IllegalArgumentException.class, () -> service.open("DE00 1234", "Bob"));
    }

    @Test
    void openRejectsDuplicateIban() {
        service.open("GB82WEST12345698765432", "Alice");
        assertThrows(IllegalArgumentException.class, () -> service.open("GB82WEST12345698765432", "Bob"));
        assertEquals(1, service.count());
    }

    @Test
    void findUnknownIbanThrows() {
        assertThrows(AccountNotFoundException.class, () -> service.find("GB82WEST12345698765432"));
    }
}
