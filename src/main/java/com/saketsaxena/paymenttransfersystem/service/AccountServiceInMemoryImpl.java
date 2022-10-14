package com.saketsaxena.paymenttransfersystem.service;

import com.saketsaxena.paymenttransfersystem.DTOs.UserAccount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/** Represents in-memory database to store account balance.
 * @author Saket Saxena
 * @since 1.0
 */
@Service
public class AccountServiceInMemoryImpl implements AccountService {

    /** Represents the map of all the user account.
     */
    private final Map<Integer, UserAccount> userAccount = new HashMap<>();
    public AccountServiceInMemoryImpl() {
        userAccount.put(111, new UserAccount(111, "First", "Name", new BigDecimal("100.10"), "GBP", "abc@abc.com", "street1"));
        userAccount.put(222, new UserAccount(222, "John", "Wick", new BigDecimal("324.45"), "GBP", "def@abc.com", "street2"));
    }

    /**
     * Get User account for specified accountId.
     * @param accountId account id of the user
     * @return object of UserAccount.
     */
    @Override
    public UserAccount getUserAccount(int accountId) {
        return userAccount.get(accountId);
    }

    /** Method to find out in the account is having insufficient balance.
     * @param accountId account id for which balance needs to be found.
     * @param amount amount which needs to be checked against the balance available.
     * @return boolean, true if balance is insufficient and false in balance is sufficient.
     */
    @Override
    public boolean isInsufficientBalance(int accountId, BigDecimal amount) {
        return userAccount.get(accountId).balance().compareTo(amount) < 0;
    }

    /**
     * To check if the account is present or not.
     * @return A boolean, true if account is present in the system
     * and false is it is not present.
     */
    @Override
    public boolean isValidAccount(int accountId) {
        return Optional.ofNullable(userAccount.get(accountId)).isPresent();
    }

    /**
     * update account balance for specified account id.
     * @param accountId account id of the receiver.
     * @param accountBalance new balance of user account.
     */
    @Override
    public void updateAccountBalance(int accountId, BigDecimal accountBalance){
        UserAccount updateUserAccount = userAccount.get(accountId);
        updateUserAccount.setBalance(accountBalance);
        userAccount.put(accountId, updateUserAccount);
    }

}
