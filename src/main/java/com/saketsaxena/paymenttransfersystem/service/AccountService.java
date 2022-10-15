package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.UserAccount;

import java.math.BigDecimal;

public interface AccountService {

    /**
     * Crate User account with specified account details.
     * @param userAccount Representation of UserAccount object.
     */
    void createUserAccount(UserAccount userAccount);

    /**
     * Get User account for specified accountId.
     * @param accountId account id of the user
     * @return object of UserAccount.
     */
    UserAccount getUserAccount(int accountId);

    /** Method to find out in the account is having insufficient balance.
     * @param accountId account id for which balance needs to be found.
     * @param amount amount which needs to be checked against the balance available.
     * @return boolean, true if balance is insufficient and false in balance is sufficient.
     */
    boolean isInsufficientBalance(int accountId, BigDecimal amount);

    /**
     * To check if the account is present or not.
     * @return A boolean, true if account is present in the system
     * and false is it is not present.
     */
    boolean isValidAccount(int accountId);

    /**
     * update account balance for specified account id.
     * @param accountId account id of the receiver.
     * @param accountBalance new balance of user account.
     */
    void updateAccountBalance(int accountId, BigDecimal accountBalance);

    void closeAccount(int accountId);
}
