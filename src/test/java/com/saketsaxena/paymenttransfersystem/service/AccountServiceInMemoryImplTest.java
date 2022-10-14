package com.saketsaxena.paymenttransfersystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AccountServiceInMemoryImplTest {

    @Autowired
    private AccountService accountServiceInMemoryImpl;

    @Test
    void should_return_true_if_account_is_valid() {
        assertTrue(accountServiceInMemoryImpl.isValidAccount(111));
    }

    @Test
    void should_return_false_if_account_is_invalid() {
        assertFalse(accountServiceInMemoryImpl.isValidAccount(1111));
    }
}