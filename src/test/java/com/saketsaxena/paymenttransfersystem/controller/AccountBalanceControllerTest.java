package com.saketsaxena.paymenttransfersystem.controller;

import com.google.common.collect.EvictingQueue;
import com.saketsaxena.paymenttransfersystem.DTOs.AccountBalance;
import com.saketsaxena.paymenttransfersystem.DTOs.MiniStatement;
import com.saketsaxena.paymenttransfersystem.service.AccountBalanceService;
import com.saketsaxena.paymenttransfersystem.service.MiniStatementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Queue;

import static com.saketsaxena.paymenttransfersystem.DTOs.TransactionType.DEBIT;
import static java.time.ZonedDateTime.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class AccountBalanceControllerTest {

    @Mock
    private AccountBalanceService accountBalanceService;
    @Mock
    private MiniStatementService miniStatementService;
    @InjectMocks
    private AccountBalanceController accountBalanceController;

    @Test
    void should_get_account_balance_if_account_id_is_valid(){
        when(accountBalanceService.getAccountBalance(111))
                .thenReturn(new AccountBalance(111, new BigDecimal("100.10"), "GBP"));

        ResponseEntity<?> response = accountBalanceController.getAccountBalance(111);
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody())
                .extracting("accountId", "balance", "currency")
                .contains(111, new BigDecimal("100.10"), "GBP");
    }

    @Test
    void should_get_mini_statement_if_account_id_is_valid(){
        Queue<MiniStatement> miniStatements = EvictingQueue.create(20);
        miniStatements.add(new MiniStatement(111, new BigDecimal("20"), "GBP", DEBIT, now()));
        when(miniStatementService.getMiniStatement(111))
                .thenReturn(miniStatements);

        ResponseEntity<?> response = accountBalanceController.getAccountMiniStatement(111);
        assertThat(response.getStatusCode()).isEqualTo(OK);
        Queue<MiniStatement> miniStatement = (Queue<MiniStatement>) response.getBody();
        assertThat(miniStatement).isNotNull();
        miniStatement.forEach(
                statement -> assertThat(statement)
                        .extracting("accountId", "amount", "currency", "type")
                        .contains(111, new BigDecimal("20"), "GBP", DEBIT));
    }
}