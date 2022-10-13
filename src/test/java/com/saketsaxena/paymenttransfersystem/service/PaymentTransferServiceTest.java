package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.PaymentTransfer;
import com.saketsaxena.paymenttransfersystem.exception.InsufficientBalanceException;
import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import com.saketsaxena.paymenttransfersystem.helper.AccountStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentTransferServiceTest {

    @Mock
    private AccountStore accountStore;
    @InjectMocks
    private PaymentTransferService paymentTransferService;


    @Test
    void should_transfer_fund_when_account_is_valid_and_fund_available() {
        when(accountStore.isValidAccount(anyInt())).thenReturn(true);
        when(accountStore.isInsufficientBalance(anyInt())).thenReturn(false);

        PaymentTransfer paymentTransfer = new PaymentTransfer(111, 222, new BigDecimal("20"));
        paymentTransferService.transferFund(paymentTransfer);

        verify(accountStore).creditFundToAccount(222, new BigDecimal("20"));
        verify(accountStore).debitFundFromAccount(111, new BigDecimal("20"));
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