package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.AccountBalance;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/** Represents in-memory database to store account balance.
 * @author Saket Saxena
 * @since 1.0
 */
@Service
public class AccountServiceInMemoryImpl implements AccountService {

    /** Represents the map of all the account balances.
     */
    private final Map<Integer, AccountBalance> accountBalances = new HashMap<>();
    public AccountServiceInMemoryImpl() {
        accountBalances.put(111, new AccountBalance(111, new BigDecimal("100.10"), "GBP"));
        accountBalances.put(222, new AccountBalance(222, new BigDecimal("324.45"), "GBP"));
    }

    /**
     * Get all account balance .
     *
     * @return A List representing the AccountBalance of all the users.
     */
    @Override
    public Map<Integer, AccountBalance> getAccountBalances() {
        return accountBalances;
    }

    /** Method to find out in the account is having insufficient balance.
     * @return boolean, true if balance is insufficient and false in balance is sufficient
     */
    @Override
    public boolean isInsufficientBalance(int accountId, BigDecimal amount) {
        return accountBalances.get(accountId).balance().compareTo(amount) < 0;
    }

    /**
     * To check if the account is present or not.
     *
     * @return A boolean, true if account is present in the system
     * and false is it is not present.
     */
    @Override
    public boolean isValidAccount(int accountId) {
        return Optional.ofNullable(accountBalances.get(accountId)).isPresent();
    }

    /**
     * update account balance for specified account id.
     *
     * @param accountId account id of the receiver.
     * @param accountBalance Object of AccountBalance need to be updated.
     */
    @Override
    public void updateAccountBalance(int accountId, AccountBalance accountBalance){
        accountBalances.put(accountId, accountBalance);
    }

}
