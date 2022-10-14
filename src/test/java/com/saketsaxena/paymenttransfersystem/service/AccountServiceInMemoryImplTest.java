package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.AccountBalance;
]import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void should_get_account_balances() {
        assertThat(accountServiceInMemoryImpl.getAccountBalances()).hasSize(2);
    }

    @Test
    void should_update_account_balance(){
        AccountBalance accountBalance = new AccountBalance(111, new BigDecimal("80"), "GBP");
        accountServiceInMemoryImpl.updateAccountBalance(111, accountBalance);

        assertThat(accountServiceInMemoryImpl.getAccountBalances().get(111))
                .extracting("accountId", "balance", "currency")
                .contains(111, new BigDecimal("80"), "GBP");
    }

}