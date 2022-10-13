package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.AccountBalance;
import com.saketsaxena.paymenttransfersystem.DTOs.PaymentTransfer;
import com.saketsaxena.paymenttransfersystem.exception.InsufficientBalanceException;
import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import com.saketsaxena.paymenttransfersystem.helper.AccountStore;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.saketsaxena.paymenttransfersystem.DTOs.TransactionType.CREDIT;
import static com.saketsaxena.paymenttransfersystem.DTOs.TransactionType.DEBIT;

/** Service to perform operation related to payment transfer.
 * @author Saket Saxena
 * @since 1.0
 */
@Service
public class PaymentTransferService {

    private final AccountStore accountStore;
    private final MiniStatementService miniStatementService;

    public PaymentTransferService(AccountStore accountStore, MiniStatementService miniStatementService) {
        this.accountStore = accountStore;
        this.miniStatementService = miniStatementService;
    }

    /** Method to transfer fund.
     * @param paymentTransfer An object representing details of payment transfer
     */
    public void transferFund(PaymentTransfer paymentTransfer) {
        validateRequest(paymentTransfer);
        creditFundToAccount(paymentTransfer.receiverAccountId(), paymentTransfer.transferAmount());
        debitFundFromAccount(paymentTransfer.senderAccountId(), paymentTransfer.transferAmount());
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

    /**
     * Method to credit fund to the specified account.
     *
     * @param receiverAccountId account id of the receiver
     * @param amount amount need to be credit
     */
    private void creditFundToAccount(int receiverAccountId, BigDecimal amount) {
        AccountBalance existingBalance = accountStore.getAccountBalances().get(receiverAccountId);
        AccountBalance newAccountBalance = new AccountBalance(existingBalance.accountId(),
                existingBalance.balance().add(amount), existingBalance.currency());
        accountStore.updateAccountBalance(receiverAccountId, newAccountBalance);
        miniStatementService.addTransactionToMiniStatement(receiverAccountId, amount, existingBalance.currency(), CREDIT);
    }

    /**
     * Method to debit fund from the specified account.
     *
     * @param senderAccountId account id of the sender
     * @param amount amount need to debit
     */
    private void debitFundFromAccount(int senderAccountId, BigDecimal amount) {
        AccountBalance existingBalance = accountStore.getAccountBalances().get(senderAccountId);
        AccountBalance newAccountBalance = new AccountBalance(existingBalance.accountId(),
                existingBalance.balance().subtract(amount), existingBalance.currency());
        accountStore.updateAccountBalance(senderAccountId, newAccountBalance);
        miniStatementService.addTransactionToMiniStatement(senderAccountId, amount, existingBalance.currency(), DEBIT);
    }
}
