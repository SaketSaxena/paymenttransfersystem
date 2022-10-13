package com.saketsaxena.paymenttransfersystem.controller;

import com.saketsaxena.paymenttransfersystem.DTOs.ErrorResponse;
import com.saketsaxena.paymenttransfersystem.DTOs.PaymentTransfer;
import com.saketsaxena.paymenttransfersystem.exception.InsufficientBalanceException;
import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import com.saketsaxena.paymenttransfersystem.service.PaymentTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class PaymentTransferController {

    private final PaymentTransferService paymentTransferService;

    @Autowired
    public PaymentTransferController(PaymentTransferService paymentTransferService) {
        this.paymentTransferService = paymentTransferService;
    }

    @PostMapping("/transfer-fund")
    public ResponseEntity<?> transferFund(@RequestBody PaymentTransfer paymentTransfer) {
        try {
            paymentTransferService.transferFund(paymentTransfer);
            return ResponseEntity.ok().build();
        } catch (InvalidAccountException invalidAccountException){
            ErrorResponse errorResponse = new ErrorResponse(invalidAccountException.getMessage());
            return ResponseEntity.status(NOT_FOUND).body(errorResponse);
        } catch (InsufficientBalanceException insufficientBalanceException){
            ErrorResponse errorResponse = new ErrorResponse(insufficientBalanceException.getMessage());
            return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
        }
    }
}
