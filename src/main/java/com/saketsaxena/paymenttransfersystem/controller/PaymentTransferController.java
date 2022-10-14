package com.saketsaxena.paymenttransfersystem.controller;

import com.saketsaxena.paymenttransfersystem.DTOs.FundTransferSuccess;
import com.saketsaxena.paymenttransfersystem.DTOs.PaymentTransfer;
import com.saketsaxena.paymenttransfersystem.service.PaymentTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller to publish rest api to transfer funds between accounts.
 *
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

    /**
     * Rest endpoint to transfer the fund between accounts.
     *
     * @param paymentTransfer An object contains the information of payment transfer
     *                        like sender-account-id, receiver-account-id and amount
     * @return A success response if the transfer is successful, not found in case of account is invalid,
     * bad request in case of insufficient fund.
     */
    @PostMapping("/transfer-fund")
    public ResponseEntity<?> transferFund(@RequestBody PaymentTransfer paymentTransfer) {
        paymentTransferService.transferFund(paymentTransfer);
        return ResponseEntity
                .ok(new FundTransferSuccess(String.format("Money has been successfully transferred to account %s", paymentTransfer.receiverAccountId())));
    }
}
