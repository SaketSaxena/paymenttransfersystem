package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.PaymentTransfer;
import com.saketsaxena.paymenttransfersystem.DTOs.UserAccount;
import com.saketsaxena.paymenttransfersystem.exception.BadRequestException;
import com.saketsaxena.paymenttransfersystem.exception.InsufficientBalanceException;
import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.saketsaxena.paymenttransfersystem.DTOs.TransactionType.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentTransferServiceTest {

    @Mock
    private AccountServiceInMemoryImpl accountServiceInMemoryImpl;
    @Mock
    private MiniStatementService miniStatementService;
    @InjectMocks
    private PaymentTransferService paymentTransferService;

    private final Map<Integer, UserAccount> userAccount = new HashMap<>();

    @BeforeEach
    void setUp(){
        userAccount.put(111, new UserAccount(111, "First", "Name", new BigDecimal("100.10"), "GBP", "abc@abc.com", "street1"));
        userAccount.put(222, new UserAccount(222, "John", "Wick", new BigDecimal("324.45"), "GBP", "def@abc.com", "street2"));
    }

    @Test
    void should_transfer_fund_when_account_is_valid_and_fund_available() {
        when(accountServiceInMemoryImpl.isValidAccount(anyInt())).thenReturn(true);
        when(accountServiceInMemoryImpl.isInsufficientBalance(anyInt(), any(BigDecimal.class))).thenReturn(false);
        when(accountServiceInMemoryImpl.getUserAccount(111)).thenReturn(Optional.of(userAccount.get(111)));
        when(accountServiceInMemoryImpl.getUserAccount(222)).thenReturn(Optional.of(userAccount.get(222)));

        PaymentTransfer paymentTransfer = new PaymentTransfer(111, 222, new BigDecimal("20"));
        paymentTransferService.transferFund(paymentTransfer);

        verify(miniStatementService).addTransactionToMiniStatement(111, new BigDecimal("20"), "GBP", DEBIT);
        verify(miniStatementService).addTransactionToMiniStatement(222, new BigDecimal("20"), "GBP", CREDIT);
    }

    @Test
    void should_not_transfer_fund_when_receiver_account_id_and_sender_account_id_is_same() {
        PaymentTransfer paymentTransfer = new PaymentTransfer(111, 111, new BigDecimal("20"));
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> paymentTransferService.transferFund(paymentTransfer));
    }

    @Test
    void should_not_transfer_fund_when_sender_account_is_invalid() {
        when(accountServiceInMemoryImpl.isValidAccount(222)).thenReturn(true);
        when(accountServiceInMemoryImpl.isValidAccount(111)).thenReturn(false);

        PaymentTransfer paymentTransfer = new PaymentTransfer(111, 222, new BigDecimal("20"));
        assertThatExceptionOfType(InvalidAccountException.class)
                .isThrownBy(() -> paymentTransferService.transferFund(paymentTransfer));
    }

    @Test
    void should_not_transfer_fund_when_receiver_account_is_invalid() {
        when(accountServiceInMemoryImpl.isValidAccount(222)).thenReturn(false);

        PaymentTransfer paymentTransfer = new PaymentTransfer(111, 222, new BigDecimal("20"));
        assertThatExceptionOfType(InvalidAccountException.class)
                .isThrownBy(() -> paymentTransferService.transferFund(paymentTransfer));
    }

    @Test
    void should_not_transfer_fund_when_sender_has_insufficient_fund() {
        when(accountServiceInMemoryImpl.isValidAccount(222)).thenReturn(true);
        when(accountServiceInMemoryImpl.isValidAccount(111)).thenReturn(true);
        when(accountServiceInMemoryImpl.isInsufficientBalance(111, new BigDecimal("20"))).thenReturn(true);

        PaymentTransfer paymentTransfer = new PaymentTransfer(111, 222, new BigDecimal("20"));
        assertThatExceptionOfType(InsufficientBalanceException.class)
                .isThrownBy(() -> paymentTransferService.transferFund(paymentTransfer));
    }

    @Test
    void should_credit_funds_to_account(){
        when(accountServiceInMemoryImpl.getUserAccount(111)).thenReturn(Optional.of(userAccount.get(111)));

        paymentTransferService.creditFundToAccount(111, new BigDecimal("20"));
        verify(accountServiceInMemoryImpl).updateAccountBalance(111, new BigDecimal("120.10"));
        verify(miniStatementService).addTransactionToMiniStatement(111, new BigDecimal("20"), "GBP", CREDIT);
    }

    @Test
    void should_debit_funds_to_account(){
        when(accountServiceInMemoryImpl.getUserAccount(111)).thenReturn(Optional.of(userAccount.get(111)));

        paymentTransferService.debitFundFromAccount(111, new BigDecimal("20"));
        verify(accountServiceInMemoryImpl).updateAccountBalance(111, new BigDecimal("80.10"));
        verify(miniStatementService).addTransactionToMiniStatement(111, new BigDecimal("20"), "GBP", DEBIT);
    }
}