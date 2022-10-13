package com.saketsaxena.paymenttransfersystem.controller;

import com.saketsaxena.paymenttransfersystem.DTOs.AccountBalance;
import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import com.saketsaxena.paymenttransfersystem.service.AccountBalanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class AccountBalanceControllerTest {

    @Mock
    private AccountBalanceService accountBalanceService;
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
    void should_get_error_message_if_account_balance_is_invalid(){
        when(accountBalanceService.getAccountBalance(100))
                .thenThrow(new InvalidAccountException("Account id 100 is not valid"));

        ResponseEntity<?> response = accountBalanceController.getAccountBalance(100);
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getBody())
                .extracting("errorMessage").isEqualTo("Account id 100 is not valid");
    }
}