package com.saketsaxena.paymenttransfersystem.helper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void should_credit_funds_to_receiver_account() {
        accountStore.creditFundToAccount(222, new BigDecimal("20"));
        assertThat(accountStore.getAccountBalances().get(222))
                .extracting("accountId", "balance", "currency")
                .contains(222, new BigDecimal("344.45"), "GBP");
    }

    @Test
    void should_debit_funds_from_sender_account() {
        accountStore.debitFundFromAccount(111, new BigDecimal("20"));
        assertThat(accountStore.getAccountBalances().get(111))
                .extracting("accountId", "balance", "currency")
                .contains(111, new BigDecimal("80.10"), "GBP");
    }

}