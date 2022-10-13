package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.PaymentTransfer;
import com.saketsaxena.paymenttransfersystem.exception.InsufficientBalanceException;
import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import com.saketsaxena.paymenttransfersystem.helper.AccountStore;
import org.springframework.stereotype.Service;

/** Service to perform operation related to payment transfer.
 * @author Saket Saxena
 * @since 1.0
 */
@Service
public class PaymentTransferService {

    private final AccountStore accountStore;

    public PaymentTransferService(AccountStore accountStore) {
        this.accountStore = accountStore;
    }

    /** Method to transfer fund.
     * @param paymentTransfer An object representing details of payment transfer
     */
    public void transferFund(PaymentTransfer paymentTransfer) {
        validateRequest(paymentTransfer);
        accountStore.creditFundToAccount(paymentTransfer.receiverAccountId(), paymentTransfer.transferAmount());
        accountStore.debitFundFromAccount(paymentTransfer.senderAccountId(), paymentTransfer.transferAmount());
    }


    /** Method to valid if the transer fund request is valid or not.
     * @param paymentTransfer An object representing details of payment transfer
     */
    private void validateRequest(PaymentTransfer paymentTransfer) {
        if (!accountStore.isValidAccount(paymentTransfer.receiverAccountId())){
            throw new InvalidAccountException(String.format("Invalid receiver account id %s", paymentTransfer.receiverAccountId()));
        } else if (!accountStore.isValidAccount(paymentTransfer.senderAccountId())){
            throw new InvalidAccountException(String.format("Invalid sender account id %s", paymentTransfer.senderAccountId()));
        } else if (accountStore.isInsufficientBalance(paymentTransfer.senderAccountId())){
            throw new InsufficientBalanceException("You do not have sufficient balance to transfer fund");
        }
    }
}
