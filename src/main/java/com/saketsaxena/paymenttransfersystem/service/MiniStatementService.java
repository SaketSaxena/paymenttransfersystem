package com.saketsaxena.paymenttransfersystem.service;

import com.google.common.collect.EvictingQueue;
import com.saketsaxena.paymenttransfersystem.DTOs.MiniStatement;
import com.saketsaxena.paymenttransfersystem.DTOs.TransactionType;
import com.saketsaxena.paymenttransfersystem.exception.InvalidAccountException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

/** Service to perform operation mini statement.
 * @author Saket Saxena
 * @since 1.0
 */
@Service
public class MiniStatementService {

    /** Store mini statements for all the accounts.
     */
    public final Map<Integer, Queue<MiniStatement>> miniStatements = new HashMap<>();

    @Qualifier("accountServiceInMemoryImpl")
    private final AccountService accountService;

    public MiniStatementService(AccountServiceInMemoryImpl accountService) {
        this.accountService = accountService;
    }

    /** add the transaction to mini statement.
     * @param accountId account id in which transaction is happened
     * @param amount transactional amount
     * @param currency transactional amount currency
     * @param type transaction type (CREDIT/DEBIT)
     */
    public void addTransactionToMiniStatement(int accountId, BigDecimal amount, String currency, TransactionType type){
        Queue<MiniStatement> miniStatement = findAccountMiniStatement(accountId);
        miniStatement.add(new MiniStatement(accountId, amount, currency, type, ZonedDateTime.now()));
        miniStatements.put(accountId, miniStatement);
    }

    /** get mini statement for a specified account.
     * @param accountId account id in which transaction is happened.
     * @return Queue of a MiniStatement of the object, holds only last 20 transaction.
     */
    public Queue<MiniStatement> getMiniStatement(int accountId){
        return accountService.getActiveUserAccount(accountId)
                .map(userAccount -> findAccountMiniStatement(userAccount.accountId()))
                .orElseThrow(() -> new InvalidAccountException(String.format("Account id %s is not valid", accountId)));
    }


    /** find the ministatement for the account and create a new mini statement if not present.
     * @param accountId account id in which transaction is happened.
     * @return Queue of a MiniStatement of the object, return empty Queue if is not found.
     */
    private Queue<MiniStatement> findAccountMiniStatement(int accountId){
        return Optional.ofNullable(miniStatements.get(accountId))
                .orElse(getEmptyMiniStatement());
    }

    /** create empty queue to hold 20 mini statement
     * @return empty queue of the MiniStatement object which can hold 20 mini statement.
     */
    private EvictingQueue<MiniStatement> getEmptyMiniStatement(){
        return EvictingQueue.create(20);
    }
}
