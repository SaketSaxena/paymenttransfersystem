package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.PaymentTransfer;
import com.saketsaxena.paymenttransfersystem.exception.BadRequestException;
import com.saketsaxena.paymenttransfersystem.exception.InsufficientBalanceException;
import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.saketsaxena.paymenttransfersystem.DTOs.TransactionType.CREDIT;
import static com.saketsaxena.paymenttransfersystem.DTOs.TransactionType.DEBIT;

/**
 * Service to perform operation related to payment transfer.
 *
 * @author Saket Saxena
 * @since 1.0
 */
@Service
public class PaymentTransferService {

    @Qualifier("accountServiceInMemoryImpl")
    private final AccountService accountService;
    private final MiniStatementService miniStatementService;

    public PaymentTransferService(AccountService accountService, MiniStatementService miniStatementService) {
        this.accountService = accountService;
        this.miniStatementService = miniStatementService;
    }

    /**
     * Method to transfer fund.
     *
     * @param paymentTransfer An object representing details of payment transfer
     */
    public void transferFund(PaymentTransfer paymentTransfer) {
        validateRequest(paymentTransfer);
        creditFundToAccount(paymentTransfer.receiverAccountId(), paymentTransfer.transferAmount());
        debitFundFromAccount(paymentTransfer.senderAccountId(), paymentTransfer.transferAmount());
    }


    /**
     * Method to valid if the transer fund request is valid or not.
     *
     * @param paymentTransfer An object representing details of payment transfer
     */
    private void validateRequest(PaymentTransfer paymentTransfer) {
        if (paymentTransfer.senderAccountId() == paymentTransfer.receiverAccountId()) {
            throw new BadRequestException("Sender and receiver id can not be the same");
        } else if (!accountService.isValidAccount(paymentTransfer.receiverAccountId())) {
            throw new InvalidAccountException(String.format("Invalid receiver account id %s", paymentTransfer.receiverAccountId()));
        } else if (!accountService.isValidAccount(paymentTransfer.senderAccountId())) {
            throw new InvalidAccountException(String.format("Invalid sender account id %s", paymentTransfer.senderAccountId()));
        } else if (accountService.isInsufficientBalance(paymentTransfer.senderAccountId(), paymentTransfer.transferAmount())) {
            throw new InsufficientBalanceException("You do not have sufficient balance to transfer fund");
        }
    }

    /**
     * Method to credit fund to the specified account.
     *
     * @param receiverAccountId account id of the receiver
     * @param amount            amount need to be credit
     */
    protected void creditFundToAccount(int receiverAccountId, BigDecimal amount) {
        accountService.getUserAccount(receiverAccountId)
                .ifPresent(userAccount -> {
                    accountService.updateAccountBalance(receiverAccountId, userAccount.balance().add(amount));
                    miniStatementService.addTransactionToMiniStatement(receiverAccountId, amount, userAccount.currency(), CREDIT);
                });
    }

    /**
     * Method to debit fund from the specified account.
     *
     * @param senderAccountId account id of the sender
     * @param amount          amount need to debit
     */
    protected void debitFundFromAccount(int senderAccountId, BigDecimal amount) {
        accountService.getUserAccount(senderAccountId)
                .ifPresent(userAccount -> {
                    accountService.updateAccountBalance(senderAccountId, userAccount.balance().subtract(amount));
                    miniStatementService.addTransactionToMiniStatement(senderAccountId, amount, userAccount.currency(), DEBIT);
                });

    }
}
