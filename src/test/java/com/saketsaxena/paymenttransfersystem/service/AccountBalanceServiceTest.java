package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static com.saketsaxena.paymenttransfersystem.DTOs.TransactionType.DEBIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class AccountBalanceServiceTest {
    private final AccountBalanceService accountBalanceService;
    private final MiniStatementService miniStatementService;
    private final PaymentTransferService paymentTransferService;

    @Autowired
    AccountBalanceServiceTest(AccountBalanceService accountBalanceService,
                              MiniStatementService miniStatementService,
                              PaymentTransferService paymentTransferService) {
        this.accountBalanceService = accountBalanceService;
        this.miniStatementService = miniStatementService;
        this.paymentTransferService = paymentTransferService;
    }

    @Test
    void should_throw_invalid_account_exception_if_the_account_id_is_invalid() {
        assertThatExceptionOfType(InvalidAccountException.class)
                .isThrownBy(() -> accountBalanceService.getAccountBalance(100))
                .withMessage("Account id 100 is not valid");
    }

    @Test
    void should_fetch_account_balance_if_the_account_id_is_valid() {
        assertThat(accountBalanceService.getAccountBalance(111))
                .extracting("accountId", "balance", "currency")
                .contains(111, new BigDecimal("100.10"), "GBP");

    }


    @Test
    void should_throw_invalid_account_exception_if_the_account_id_is_invalid_while_getting_mini_statement() {
        assertThatExceptionOfType(InvalidAccountException.class)
                .isThrownBy(() -> miniStatementService.getMiniStatement(100))
                .withMessage("Account id 100 is not valid");
    }

    @Test
    void should_fetch_mini_statement_if_the_account_id_is_valid() {
        miniStatementService.addTransactionToMiniStatement(111, new BigDecimal("20"), "GBP", DEBIT);
        assertThat(miniStatementService.getMiniStatement(111))
                .extracting("accountId", "amount", "currency", "type")
                .contains(Tuple.tuple(111, new BigDecimal("20"), "GBP", DEBIT));
    }
}