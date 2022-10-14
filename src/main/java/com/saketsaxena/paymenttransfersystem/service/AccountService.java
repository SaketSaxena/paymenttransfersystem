package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.AccountBalance;

import java.util.Map;

public interface AccountService {

    /**
     * Get all account balance .
     *
     * @return A List representing the AccountBalance of all the users.
     */
    Map<Integer, AccountBalance> getAccountBalances();

    /** Method to find out in the account is having insufficient balance.
     * @return boolean, true if balance is insufficient and false in balance is sufficient
     */
    boolean isInsufficientBalance(int accountId);

    /**
     * To check if the account is present or not.
     *
     * @return A boolean, true if account is present in the system
     * and false is it is not present.
     */
    boolean isValidAccount(int accountId);

    /**
     * update account balance for specified account id.
     *
     * @param accountId account id of the receiver.
     * @param accountBalance Object of AccountBalance need to be updated.
     */
    void updateAccountBalance(int accountId, AccountBalance accountBalance);
}
