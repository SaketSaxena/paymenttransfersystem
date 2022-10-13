package com.saketsaxena.paymenttransfersystem.controller;

import com.saketsaxena.paymenttransfersystem.DTOs.PaymentTransfer;
import com.saketsaxena.paymenttransfersystem.exception.InsufficientBalanceException;
import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import com.saketsaxena.paymenttransfersystem.service.PaymentTransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class PaymentTransferControllerTest {

    @Mock
    private PaymentTransferService paymentTransferService;
    @InjectMocks
    private PaymentTransferController paymentTransferController;


    @Test
    void should_transfer_fund_is_account_details_are_valid(){
        PaymentTransfer paymentTransfer = new PaymentTransfer(111, 222, new BigDecimal("20"));
        ResponseEntity<?> response = paymentTransferController.transferFund(paymentTransfer);
        assertThat(response.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void should_get_error_message_if_account_id_is_not_valid(){
        doThrow(new InvalidAccountException("Account id 100 is not valid"))
                .when(paymentTransferService).transferFund(any(PaymentTransfer.class));

        PaymentTransfer paymentTransfer = new PaymentTransfer(111, 222, new BigDecimal("20"));

        ResponseEntity<?> response = paymentTransferController.transferFund(paymentTransfer);
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getBody())
                .extracting("errorMessage").isEqualTo("Account id 100 is not valid");
    }

    @Test
    void should_get_error_message_if_sender_has_insufficient_balance(){
        doThrow(new InsufficientBalanceException("You do not have sufficient balance to transfer fund"))
                .when(paymentTransferService).transferFund(any(PaymentTransfer.class));

        PaymentTransfer paymentTransfer = new PaymentTransfer(111, 222, new BigDecimal("20"));

        ResponseEntity<?> response = paymentTransferController.transferFund(paymentTransfer);
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
        assertThat(response.getBody())
                .extracting("errorMessage").isEqualTo("You do not have sufficient balance to transfer fund");
    }

}