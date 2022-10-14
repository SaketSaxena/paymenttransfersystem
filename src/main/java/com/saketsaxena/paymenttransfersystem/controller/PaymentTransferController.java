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

/** Controller to publish rest api to transfer funds between accounts.
 * @author Saket Saxena
 * @since 1.0
 */
@RestController
public class PaymentTransferController {

    private final PaymentTransferService paymentTransferService;

    @Autowired
    public PaymentTransferController(PaymentTransferService paymentTransferService) {
        this.paymentTransferService = paymentTransferService;
    }

    /** Rest endpoint to transfer the fund between accounts.
     * @param paymentTransfer An object contains the information of payment transfer
     * like sender-account-id, receiver-account-id and amount
     * @return A success response if the transfer is successful, not found in case of account is invalid,
     * bad request in case of insufficient fund.
     */
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
