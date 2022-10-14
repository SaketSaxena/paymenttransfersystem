package com.saketsaxena.paymenttransfersystem.controller;

import com.saketsaxena.paymenttransfersystem.DTOs.PaymentTransfer;
import com.saketsaxena.paymenttransfersystem.service.PaymentTransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.OK;

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

}