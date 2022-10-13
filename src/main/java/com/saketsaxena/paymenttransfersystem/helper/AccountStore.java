package com.saketsaxena.paymenttransfersystem.helper;

import com.saketsaxena.paymenttransfersystem.DTOs.AccountBalance;
import com.saketsaxena.paymenttransfersystem.service.MiniStatementService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static com.saketsaxena.paymenttransfersystem.DTOs.TransactionType.*;

/** Represents in-memory database to store account balance.
 * @author Saket Saxena
 * @since 1.0
 */
@Service
public class AccountStore {

    /** Represents the map of all the account balances.
     */
    private final Map<Integer, AccountBalance> accountBalances = new HashMap<>();
    private final MiniStatementService miniStatementService;

    public AccountStore(MiniStatementService miniStatementService) {
        this.miniStatementService = miniStatementService;
        accountBalances.put(111, new AccountBalance(111, new BigDecimal("100.10"), "GBP"));
        accountBalances.put(222, new AccountBalance(222, new BigDecimal("324.45"), "GBP"));
    }

    /**
     * Get all account balance .
     *
     * @return A List representing the AccountBalance of all the users.
     */
    public Map<Integer, AccountBalance> getAccountBalances() {
        return accountBalances;
    }

    /** Method to find out in the account is having insufficient balance.
     * @return boolean, true if balance is insufficient and false in balance is sufficient
     */
    public boolean isInsufficientBalance(int accountId) {
        return accountBalances.get(accountId).balance().compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * To check if the account is present or not.
     *
     * @return A boolean, true if account is present in the system
     * and false is it is not present.
     */
    public boolean isValidAccount(int accountId) {
        return Optional.ofNullable(accountBalances.get(accountId)).isPresent();
    }

    /**
     * Method to credit fund to the specified account.
     *
     * @param receiverAccountId account id of the receiver
     * @param amount amount need to be credit
     */
    public void creditFundToAccount(int receiverAccountId, BigDecimal amount) {
        AccountBalance existingBalance = accountBalances.get(receiverAccountId);
        AccountBalance newAccountBalance = new AccountBalance(existingBalance.accountId(),
                existingBalance.balance().add(amount), existingBalance.currency());
        accountBalances.put(receiverAccountId, newAccountBalance);
        miniStatementService.addTransactionToMiniStatement(receiverAccountId, amount, existingBalance.currency(), CREDIT);
    }

    /**
     * Method to debit fund from the specified account.
     *
     * @param senderAccountId account id of the sender
     * @param amount amount need to debit
     */
    public void debitFundFromAccount(int senderAccountId, BigDecimal amount) {
        AccountBalance existingBalance = accountBalances.get(senderAccountId);
        AccountBalance newAccountBalance = new AccountBalance(existingBalance.accountId(),
                existingBalance.balance().subtract(amount), existingBalance.currency());
        accountBalances.put(senderAccountId, newAccountBalance);
        miniStatementService.addTransactionToMiniStatement(senderAccountId, amount, existingBalance.currency(), DEBIT);
    }
}
