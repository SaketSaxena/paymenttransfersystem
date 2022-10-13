package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.AccountBalance;
import com.saketsaxena.paymenttransfersystem.DTOs.PaymentTransfer;
import com.saketsaxena.paymenttransfersystem.exception.InsufficientBalanceException;
import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import com.saketsaxena.paymenttransfersystem.helper.AccountStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.saketsaxena.paymenttransfersystem.DTOs.TransactionType.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentTransferServiceTest {

    @Mock
    private AccountStore accountStore;
    @Mock
    private MiniStatementService miniStatementService;
    @InjectMocks
    private PaymentTransferService paymentTransferService;

    private final Map<Integer, AccountBalance> accountBalances = new HashMap<>();

    @BeforeEach
    void setUp(){
        accountBalances.put(111, new AccountBalance(111, new BigDecimal("100.10"), "GBP"));
        accountBalances.put(222, new AccountBalance(222, new BigDecimal("324.45"), "GBP"));
    }

    @Test
    void should_transfer_fund_when_account_is_valid_and_fund_available() {
        when(accountStore.isValidAccount(anyInt())).thenReturn(true);
        when(accountStore.isInsufficientBalance(anyInt())).thenReturn(false);
        when(accountStore.getAccountBalances()).thenReturn(accountBalances);

        PaymentTransfer paymentTransfer = new PaymentTransfer(111, 222, new BigDecimal("20"));
        paymentTransferService.transferFund(paymentTransfer);

        verify(miniStatementService).addTransactionToMiniStatement(111, new BigDecimal("20"), "GBP", DEBIT);
        verify(miniStatementService).addTransactionToMiniStatement(222, new BigDecimal("20"), "GBP", CREDIT);
    }

    @Test
    void should_not_transfer_fund_when_sender_account_is_invalid() {
        when(accountStore.isValidAccount(222)).thenReturn(true);
        when(accountStore.isValidAccount(111)).thenReturn(false);

        PaymentTransfer paymentTransfer = new PaymentTransfer(111, 222, new BigDecimal("20"));
        assertThatExceptionOfType(InvalidAccountException.class)
                .isThrownBy(() -> paymentTransferService.transferFund(paymentTransfer));
    }

    @Test
    void should_not_transfer_fund_when_receiver_account_is_invalid() {
        when(accountStore.isValidAccount(222)).thenReturn(false);

        PaymentTransfer paymentTransfer = new PaymentTransfer(111, 222, new BigDecimal("20"));
        assertThatExceptionOfType(InvalidAccountException.class)
                .isThrownBy(() -> paymentTransferService.transferFund(paymentTransfer));
    }

    @Test
    void should_not_transfer_fund_when_sender_has_insufficient_fund() {
        when(accountStore.isValidAccount(222)).thenReturn(true);
        when(accountStore.isValidAccount(111)).thenReturn(true);
        when(accountStore.isInsufficientBalance(111)).thenReturn(true);

        PaymentTransfer paymentTransfer = new PaymentTransfer(111, 222, new BigDecimal("20"));
        assertThatExceptionOfType(InsufficientBalanceException.class)
                .isThrownBy(() -> paymentTransferService.transferFund(paymentTransfer));
    }
}