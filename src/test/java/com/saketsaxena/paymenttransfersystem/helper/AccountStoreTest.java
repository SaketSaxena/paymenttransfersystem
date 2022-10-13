package com.saketsaxena.paymenttransfersystem.helper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountStoreTest {

    @Autowired
    private AccountStore accountStore ;

    @Test
    void should_return_true_if_account_is_valid() {
        assertTrue(accountStore.isValidAccount(111));
    }

    @Test
    void should_return_false_if_account_is_invalid() {
        assertFalse(accountStore.isValidAccount(1111));
    }
}